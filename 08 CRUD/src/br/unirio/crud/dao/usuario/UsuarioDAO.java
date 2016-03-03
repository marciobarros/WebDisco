package br.unirio.crud.dao.usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.unirio.crud.dao.SupportDAO;
import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.dao.OrderingType;
import br.unirio.simplemvc.utils.DateUtils;

/**
 * Classe responsável pela persistência de usuários
 * 
 * @author Marcio Barros
 */
public class UsuarioDAO implements IUsuarioDAO
{
	/**
	 * Carrega os dados de um usuário a partir do resultado de uma consulta
	 */
	private Usuario load(ResultSet rs) throws SQLException
	{
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id"));
		usuario.setDataRegistro(DateUtils.toDateTime(rs.getTimestamp("dataRegistro")));
		usuario.setNome(rs.getString("nome"));
		usuario.setEmail(rs.getString("email"));
		usuario.setEndereco(rs.getString("endereco"));
		usuario.setComplemento(rs.getString("complemento"));
		usuario.setEstado(Estado.get(rs.getString("estado")));
		usuario.setMunicipio(rs.getString("municipio"));
		usuario.setCep(rs.getString("cep"));
		usuario.setTelefoneFixo(rs.getString("telefoneFixo"));
		usuario.setTelefoneCelular(rs.getString("telefoneCelular"));		
		usuario.setSenhaCodificada(rs.getString("senha"));
		usuario.setDeveTrocarSenha(rs.getInt("deveTrocarSenha") != 0);
		return usuario;
	}

