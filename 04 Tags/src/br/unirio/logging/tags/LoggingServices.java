/**
 * Classe respons�vel pelo tratamento do servi�o de login
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
	 * Inicializa o servi�o de tratamento de login - SINGLETON
	 */
	
	private LoggingServices ()
	{
	}

	
	/**
	 * Retorna a inst�ncia do servi�o de tratamento de login
	 */
	
	public static LoggingServices getInstance ()
	{
		if (instance == null)
			instance = new LoggingServices ();
		
		return (instance);
	}
	
	
	/**
	 * Retorna o usu�rio logado na aplica��o
	 *  
	 * @param session	Mem�ria de sess�o da aplica��o
	 */
	
	private GenericUser getLoggedUser (PageContext pc)
	{
		HttpSession session = pc.getSession();
		
        if (session == null)
        	return (null);

        GenericUser user = (GenericUser) session.getAttribute (USER_KEY);
        return (user);
	}
	
	
	/**
	 * Verifica se existe um usu�rio logado
	 * 
	 * @param pc		Contexto de p�gina da requisi��o
	 */

	public boolean isUserLogged (PageContext pc)
	{
		return (getLoggedUser(pc) != null);
	}
	
	
	/**
	 * Verifica se o usu�rio logado possui um determinado n�vel de acesso
	 * 
	 * @param pc		Contexto de p�gina da requisi��o
	 * @param level		N�vel de acesso desejado
	 */
	public boolean userHasLevel (PageContext pc, String level)
	{
		GenericUser user = getLoggedUser(pc);
		
		if (user == null)
			return (false);
		
		return (user.checkLevel(level));
	}
	
	
	/**
	 * Realiza o login de um usu�rio
	 * 
	 * @param pc		Contexto de p�gina da requisi��o
	 * @param user		Usu�rio que ficar� logado
	 */
	
	public void logUser (PageContext pc, GenericUser user)
	{
		HttpSession session = pc.getSession();
		
        if (session != null)
        	session.setAttribute (USER_KEY, user);
	}
	
	
	/**
	 * Realiza o logoff de um usu�rio
	 * 
	 * @param pc		Contexto de p�gina da requisi��o
	 */

	public void invalidateUser (PageContext pc)
	{
		HttpSession session = pc.getSession();
		
        if (session != null)
        	session.invalidate();
	}
}