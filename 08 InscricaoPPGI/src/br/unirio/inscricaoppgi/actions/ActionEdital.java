package br.unirio.inscricaoppgi.actions;

import java.util.Date;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.Edital;
import br.unirio.inscricaoppgi.model.TipoUsuario;
import br.unirio.inscricaoppgi.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.json.JSONObject;

/**
 * A��es relacionadas aos editais de vagas
 * 
 * @author Marcio
 */
public class ActionEdital extends Action
{
	/**
	 * A��o para preparar a abertura de um edital
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/edital/confirmaAbertura.jsp")
	public String preparaAbertura() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para abrir o edital.");

		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital == null, "J� existe um edital aberto. N�o � poss�vel abrir um novo edital.");
		
		return SUCCESS;
	}

	/**
	 * A��o para abrir um edital
	 */
	@Error("/login/homepage.do")
	@SuccessRedirect("/login/homepage.do")
	public String abre() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para abrir o edital.");

		Edital ultimoEditalAberto = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(ultimoEditalAberto == null, "J� existe um edital aberto. N�o � poss�vel abrir outro edital.");

		Edital edital = new Edital();
		edital.setDataAbertura(new Date());

		DAOFactory.getEditalDAO().put(edital);
		addRedirectNotice("edital.aberto.sucesso");
		return SUCCESS;
	}

	/**
	 * A��o para preparar o fechamento de um edital
	 */
	@Error("/login/homepage.do")
	@Success("/jsp/edital/confirmaFechamento.jsp")
	public String preparaFechamento() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para fechar o edital.");

		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital aberto no sistema. N�o � poss�vel fechar o edital.");
		setAttribute("edital", edital);
		
		return SUCCESS;
	}

	/**
	 * A��o para fechar um edital previamente aberto
	 */
	@Error("/login/homepage.do")
	@SuccessRedirect("/login/homepage.do")
	public String fecha() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		check(usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR, "O usu�rio deve ser administrador para abrir o edital.");

		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		check(edital != null, "N�o existe um edital aberto. N�o � poss�vel fechar outro edital.");

		edital.setDataFechamento(new Date());
		DAOFactory.getEditalDAO().put(edital);

		addRedirectNotice("edital.fechado.sucesso");
		return SUCCESS;
	}

	/**
	 * A��o AJAX que determina se existe um edital em aberto
	 */
	@DisableUserVerification
	@Ajax
	public String temEditalAberto() throws ActionException
	{
		Edital edital = DAOFactory.getEditalDAO().pegaUltimoEditalAberto();
		boolean temEdital = (edital != null);
		JSONObject jsonResultado = new JSONObject().add("result", temEdital);
		return ajaxSuccess(jsonResultado);
	}
}