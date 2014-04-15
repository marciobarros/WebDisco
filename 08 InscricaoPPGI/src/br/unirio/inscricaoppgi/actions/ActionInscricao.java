package br.unirio.inscricaoppgi.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.Edital;
import br.unirio.inscricaoppgi.model.InscricaoEdital;
import br.unirio.inscricaoppgi.model.LinhaPesquisa;
import br.unirio.inscricaoppgi.model.TipoUsuario;
import br.unirio.inscricaoppgi.model.TopicoInteresse;
import br.unirio.inscricaoppgi.model.TopicoPesquisa;
import br.unirio.inscricaoppgi.model.Usuario;
import br.unirio.inscricaoppgi.reports.inscritos.FiltroInscritos;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.json.JSONArray;
import br.unirio.simplemvc.json.JSONObject;

import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Classe que implementa as a��es relacionadas com inscri��es em editais
 * 
 * @author Marcio
 */
public class ActionInscricao extends Action
{
	/**
	 * Prepara os dados da inscri��o para serem editados
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/inscricao/form.jsp")
	public String prepara() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		
		if (inscricao == null)
		{
			inscricao = new InscricaoEdital();
			inscricao.setIdCandidato(usuario.getId());
			inscricao.setIdEdital(edital.getId());
		}
		
		setAttribute("inscricao", inscricao);
		return SUCCESS;
	}
	
	/**
	 * Salva os dados da inscri��o em um edital
	 */
	@Error("/jsp/inscricao/form.jsp")
	@SuccessRedirect("/inscricao/prepara.do")
	public String salva() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		
		if (inscricao == null)
		{
			inscricao = new InscricaoEdital();
			inscricao.setIdCandidato(usuario.getId());
			inscricao.setIdEdital(edital.getId());
		}
		
		String codigoLinhaPrincipal = getParameter("linhaPrincipal", "");
		String codigoLinhaSecundaria = getParameter("linhaSecundaria", "");
		
		setAttribute("inscricao", inscricao);
		inscricao.setDataInscricao(new Date());
		inscricao.setLinhaPesquisaPrincipal(LinhaPesquisa.get(codigoLinhaPrincipal));
		inscricao.setLinhaPesquisaSecundaria(LinhaPesquisa.get(codigoLinhaSecundaria));
		inscricao.setObservacoes(getParameter("observacoes", ""));

