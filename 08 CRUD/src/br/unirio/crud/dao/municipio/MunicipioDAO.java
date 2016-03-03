package br.unirio.crud.dao.municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unirio.crud.dao.SupportDAO;
import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Municipio;

/**
 * Classe responsável pela persistência de municípios
 * 
 * @author Marcio Barros
 */
public class MunicipioDAO implements IMunicipioDAO
{
	/**
	 * Carrega os dados de um município a partir do resultado de uma consulta
	 */
	private Municipio load(ResultSet rs) throws SQLException
	{
		Municipio municipio = new Municipio();
		municipio.setId(rs.getInt("id"));
		municipio.setNome(rs.getString("nome"));
		municipio.setEstado(Estado.get(rs.getString("estado")));
		municipio.setLongitude(rs.getString("longitude"));
		municipio.setLatitude(rs.getString("latitude"));
		return municipio;
	}

	/**
	 * Carrega os nomes dos municípios de um determinado estado
	 */
	@Override
	public List<Municipio> getMunicipiosEstado(Estado estado)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;

		List<Municipio> municipios = new ArrayList<Municipio>();
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Municipio WHERE estado = ? ORDER BY nome");
			ps.setString(1, estado.getSigla());
			ResultSet rs = ps.executeQuery();

			while (rs.next())
				municipios.add(load(rs));

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("MunicipioDAO.getMunicipiosEstado: " + e.getMessage());
		}
		    
		return municipios;
	}
	
	/**
	 * Retorna um municipio, dado seu nome
	 */
	@Override
	public Municipio pegaMunicipioNome(Estado estado, String nomeMunicipio)
	{
		Connection c = SupportDAO.getConnection();
		
		if (c == null)
			return null;
		
		Municipio item = null;
		
		try
		{
			PreparedStatement ps = c.prepareStatement("SELECT * FROM Municipio WHERE nome = ? AND estado = ?");
			ps.setString(1, nomeMunicipio);
			ps.setString(2, estado.getSigla());
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				item = load(rs);

			c.close();

		} catch (SQLException e)
		{
			SupportDAO.log("MunicipioDAO.pegaMunicipioNome: " + e.getMessage());
		}
		    
		return item;
	}
}