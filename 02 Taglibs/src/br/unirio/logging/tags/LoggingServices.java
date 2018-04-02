/**
 * Classe responsavel pelo tratamento do servico de login
 * 
 *  @author marcio.barros
 *  @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public class LoggingServices
{
	private static final String USER_KEY = "usuario";

	private static LoggingServices instance = null;

	/**
	 * Inicializa o servico de tratamento de login - SINGLETON
	 */
	private LoggingServices()
	{
	}

	/**
	 * Retorna a instancia do servico de tratamento de login
	 */
	public static LoggingServices getInstance()
	{
		if (instance == null)
			instance = new LoggingServices();

		return instance;
	}

	/**
	 * Retorna o usuario logado na aplicacao
	 */
	private GenericUser getLoggedUser(PageContext pc)
	{
		HttpSession session = pc.getSession();

		if (session == null)
			return null;

		return (GenericUser) session.getAttribute(USER_KEY);
	}

	/**
	 * Verifica se existe um usuario logado
	 */
	public boolean isUserLogged(PageContext pc)
	{
		return getLoggedUser(pc) != null;
	}

	/**
	 * Verifica se o usuario logado possui um determinado nivel de acesso
	 */
	public boolean userHasLevel(PageContext pc, String level)
	{
		GenericUser user = getLoggedUser(pc);

		if (user == null)
			return false;

		return user.checkLevel(level);
	}

	/**
	 * Realiza o login de um usuario
	 */
	public void logUser(PageContext pc, GenericUser user)
	{
		HttpSession session = pc.getSession();

		if (session != null)
			session.setAttribute(USER_KEY, user);
	}

	/**
	 * Realiza o logoff de um usuario
	 */
	public void invalidateUser(PageContext pc)
	{
		HttpSession session = pc.getSession();

		if (session != null)
			session.invalidate();
	}
}