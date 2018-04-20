package br.unirio.dsw.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import br.unirio.dsw.model.usuario.Usuario;
import br.unirio.dsw.utils.DateUtils;

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
}