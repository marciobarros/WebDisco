package br.unirio.inscricaoppgi.dao;

import java.util.Date;
import java.util.List;

import br.unirio.inscricaoppgi.model.InscricaoEdital;
import br.unirio.inscricaoppgi.model.LinhaPesquisa;
import br.unirio.inscricaoppgi.model.TopicoInteresse;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Classe que trata da persistência de inscrições em editais
 * 
 * @author Marcio
 */
public class InscricaoEditalDAO extends AbstractDAO<InscricaoEdital>
{
	/**
	 * Inicializa a classe
	 */
	protected InscricaoEditalDAO()
	{
		super("InscricaoEdital");
	}

	/**
	 * Carrega os dados de uma inscrição em edital
	 */
	@Override
	protected InscricaoEdital load(Entity e)
	{
		InscricaoEdital inscricao = new InscricaoEdital();
		inscricao.setId((int)e.getKey().getId());
		inscricao.setIdCandidato(getIntProperty(e, "idCandidato", -1));
		inscricao.setIdEdital(getIntProperty(e, "idEdital", -1));
		inscricao.setDataInscricao(getDateProperty(e, "data", new Date()));
		inscricao.setLinhaPesquisaPrincipal(LinhaPesquisa.get(getStringProperty(e, "linhaPesquisa", "")));
		inscricao.setLinhaPesquisaSecundaria(LinhaPesquisa.get(getStringProperty(e, "linhaSecundaria", "")));
		inscricao.setObservacoes(getTextProperty(e, "observacoes", ""));
		inscricao.setInscricaoVerificada(getBooleanProperty(e, "inscricaoVerificada", false));
		inscricao.setDocumentosPendente(getBooleanProperty(e, "documentosPendentes", false));
		carregaTopicos(inscricao, getTextProperty(e, "topicos", ""));
		return inscricao;
	}
	
	/**
	 * Carrega os tópicos de uma inscrição em edital
	 */
	private void carregaTopicos(InscricaoEdital inscricao, String jsonTopicos)
	{
	    JsonParser parser = new JsonParser();
	    JsonArray array = parser.parse(jsonTopicos).getAsJsonArray();
	    
	    for (int i = 0; i < array.size(); i++)
	    {
	    	JsonElement obj = array.get(i);
	    	TopicoInteresse topico = (TopicoInteresse) new Gson().fromJson(obj, TopicoInteresse.class);
	    	inscricao.adicionaTopicoInteresse(topico);
	    }
	}

	/**
	 * Salva os dados de uma inscrição em edital
	 */
	@Override
	protected void save(InscricaoEdital inscricao, Entity e)
	{
		e.setProperty("idCandidato", inscricao.getIdCandidato());
		e.setProperty("idEdital", inscricao.getIdEdital());
		e.setProperty("data", inscricao.getDataInscricao());
		e.setProperty("linhaPesquisa", inscricao.getLinhaPesquisaPrincipal().getCodigo());
		e.setProperty("linhaSecundaria", inscricao.getLinhaPesquisaSecundaria().getCodigo());
		e.setProperty("observacoes", new Text(inscricao.getObservacoes()));
		e.setProperty("inscricaoVerificada", inscricao.getInscricaoVerificada());
		e.setProperty("documentosPendentes", inscricao.getDocumentosPendente());
		
		String topicos = new Gson().toJson(inscricao.getTopicos());
		e.setProperty("topicos", new Text(topicos));
	}
	
	/**
	 * Carrega a inscrição de um usuário em um edital
	 */
	public InscricaoEdital carrega(int idUsuario, int idEdital)
	{
		return get(and(exactFilter("idCandidato", FilterOperator.EQUAL, idUsuario), exactFilter("idEdital", FilterOperator.EQUAL, idEdital)));
	}

	/**
	 * Carrega a lista de inscrições em um determinado edital
	 */
	public List<InscricaoEdital> pegaInscricoesEdital(int idEdital)
	{
		return list(exactFilter("idEdital", FilterOperator.EQUAL, idEdital));
	}
}