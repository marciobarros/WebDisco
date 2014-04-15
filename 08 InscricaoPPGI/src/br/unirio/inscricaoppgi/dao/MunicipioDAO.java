package br.unirio.inscricaoppgi.dao;

import br.unirio.inscricaoppgi.model.Municipio;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Classe que trata da persistência de municípios
 * 
 * @author Marcio
 */
public class MunicipioDAO extends AbstractDAO<Municipio>
{
	/**
	 * Inicializa a classe
	 */
	protected MunicipioDAO()
	{
		super("Municipio");
	}

	/**
	 * Carrega os dados de um município
	 */
	@Override
	protected Municipio load(Entity e)
	{
		Municipio municipio = new Municipio();
		municipio.setId((int)e.getKey().getId());
		municipio.setNome(getStringProperty(e, "nome", ""));
		municipio.setEstado(getStringProperty(e, "email", ""));
		return municipio;
	}

	/**
	 * Salva os dados de um município
	 */
	@Override
	protected void save(Municipio municipio, Entity e)
	{
		e.setProperty("nome", municipio.getNome());
		e.setProperty("email", municipio.getEstado());
	}

	/**
	 * Carrega a lista de municípios de um estado
	 */
	public Iterable<Municipio> getMunicipiosEstado(String estado)
	{
		return list(exactFilter("email", FilterOperator.EQUAL, estado), "nome", SortDirection.ASCENDING);
	}
}