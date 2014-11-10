package br.unirio.inscricaoppgi.dao;

import java.util.List;

import br.unirio.inscricaoppgi.gae.datastore.AbstractDAO;
import br.unirio.inscricaoppgi.model.Edital;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Classe que trata da persistência de editais
 * 
 * @author Marcio
 */
public class EditalDAO extends AbstractDAO<Edital>
{
	/**
	 * Inicializa a classe
	 */
	public EditalDAO()
	{
		super("Edital");
	}

	/**
	 * Carrega os dados de um edital
	 */
	@Override
	protected Edital load(Entity e)
	{
		Edital edital = new Edital();
		edital.setId((int)e.getKey().getId());
		edital.setDataAbertura(getDateProperty(e, "dataAbertura"));
		edital.setDataFechamento(getDateProperty(e, "dataFechamento"));
		return edital;
	}

	/**
	 * Salva os dados de um edital
	 */
	@Override
	protected void save(Edital edital, Entity e)
	{
		e.setProperty("dataAbertura", edital.getDataAbertura());
		e.setProperty("dataFechamento", edital.getDataFechamento());
	}
	
	/**
	 * Carrega o último edital aberto
	 * @return
	 */
	public Edital pegaUltimoEditalAberto()
	{
		List<Edital> editais = DAOFactory.getEditalDAO().list("dataAbertura", SortDirection.DESCENDING);
		
		if (editais.size() == 0)
			return null;
		
		Edital edital = editais.get(0);
		
		if (edital.getDataFechamento() != null)
			return null;
		
		return edital;
	}	
}