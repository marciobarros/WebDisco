package br.unirio.dsw.service.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.unirio.dsw.model.Unidade;
import br.unirio.dsw.model.Unidade.GestorUnidade;

/**
 * Classe responsavel pela persistencia de unidades funcionais
 * 
 * @author Marcio Barros
 */
@Service("unidadeDAO")
public class UnidadeDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de uma unidade a partir do resultado de uma consulta
	 */
	private Unidade carrega(ResultSet rs) throws SQLException
	{
		Unidade user = new Unidade();
		user.setId(rs.getInt("id"));
		user.setSigla(rs.getString("sigla"));
		user.setNome(rs.getString("nome"));
		return user;
	}

	/**
	 * Carrega uma unidade, dado seu identificador
	 */
	public Unidade carregaUnidadeId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM UnidadeFuncional WHERE id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Unidade item = rs.next() ? carrega(rs) : null;
			c.close();
			return item;

		} catch (SQLException e)
		{
			log("UnidadeDAO.carregaUnidadeId: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Conta o número de unidades segundo um filtro
	 */
	public int conta(String sigla, String nome)
	{
		String SQL = "SELECT COUNT(*) " +
					 "FROM UnidadeFuncional " + 
					 "WHERE sigla like ? " +
					 "AND nome like ? ";
		
		Connection c = getConnection();
		
		if (c == null)
			return 0;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + sigla + "%");
			ps.setString(2, "%" + nome + "%");
			ResultSet rs = ps.executeQuery();
			int contador = rs.next() ? rs.getInt(1) : null;
			c.close();
			return contador;

		} catch (SQLException e)
		{
			log("UnidadeDAO.contaUnidades: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Retorna uma lista de unidades segundo um filtro
	 */
	public List<Unidade> lista(int pagina, int tamanhoPagina, String sigla, String nome)
	{
		String SQL = "SELECT * " +
					 "FROM UnidadeFuncional " + 
					 "WHERE sigla like ? " +
					 "AND nome like ? " + 
					 "LIMIT ? OFFSET ? ";
		
		Connection c = getConnection();
		List<Unidade> unidades = new ArrayList<Unidade>();
		
		if (c == null)
			return unidades;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setString(1, "%" + sigla + "%");
			ps.setString(2, "%" + nome + "%");
			ps.setInt(3, tamanhoPagina);
			ps.setInt(4, pagina * tamanhoPagina);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				Unidade unidade = carrega(rs);
				unidades.add(unidade);
			}
			
			c.close();
			return unidades;

		} catch (SQLException e)
		{
			log("UnidadeDAO.listaUnidades: " + e.getMessage());
			return unidades;
		}
	}
	
	/**
	 * Adiciona uma unidade no sistema
	 */
	public boolean cria(Unidade unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalInsere(?, ?, ?)}");
			cs.setString(1, unidade.getNome());
			cs.setString(2, unidade.getSigla());
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			
			adicionaGestores(c, unidade);
			unidade.setId(cs.getInt(3));
			
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UnidadeDAO.cria: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Atualiza uma unidade no sistema
	 */
	public boolean atualiza(Unidade unidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalAtualiza(?, ?, ?)}");
			cs.setInt(1, unidade.getId());
			cs.setString(2, unidade.getNome());
			cs.setString(3, unidade.getSigla());
			cs.execute();
			
			removeGestores(c, unidade.getId());
			adicionaGestores(c, unidade);

			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UnidadeDAO.atualiza: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Remove uma unidade no sistema
	 */
	public boolean remove(int idUnidade)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call UnidadeFuncionalRemove(?)}");
			cs.setInt(1, idUnidade);
			cs.execute();
			c.close();
			return true;

		} catch (SQLException e)
		{
			log("UnidadeDAO.remove: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Carrega os gestores de uma unidade
	 */
	public boolean carregaGestores(Unidade unidade)
	{
		String SQL = "SELECT u.id, u.nome " +
					 "FROM GestorUnidadeFuncional g " +
					 "INNER JOIN Usuario u ON g.idUsuario = u.id " +
					 "WHERE g.idUnidade = ?";
		
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			PreparedStatement ps = c.prepareStatement(SQL);
			ps.setInt(1, unidade.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				unidade.adicionaGestor(id, nome);
			}
			
			c.close();
			return true;
	
		} catch (SQLException e)
		{
			log("UnidadeDAO.carregaGestores: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Adiciona os gestores em uma unidade
	 */
	private void adicionaGestores(Connection c, Unidade unidade) throws SQLException
	{
		for (GestorUnidade gestor : unidade.pegaGestores())
			adicionaGestor(c, unidade.getId(), gestor.getId());
	}

	/**
	 * Adiciona um gestor em uma unidade
	 */
	private void adicionaGestor(Connection c, int idUnidade, int idUsuario) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call UnidadeFuncionalAssociaGestor(?, ?)}");
		cs.setInt(1, idUnidade);
		cs.setInt(2, idUsuario);
		cs.execute();
		c.close();
	}

	/**
	 * Remove todos os gestores de uma unidade
	 */
	private void removeGestores(Connection c, int idUnidade) throws SQLException
	{
		CallableStatement cs = c.prepareCall("{call UnidadeFuncionalDesassociaGestores(?)}");
		cs.setInt(1, idUnidade);
		cs.execute();
		c.close();
	}
}