/**
 * Classe responsável pelo tratamento do serviço de login
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
	 * Inicializa o serviço de tratamento de login - SINGLETON
	 */
	
	private LoggingServices ()
	{
	}

	
	/**
	 * Retorna a instância do serviço de tratamento de login
	 */
	
	public static LoggingServices getInstance ()
	{
		if (instance == null)
			instance = new LoggingServices ();
		
		return (instance);
	}
	
	
	/**
	 * Retorna o usuário logado na aplicação
	 *  
	 * @param session	Memória de sessão da aplicação
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
	 * Verifica se existe um usuário logado
	 * 
	 * @param pc		Contexto de página da requisição
	 */

	public boolean isUserLogged (PageContext pc)
	{
		return (getLoggedUser(pc) != null);
	}
	
	
	/**
	 * Verifica se o usuário logado possui um determinado nível de acesso
	 * 
	 * @param pc		Contexto de página da requisição
	 * @param level		Nível de acesso desejado
	 */
	public boolean userHasLevel (PageContext pc, String level)
	{
		GenericUser user = getLoggedUser(pc);
		
		if (user == null)
			return (false);
		
		return (user.checkLevel(level));
	}
	
	
	/**
	 * Realiza o login de um usuário
	 * 
	 * @param pc		Contexto de página da requisição
	 * @param user		Usuário que ficará logado
	 */
	
	public void logUser (PageContext pc, GenericUser user)
	{
		HttpSession session = pc.getSession();
		
        if (session != null)
        	session.setAttribute (USER_KEY, user);
	}
	
	
	/**
	 * Realiza o logoff de um usuário
	 * 
	 * @param pc		Contexto de página da requisição
	 */

	public void invalidateUser (PageContext pc)
	{
		HttpSession session = pc.getSession();
		
        if (session != null)
        	session.invalidate();
	}
}