	/**
	 * Carrega um usuário, dado seu identificador
	 */
	@Override
	public Usuario getUsuarioId(int id)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		Usuario item = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				item = load(rs);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.getUsuarioId: " + e.getMessage());
		}

		return item;
	}
	
	/**
	 * Retorna um usuário, dado seu nome
	 */
	@Override
	public Usuario getUsuarioNome(String nome)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		Usuario item = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE nome = ?");
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				item = load(rs);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.getUsuarioNome: " + e.getMessage());
		}
		    
		return item;
	}

	/**
	 * Retorna um usuário, dado seu e-mail
	 */
	@Override
	public Usuario getUsuarioEmail(String email)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		Usuario item = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Usuario WHERE email = ?");
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				item = load(rs);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.getUsuarioEmail: " + e.getMessage());
		}
		    
		return item;
	}
	
	/**
	 * Conta o número de usuários que atendem a um filtro
	 */
	@Override
	public int conta(String filtroNome, String filtroEmail, String filtroEstado)
	{
		String SQL = "SELECT COUNT(*) " + 
					 "FROM Usuario " + 
					 "WHERE nome LIKE ? " + 
					 "AND email LIKE ? " + 
					 "AND estado LIKE ? ";
		
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return 0;
		
		int count = 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + filtroNome + "%");
			ps.setString(2, "%" + filtroEmail + "%");
			ps.setString(3, "%" + filtroEstado + "%");
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				count = rs.getInt(1);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.conta: " + e.getMessage());
		}
		    
		return count;
	}

	/**
	 * Retorna a lista de usuários registrados no sistema que atendem a um filtro
	 */
	@Override
	public List<Usuario> lista(int pagina, int tamanho, OrdenacaoUsuario campoOrdenacao, OrderingType tipoOrdenacao, String filtroNome, String filtroEmail, String filtroEstado)
	{
		String SQL = "SELECT * " + 
					 "FROM Usuario " + 
					 "WHERE nome LIKE ? " + 
					 "AND email LIKE ? " + 
					 "AND estado LIKE ? ";
		
		String SQL_PAGE = "LIMIT ? OFFSET ?";

		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		List<Usuario> lista = new ArrayList<Usuario>();
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL + campoOrdenacao.geraClausula(tipoOrdenacao) + SQL_PAGE);
			ps.setString(1, "%" + filtroNome + "%");
			ps.setString(2, "%" + filtroEmail + "%");
			ps.setString(3, "%" + filtroEstado + "%");
			ps.setInt(4, tamanho);
			ps.setInt(5, pagina * tamanho);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				Usuario item = load(rs);
				lista.add(item);
			}

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.lista: " + e.getMessage());
		}
		    
		return lista;
	}

	/**
	 * Adiciona um usuário no sistema
	 */
	@Override
	public boolean adiciona(Usuario usuario)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InsereUsuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cs.setString(1, usuario.getNome());
			cs.setString(2, usuario.getEmail());
			cs.setString(3, usuario.getSenhaCodificada());
			cs.setString(4, usuario.getEndereco());
			cs.setString(5, usuario.getComplemento());
			cs.setString(6, usuario.getEstado().getSigla());
			cs.setString(7, usuario.getMunicipio());
			cs.setString(8, usuario.getCep());
			cs.setString(9, usuario.getTelefoneFixo());
			cs.setString(10, usuario.getTelefoneCelular());
			cs.registerOutParameter(11, Types.INTEGER);
			cs.execute();
			usuario.setId(cs.getInt(11));
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.adiciona: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza os dados de um usuário
	 */
	@Override
	public boolean atualiza(Usuario usuario)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call AtualizaUsuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, usuario.getId());
			cs.setString(2, usuario.getNome());
			cs.setString(3, usuario.getEmail());
			cs.setString(4, usuario.getEndereco());
			cs.setString(5, usuario.getComplemento());
			cs.setString(6, usuario.getEstado().getSigla());
			cs.setString(7, usuario.getMunicipio());
			cs.setString(8, usuario.getCep());
			cs.setString(9, usuario.getTelefoneFixo());
			cs.setString(10, usuario.getTelefoneCelular());
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.atualiza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Remove um usuário do sistema
	 */
	@Override
	public boolean remove(int id)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			PreparedStatement cs = c.prepareStatement("{call RemoveUsuario(?)}");
			cs.setInt(1, id);			
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.remove: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza a senha de um usuário
	 */
	@Override
	public boolean atualizaSenha(int idUsuario, String senhaCodificada)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call AtualizaSenhaUsuario(?, ?)}");
			cs.setInt(1, idUsuario);
			cs.setString(2, senhaCodificada);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.atualizaSenha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Registra o login de um usuário com sucesso
	 */
	@Override
	public boolean registraLoginSucesso(int idUsuario)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call RegistraLoginSucesso(?)}");
			cs.setInt(1, idUsuario);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.registraLoginSucesso: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Registra o login de um usuário com falha
	 */
	@Override
	public boolean registraLoginFalha(int idUsuario)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call RegistraLoginFalha(?)}");
			cs.setInt(1, idUsuario);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.registraLoginFalha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Pega a data/hora do último login de um usuário
	 */
	@Override
	public DateTime pegaDataUltimoLogin(int idUsuario)
	{
		String SQL = "select max(data) " +
					 "from UsuarioLogin " + 
					 "where idUsuario = ? " + 
					 "and sucesso = 1";

		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		DateTime data = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				data = DateUtils.toDateTime(rs.getTimestamp(1));

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.pegaDataUltimoLogin: " + e.getMessage());
		}
		    
		return data;
	}
	
	/**
	 * Pega o número de tentativas de login com falha de um usuário
	 */
	@Override
	public int pegaNumeroTentativasFalha(int idUsuario)
	{
		String SQL = "select count(*) " +
					 "from UsuarioLogin " +
					 "where idUsuario = ? " +
					 "and data > (select COALESCE(max(data), '2000-01-01') from UsuarioLogin where idUsuario = ? and sucesso = 1)";
		
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return 0;
		
		int tentativas = 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				tentativas = rs.getInt(1);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.pegaNumeroTentativasFalha: " + e.getMessage());
		}
		    
		return tentativas;
	}
	
	/**
	 * Armazena um token de troca de senha para um usuário
	 */
	@Override
	public boolean armazenaTokenTrocaSenha(int idUsuario, String token)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call RegistraTokenResetSenha(?, ?)}");
			cs.setInt(1, idUsuario);
			cs.setString(2, token);
			cs.executeUpdate();
			c.close();
			return true;

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.armazenaTokenTrocaSenha: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Verifica se um token de troca de senha é válido para um usuário
	 */
	@Override
	public boolean verificaTokenTrocaSenha(int idUsuario, String token, int maxHoras)
	{
		String SQL = "select token, data, NOW() " +
					 "from UsuarioToken " + 
					 "where idUsuario = ? " + 
					 "order by data desc";

		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, idUsuario);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{
				String tokenBanco = rs.getString(1);
				DateTime dataToken = DateUtils.toDateTime(rs.getTimestamp(2));
				DateTime dataAgora = DateUtils.toDateTime(rs.getTimestamp(3));

				long horas = (dataAgora.getMillis() - dataToken.getMillis()) / (60 * 60 * 1000);
				
				if (horas > maxHoras)
					return false;
				
				if (token.compareTo(tokenBanco) != 0)
					return false;
			}

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("UsuarioDAO.verificaTokenTrocaSenha: " + e.getMessage());
		}
		    
		return true;
	}
}