		check(inscricao.getLinhaPesquisaPrincipal() != null, "Selecione uma linha de pesquisa principal para a sua inscri��o");
		check(inscricao.getLinhaPesquisaSecundaria() != null, "Selecione uma linha de pesquisa secund�ria para a sua inscri��o");
		check(inscricao.getLinhaPesquisaSecundaria() != inscricao.getLinhaPesquisaPrincipal(), "A linha de pesquisa secund�ria deve ser diferente da linha principal");

		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		addRedirectNotice("inscricao.salva.sucesso");
		return SUCCESS;
	}
	
	/**
	 * Gera a representa��o JSON dos t�picos de interesse registrados na inscri��o
	 */
	private JSONArray geraRepresentacaoTopicos(InscricaoEdital inscricao)
	{
		JSONArray resultado = new JSONArray();
		
		for (int i = 0; i < inscricao.getNumeroTopicosInteresse(); i++)
		{
			TopicoInteresse topico = inscricao.pegaTopicoInteresseIndice(i);
			JSONObject jsonTopico = new JSONObject();
			jsonTopico.add("indice", topico.getTopico().getCodigo());
			jsonTopico.add("codigo", topico.getTopico().getCodigo());
			jsonTopico.add("nome", topico.getTopico().getNome());
			jsonTopico.add("descricao", topico.getDescricao());
			resultado.add(jsonTopico);
		}
		
		return resultado;
	}

	/**
	 * Lista os t�picos de interesse associados a uma inscri��o
	 */
	@Ajax
	public String listaTopicos() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		JSONArray resultado = (inscricao == null) ? new JSONArray() : geraRepresentacaoTopicos(inscricao);
		return ajaxSuccess(resultado);
	}
	
	/**
	 * Adiciona ou edita um t�pico de interesse em uma inscri��o
	 */
	@Ajax
	public String salvaTopico() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		check(inscricao != null, "O usu�rio ainda n�o est� inscrito em um edital de vagas.");

		String codigoTopico = getParameter("topicoPesquisa", "");
		TopicoPesquisa topicoPesquisa = TopicoPesquisa.get(codigoTopico);
		check(topicoPesquisa != null, "Selecione o t�pico de pesquisa do seu interesse.");
		
		String comentario = getParameter("comentario", "");
		check(comentario.length() > 0, "Entre com uma breve descri��o dos seus interesses no t�pico.");
		
		inscricao.adicionaTopicoInteresse(new TopicoInteresse(topicoPesquisa, comentario));
		DAOFactory.getInscricaoEditalDAO().put(inscricao);

		return ajaxSuccess(geraRepresentacaoTopicos(inscricao));
	}
	
	/**
	 * Remove um t�pico de interesse da inscri��o em um edital
	 */
	@Ajax
	public String removeTopico() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		check(inscricao != null, "O usu�rio ainda n�o est� inscrito em um edital de vagas.");
		
		int indice = getIntParameter("indice", -1);
		check(indice >= 0 && indice < inscricao.getNumeroTopicosInteresse(), "Selecione o �ndice do t�pico de interesse que deseja remover");
	
		inscricao.removeTopicoInteresse(indice);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess(geraRepresentacaoTopicos(inscricao));
	}
	
	/**
	 * Sobe um t�pico de interesse na inscri��o em um edital
	 */
	@Ajax
	public String sobeTopico() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		check(inscricao != null, "O usu�rio ainda n�o est� inscrito em um edital de vagas.");
		
		int indice = getIntParameter("indice", -1);
		check(indice > 0 && indice < inscricao.getNumeroTopicosInteresse(), "Selecione o �ndice do t�pico de interesse que deseja subir.");
	
		inscricao.sobeTopicoInteresse(indice);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess(geraRepresentacaoTopicos(inscricao));
	}
	
	/**
	 * Desce um t�pico de interesse na inscri��o em um edital
	 */
	@Ajax
	public String desceTopico() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.CANDIDATO, "O usu�rio deve ser candidato para se inscrever em um edital.");
		
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital de vagas aberto.");

		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().carrega(usuario.getId(), edital.getId());
		check(inscricao != null, "O usu�rio ainda n�o est� inscrito em um edital de vagas.");
		
		int indice = getIntParameter("indice", -1);
		check(indice >= 0 && indice < inscricao.getNumeroTopicosInteresse()-1, "Selecione o �ndice do t�pico de interesse que deseja descer.");
	
		inscricao.desceTopicoInteresse(indice);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess(geraRepresentacaoTopicos(inscricao));
	}
	
	/**
	 * Valida a documenta��o da inscri��o de um candidato
	 */
	@Ajax
	public String validaDocumentacao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para validar os documentos de um candidato.");
		
		int idInscricao = getIntParameter("idInscricao", -1);
		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().get(idInscricao);
		check(inscricao != null, "A inscri��o selecionada n�o foi encontrada no sistema.");

		inscricao.setDocumentosPendente(false);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess();
	}
	
	/**
	 * Invalida a documenta��o da inscri��o de um candidato
	 */
	@Ajax
	public String invalidaDocumentacao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para validar os documentos de um candidato.");
		
		int idInscricao = getIntParameter("idInscricao", -1);
		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().get(idInscricao);
		check(inscricao != null, "A inscri��o selecionada n�o foi encontrada no sistema.");

		inscricao.setDocumentosPendente(true);
		inscricao.setInscricaoVerificada(false);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess();
	}
	
	/**
	 * Valida a inscri��o de um candidato
	 */
	@Ajax
	public String validaInscricao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para validar os documentos de um candidato.");
		
		int idInscricao = getIntParameter("idInscricao", -1);
		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().get(idInscricao);
		check(inscricao != null, "A inscri��o selecionada n�o foi encontrada no sistema.");

		check(!inscricao.getDocumentosPendente(), "A documenta��o deve ser validada antes da inscri��o.");

		inscricao.setInscricaoVerificada(true);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess();
	}
	
	/**
	 * Invalida a inscri��o de um candidato
	 */
	@Ajax
	public String invalidaInscricao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para validar os documentos de um candidato.");
		
		int idInscricao = getIntParameter("idInscricao", -1);
		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().get(idInscricao);
		check(inscricao != null, "A inscri��o selecionada n�o foi encontrada no sistema.");

		inscricao.setInscricaoVerificada(false);
		DAOFactory.getInscricaoEditalDAO().put(inscricao);
		return ajaxSuccess();
	}
	
	/**
	 * Gera a ficha de uma inscri��o
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/inscricao/ficha.jsp")
	public String geraFicha() throws ActionException
	{
		checkLogged();
		
		int idInscricao = getIntParameter("idInscricao", -1);
		InscricaoEdital inscricao = DAOFactory.getInscricaoEditalDAO().get(idInscricao);
		check(inscricao != null, "A inscri��o selecionada n�o foi encontrada no sistema.");

		Usuario candidato = DAOFactory.getUsuarioDAO().get(inscricao.getIdCandidato());
		check(candidato != null, "O usu�rio selecionado n�o foi encontrado no sistema.");
		
		setAttribute("usuario", candidato);
		setAttribute("inscricao", inscricao);
		return SUCCESS;
	}
	
	/**
	 * A��o que prepara a execu��o do relat�rio de inscritos
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/inscricao/filtroRelatorioInscritos.jsp")
	public String preparaRelatorioInscritos() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para visualizar o relat�rio de inscritos.");
		
		List<Edital> editais = DAOFactory.getEditalDAO().list("dataAbertura", SortDirection.DESCENDING);
		String[] nomeEdital = new String[editais.size()]; 
		String[] valorEdital = new String[editais.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i = 0; i < editais.size(); i++)
		{
			Edital edital = editais.get(i);
			valorEdital[i] = "" + edital.getId();
			
			if (edital.getDataFechamento() != null)
				nomeEdital[i] = sdf.format(edital.getDataAbertura()) + " - " + sdf.format(edital.getDataFechamento());
			else
				nomeEdital[i] = "Aberto desde " + sdf.format(edital.getDataAbertura());
		}
		
		setAttribute("nomeEdital", nomeEdital);
		setAttribute("valorEdital", valorEdital);
		return SUCCESS;
	}
	
	/**
	 * A��o que executa o relat�rio de inscritos
	 */
	@Error("/jsp/login/filtroRelatorioInscritos.jsp")
	@Success("/jsp/inscricao/relatorioInscritos.jsp")
	public String executaRelatorioInscritos() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para visualizar o relat�rio de inscritos.");

		String filtroNome = getParameter("filtroNomeCandidato", "");
		LinhaPesquisa filtroLinha = LinhaPesquisa.get(getParameter("filtroLinhaPesquisa", ""));
		int filtroEdital = getIntParameter("filtroEdital",  -1);
		int filtroDocumentacao = getIntParameter("filtroDocumentacao", -1);
		int filtroInscricao = getIntParameter("filtroInscricao", -1);
		setAttribute("resultado", new FiltroInscritos().executa(filtroNome, filtroLinha, filtroEdital, filtroDocumentacao, filtroInscricao));
		return SUCCESS;
	}
	
	/**
	 * A��o que prepara a execu��o do relat�rio de sele��o
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/inscricao/filtroRelatorioSelecao.jsp")
	public String preparaRelatorioSelecao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR || usuario.getTipoUsuario() == TipoUsuario.PROFESSOR, "O usu�rio deve ser administrador ou professor para visualizar o relat�rio de sele��o.");
		
		List<Edital> editais = DAOFactory.getEditalDAO().list("dataAbertura", SortDirection.DESCENDING);
		String[] nomeEdital = new String[editais.size()]; 
		String[] valorEdital = new String[editais.size()];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i = 0; i < editais.size(); i++)
		{
			Edital edital = editais.get(i);
			valorEdital[i] = "" + edital.getId();
			
			if (edital.getDataFechamento() != null)
				nomeEdital[i] = sdf.format(edital.getDataAbertura()) + " - " + sdf.format(edital.getDataFechamento());
			else
				nomeEdital[i] = "Aberto desde " + sdf.format(edital.getDataAbertura());
		}
		
		setAttribute("nomeEdital", nomeEdital);
		setAttribute("valorEdital", valorEdital);
		return SUCCESS;
	}
	
	/**
	 * A��o que executa o relat�rio de sele��o
	 */
	@Error("/jsp/login/filtroRelatorioSelecao.jsp")
	@Success("/jsp/inscricao/relatorioSelecao.jsp")
	public String executaRelatorioSelecao() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR || usuario.getTipoUsuario() == TipoUsuario.PROFESSOR, "O usu�rio deve ser administrador ou professor para visualizar o relat�rio de sele��o.");

		String filtroNome = getParameter("filtroNomeCandidato", "");
		LinhaPesquisa filtroLinha = LinhaPesquisa.get(getParameter("filtroLinhaPesquisa", ""));
		int filtroEdital = getIntParameter("filtroEdital",  -1);
		int filtroDocumentacao = getIntParameter("filtroDocumentacao", -1);
		int filtroInscricao = getIntParameter("filtroInscricao", -1);
		setAttribute("resultado", new FiltroInscritos().executa(filtroNome, filtroLinha, filtroEdital, filtroDocumentacao, filtroInscricao));
		return SUCCESS;
	}
}