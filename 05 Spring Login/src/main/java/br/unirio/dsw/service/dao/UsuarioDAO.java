package br.unirio.dsw.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import br.unirio.dsw.model.usuario.Usuario;
import br.unirio.dsw.utils.DateUtils;
import lombok.Data;

/**
 * Classe responsavel pela persistencia de usuários
 * 
 * @author Marcio Barros
 */
@Service("userDAO")
public class UsuarioDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de um usuário a partir do resultado de uma consulta
	 */
	private Usuario carrega(ResultSet rs) throws SQLException
	{
		String nome = rs.getString("nome");
		String email = rs.getString("email");
		String senha = rs.getString("senha");
		boolean bloqueado = rs.getInt("bloqueado") != 0;

		Usuario user = new Usuario(nome, email, senha, bloqueado);
		user.setId(rs.getInt("id"));
		user.setDataUltimoLogin(DateUtils.toDateTime(rs.getTimestamp("dataUltimoLogin")));
		user.setContadorFalhasLogin(rs.getInt("falhasLogin"));
		user.setTokenLogin(rs.getString("tokenSenha"));
		user.setDataTokenLogin(DateUtils.toDateTime(rs.getTimestamp("dataTokenSenha")));
		user.setAdministrador(rs.getInt("administrador") != 0);
		user.setProviderId(rs.getString("providerId"));
		user.setProviderUserId(rs.getString("providerUserId"));
		user.setProfileUrl(rs.getString("profileUrl"));
		user.setImageUrl(rs.getString("imageUrl"));
		user.setAccessToken(rs.getString("accessToken"));
		user.setSecret(rs.getString("secret"));
		user.setRefreshToken(rs.getString("refreshToken"));
		user.setExpireTime(rs.getLong("expireTime"));
		return user;
	}

	/**
	 * Carrega um usuário, dado seu identificador
	 */
	public Usuario carregaUsuarioId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Usuario item = rs.next() ? carrega(rs) : null;
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("UserDAO.carregaUsuarioId: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Carrega um usuário, dado seu e-mail
	 */
	public Usuario carregaUsuarioEmail(String email)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			Usuario item = rs.next() ? carrega(rs) : null;
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("UserDAO.carregaUsuarioEmail: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Carrega um usuário, dado seu provedor e identificador no provedor
	 */
	public Usuario carregaUsuarioProvedor(String providerId, String providerUserId)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE providerId = ? AND providerUserId = ?");
			ps.setString(1, providerId);
			ps.setString(2, providerUserId);

			ResultSet rs = ps.executeQuery();
			Usuario item = rs.next() ? carrega(rs) : null;
			
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("UserDAO.carregaUsuarioProvedor: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Conta o número de resumos de usuários segundo um filtro
	 */
	public int contaResumos(String filtroNome)
	{
		String SQL = "SELECT COUNT(*) " +
					 "FROM Usuario " + 
					 "WHERE nome like ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");
			ResultSet rs = ps.executeQuery();
			int contador = rs.next() ? rs.getInt(1) : null;
			c.close();
			return contador;
	
		} catch (SQLException e)
		{
			log("UsuarioDAO.contaResumos: " + e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Gera uma lista paginada de resumos de usuários
	 */
	public List<ResumoUsuario> listaResumos(int pagina, int tamanhoPagina, String filtroNome)
	{
		String SQL = "SELECT id, nome " +
					 "FROM Usuario " + 
					 "WHERE nome like ? " + 
					 "LIMIT ? OFFSET ? ";
		
		Connection c = getConnection();
		List<ResumoUsuario> resumos = new ArrayList<ResumoUsuario>();
		
		if (c == null)
			return resumos;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");
			ps.setInt(2, tamanhoPagina);
			ps.setInt(3, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				ResumoUsuario resumo = new ResumoUsuario();
				resumo.setId(rs.getInt(1));
				resumo.setNome(rs.getString(2));
				resumos.add(resumo);
			}
			
			c.close();
			return resumos;
	
		} catch (SQLException e)
		{
			log("UsuarioDAO.listaResumos: " + e.getMessage());
			return resumos;
		}
	}
	
	/**
	 * Adiciona um usuário no sistema
	 */
	public boolean criaNovoUsuario(Usuario usuario)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioInsere(?, ?, ?, ?)}");
			cs.setString(1, usuario.getNome());
			cs.setString(2, usuario.getUsername());
			cs.setString(3, usuario.getPassword());
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			usuario.setId(cs.getInt(4));
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.criaNovo: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Conecta um usuário no sistema
	 */
	public boolean conectaUsuario(Usuario usuario)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioConecta(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, usuario.getId());
			cs.setString(2, usuario.getProviderId());
			cs.setString(3, usuario.getProviderUserId());
			cs.setString(4, usuario.getProfileUrl());
			cs.setString(5, usuario.getImageUrl());
			cs.setString(6, usuario.getAccessToken());
			cs.setString(7, usuario.getSecret());
			cs.setString(8, usuario.getRefreshToken());
			cs.setLong(9, usuario.getExpireTime());
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.conecta: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza a senha de um usuário
	 */
	public boolean atualizaSenha(int id, String senha)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioTrocaSenha(?, ?)}");
			cs.setInt(1, id);
			cs.setString(2, senha);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.atualizaSenha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Registra o login de um usuário com sucesso
	 */
	public boolean registraLoginSucesso(String email)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraLoginSucesso(?)}");
			cs.setString(1, email);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.registraLoginSucesso: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Registra o login de um usuário com falha
	 */
	public boolean registraLoginFalha(String email)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraLoginFalha(?)}");
			cs.setString(1, email);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.registraLoginFalha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Armazena um token de troca de senha para um usuário
	 */
	public boolean salvaTokenLogin(int id, String token)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UsuarioRegistraTokenResetSenha(?, ?)}");
			cs.setInt(1, id);
			cs.setString(2, token);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UserDAO.salvaTokenLogin: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verifica se o token de login de um usuário é válido
	 */
	public boolean verificaValidadeTokenLogin(String email, String token, int maximoHoras) 
	{
		String SQL = "SELECT dataTokenSenha, NOW() " +
					 "FROM Usuario " + 
					 "WHERE email = ? " +
					 "AND tokenSenha = ?";

		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, email);
			ps.setString(2, token);

			ResultSet rs = ps.executeQuery();
			long hours = Integer.MAX_VALUE;

			if (rs.next())
			{
				DateTime dateToken = DateUtils.toDateTime(rs.getTimestamp(1));
				DateTime dateNow = DateUtils.toDateTime(rs.getTimestamp(2));
				hours = (dateNow.getMillis() - dateToken.getMillis()) / (60 * 60 * 1000);
			}

			c.close();
			return (hours < maximoHoras);

		} catch (SQLException e)
		{
			log("UserDAO.verificaValidadeTokenLogin: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Classe que representa um resumo de usuário
	 * 
	 * @author Marcio
	 *
	 */
	public @Data class ResumoUsuario
	{
		private int id;
		private String nome;
	}
}