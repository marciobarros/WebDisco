package br.unirio.simplemvc.servlets;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.unirio.simplemvc.utils.Conversion;
import br.unirio.simplemvc.utils.Crypto;

/**
 * Serviço de autenticação do sistema
 * 
 * @author Marcio Barros
 */
public class AuthenticationService
{
	/**
	 * Chave de acesso ao usuário logado na memória de requisição e nos cookies
	 */
    private static final String USER_KEY = "userid";
    
    /**
     * Classe de acesso ao serviço de autenticação na memória de requisição
     */
    private static final String SERVICE_KEY = "authservice";
    
    /**
     * Chave de acesso ao usuário logado na memória de sessão
     */
    private static final String USER_SESSION_KEY = "loogedUser";

    /**
     * Retorna a string que representa um usuário
     */
    private static String getStringFromUser(IUser user)
    {
    	DateTime data = DateTime.now();
    	return user.getId() + "#" + data.getYear() + "-" + data.getMonthOfYear() + "-" + data.getDayOfMonth() + " " + data.getHourOfDay() + ":" + data.getMinuteOfHour();
    }
    
    /**
     * Retorna o identificador de um usuário, dado sua chave
     */
    private static int getUserFromString(String s)
    {
    	if (s == null)
    		return -1;
    	
    	String[] tokens = s.split("#");
    	
    	if (tokens.length != 2)
    		return -1;
    	
		DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    	DateTime dataCookie = sdf.parseDateTime(tokens[1]);
    	DateTime data = DateTime.now();

		long difMinutos = (data.getMillis() - dataCookie.getMillis()) / (60 * 1000);
    	
    	if (difMinutos > 5)
    		return -1;
   	
    	return Conversion.safeParseInteger(tokens[0], -1);
    }
    
    /**
     * Cria o cookie que armazena o identificador do usuário
     */
    private static void createUserCookie(HttpServletResponse response, IUser user)
    {
        String s = Crypto.reversibleEncryption(getStringFromUser(user));
        Cookie cookie = new Cookie(USER_KEY, s);
        cookie.setPath("/");
    	response.addCookie(cookie);
    }
    
    /**
     * Indica o usuário logado no sistema, salvando um cookie com seu identificador
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
     * Retorna o usuário logado
     */
    public static IUser getUser(ServletRequest request)
    {    
    	return (IUser) request.getAttribute(USER_KEY);
    }
    
    /**
     * Indica o serviço de acesso a usuários que será usado na aplicação
     */
    public static void setCurrentUserService(HttpServletRequest request, HttpServletResponse response, IUserService service)
    {    
    	request.setAttribute(SERVICE_KEY, service);
		setCurrentUser(request, response, getLoggedUser(request, service));
    }
    
    /**
     * Retorna o serviço de acesso a usuários
     */
    public static IUserService getUserService(ServletRequest request)
    {    
    	return (IUserService) request.getAttribute(SERVICE_KEY);
    }

    /**
     * Retorna o identificador do usuário logado no sistema por seu cookie
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
	 * Retorna o usuário logado no sistema
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
	 * Invalida o usuário logado no sistema
	 */
	public static void invalidateCurrentUser(HttpServletRequest request, HttpServletResponse response) 
	{
		// Limpa a memória de sessão
		request.getSession().invalidate();
		
		// Remove o cookie que armazena os dados do usuário
        Cookie cookie = new Cookie(USER_KEY, null);
        cookie.setPath("/");
		response.addCookie(cookie);
		
		// Remove o usuário da memória de requisição
		request.removeAttribute(USER_KEY);
	}
}