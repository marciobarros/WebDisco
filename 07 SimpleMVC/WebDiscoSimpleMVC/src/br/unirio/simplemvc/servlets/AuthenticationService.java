package br.unirio.simplemvc.servlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.simplemvc.utils.Conversion;
import br.unirio.simplemvc.utils.Crypto;
import br.unirio.simplemvc.utils.DateUtils;

/**
 * Servi�o de autentica��o do sistema
 * 
 * @author Marcio Barros
 */
public class AuthenticationService
{
	/**
	 * Chave de acesso ao usu�rio logado na mem�ria de requisi��o e nos cookies
	 */
    private static final String USER_KEY = "userid";
    
    /**
     * Classe de acesso ao servi�o de autentica��o na mem�ria de requisi��o
     */
    private static final String SERVICE_KEY = "authservice";
    
    /**
     * Chave de acesso ao usu�rio logado na mem�ria de sess�o
     */
    private static final String USER_SESSION_KEY = "loogedUser";

    /**
     * Retorna a string que representa um usu�rio
     */
    private static String getStringFromUser(IUser user)
    {
    	Date data = new Date();
    	return user.getId() + "#" + DateUtils.getYear(data) + "-" + DateUtils.getMonth(data) + "-" + DateUtils.getDay(data) + " " + DateUtils.getHour(data) + ":" + DateUtils.getMinute(data);
    }
    
    /**
     * Retorna o identificador de um usu�rio, dado sua chave
     */
    private static int getUserFromString(String s)
    {
    	if (s == null)
    		return -1;
    	
    	String[] tokens = s.split("#");
    	
    	if (tokens.length != 2)
    		return -1;
    	
    	try
		{
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        	Date dataCookie = sdf.parse(tokens[1]);
        	Date data = new Date();

			long difMinutos = (data.getTime() - dataCookie.getTime()) / (60 * 1000);
	    	
	    	if (difMinutos > 5)
	    		return -1;
	    	
		} catch (ParseException e)
		{
			return -1;
		}
   	
    	return Conversion.safeParseInteger(tokens[0], -1);
    }
    
    /**
     * Cria o cookie que armazena o identificador do usu�rio
     */
    private static void createUserCookie(HttpServletResponse response, IUser user)
    {
        String s = Crypto.reversibleEncryption(getStringFromUser(user));
        Cookie cookie = new Cookie(USER_KEY, s);
        cookie.setPath("/");
    	response.addCookie(cookie);
    }
    
    /**
     * Indica o usu�rio logado no sistema, salvando um cookie com seu identificador
     */
    public static void setCurrentUser(HttpServletRequest request, HttpServletResponse response, IUser user)
    {
    	if (user != null)
    	{
	        request.setAttribute(USER_KEY, user);
	        request.getSession().setAttribute(USER_SESSION_KEY, user);
	        createUserCookie(response, user);
    	}
    }
    
    /**
     * Retorna o usu�rio logado
     */
    public static IUser getUser(ServletRequest request)
    {    
    	return (IUser) request.getAttribute(USER_KEY);
    }
    
    /**
     * Indica o servi�o de acesso a usu�rios que ser� usado na aplica��o
     */
    public static void setCurrentUserService(HttpServletRequest request, HttpServletResponse response, IUserService service)
    {    
    	request.setAttribute(SERVICE_KEY, service);
		setCurrentUser(request, response, getLoggedUser(request, service));
    }
    
    /**
     * Retorna o servi�o de acesso a usu�rios
     */
    public static IUserService getUserService(ServletRequest request)
    {    
    	return (IUserService) request.getAttribute(SERVICE_KEY);
    }

    /**
     * Retorna o identificador do usu�rio logado no sistema por seu cookie
     */
	private static int getUserCookie (HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null)
			return -1;
		
		for (Cookie c : cookies)
			if (c.getName().compareTo(USER_KEY) == 0)
			{
				String s = c.getValue();
				s = Crypto.decrypt(s);
				return getUserFromString(s);
			}
		
        return -1;
	}
	
	/**
	 * Retorna o usu�rio logado no sistema
	 */
	private static IUser getLoggedUser (HttpServletRequest request, IUserService userService)
	{
		IUser user = (IUser) request.getSession().getAttribute(USER_SESSION_KEY);
		
		if (user != null)
			return user;
		
		int id = getUserCookie(request);
		
		if (id != -1)
		{
			IUser userCookie = userService.getUserId(id);
			if (userCookie != null) request.getSession().setAttribute(USER_SESSION_KEY, userCookie);
			return userCookie;
		}
		
        return null;
	}

	/**
	 * Invalida o usu�rio logado no sistema
	 */
	public static void invalidateCurrentUser(HttpServletRequest request, HttpServletResponse response) 
	{
		// Limpa a mem�ria de sess�o
		request.getSession().invalidate();
		
		// Remove o cookie que armazena os dados do usu�rio
        Cookie cookie = new Cookie(USER_KEY, null);
        cookie.setPath("/");
		response.addCookie(cookie);
		
		// Remove o usu�rio da mem�ria de requisi��o
		request.removeAttribute(USER_KEY);
	}
}