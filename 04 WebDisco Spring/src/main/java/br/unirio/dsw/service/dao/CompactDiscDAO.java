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

import br.unirio.dsw.model.CompactDisc;

/**
 * Classe que implementa o acesso aos CD no banco de dados
 * 
 * @author marcio.barros
 */
@Service("compactDiscDAO")
public class CompactDiscDAO extends AbstractDAO
{
	/**
	 * Carrega os dados de um CD a partir de uma consulta
	 */
	private CompactDisc carrega(ResultSet rs) throws SQLException
	{
		CompactDisc cd = new CompactDisc();
		cd.setId(rs.getInt("id"));
		cd.setTitle(rs.getString("title"));
		cd.setPrice(rs.getDouble("price"));
		cd.setStock(rs.getDouble("stock"));
		return cd;
	}
	
	/**
	 * Retorna um CD, dado seu identificador
	 */
	public CompactDisc getCompactDiscId(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		CompactDisc item = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM CompactDisc WHERE id = ?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				item = carrega(rs);

			c.close();

		} 
		catch (SQLException e)
		{
			log("CompactDiscDAO.carregaId: " + e.getMessage());
		}
		    
		return item;
	}

	/**
	 * Retorna a lista de CDs armazenados no sistema
	 */
	public List<CompactDisc> lista()
	{
		Connection c = getConnection();
		
		if (c == null)
			return null;
		
		List<CompactDisc> lista = new ArrayList<CompactDisc>();
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM CompactDisc");
			ResultSet rs = ps.executeQuery();

			while (rs.next())
				lista.add(carrega(rs));

			c.close();

		} 
		catch (SQLException e)
		{
			log("CompactDiscDAO.lista: " + e.getMessage());
		}
		    
		return lista;
	}

	/**
	 * Insere um CD no sistema
	 */
	public boolean insere(CompactDisc cd)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call InsereCompactDisc(?, ?, ?, ?)}");
			cs.setString(1, cd.getTitle());
			cs.setDouble(2, cd.getPrice());
			cs.setDouble(3, cd.getStock());
			cs.registerOutParameter(4, Types.INTEGER);
			cs.execute();
			
			int id = cs.getInt(4);
			cd.setId(id);
			
			c.close();
			return true;

		} 
		catch (SQLException e)
		{
			log("CompactDiscDAO.insere: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Atualiza os dados de um CD no sistema
	 */
	public boolean atualiza(CompactDisc cd)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call AtualizaCompactDisc(?, ?, ?, ?)}");
			cs.setInt(1, cd.getId());
			cs.setString(2, cd.getTitle());
			cs.setDouble(3, cd.getPrice());
			cs.setDouble(4, cd.getStock());
			cs.execute();	
			c.close();
			return true;
		} 
		catch (SQLException e)
		{
			log("CompactDiscDAO.atualiza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Remove um CD do sistema
	 */
	public boolean remove(int id)
	{
		Connection c = getConnection();
		
		if (c == null)
			return false;
		
		try
		{
			CallableStatement cs = c.prepareCall("{call RemoveCompactDisc(?)}");
			cs.setInt(1, id);
			cs.execute();	
			c.close();
			return true;
		} 
		catch (SQLException e)
		{
			log("CompactDiscDAO.remove: " + e.getMessage());
			return false;
		}
	}
}