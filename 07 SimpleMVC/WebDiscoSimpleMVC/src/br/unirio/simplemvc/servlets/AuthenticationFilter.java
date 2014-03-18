package br.unirio.simplemvc.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Filtro de servlet que trata da autentica��o
 * 
 * @author Marcio Barros
 */
@SuppressWarnings("unchecked")
public class AuthenticationFilter implements Filter 
{
	/**
	 * Par�metro que identifica a classe de autentica��o
	 */
	private static final String AUTHENTICATION_SERVICE = "authService";

	/**
	 * Nome da classe de autentica��o
	 */
	private String authenticationServiceClassName;

	/**
	 * Servi�o de tratamento de usu�rios
	 */
	private static IUserService userService = null;

	/**
	 * Cria uma inst�ncia do servi�o de autentica��o
	 */
	private IUserService getAuthenticationService() throws ServletException
	{
		if (authenticationServiceClassName == null)
			return null;

		try 
		{
			Class<IUserService> clazz = (Class<IUserService>) Class.forName(authenticationServiceClassName);
			return clazz.newInstance();
		}
		catch (ClassNotFoundException e) {
			throw new ServletException("Classe de autentica��o n�o encontrada: " + e.getMessage());
		}
		catch (InstantiationException e) {
			throw new ServletException("Erro de instancia��o de classe de autentica��o: " + e.getMessage());
		}
		catch (IllegalAccessException e) {
			throw new ServletException("Acesso ilegal a classe de autentica��o: " + e.getMessage());
		}
	}

	/**
	 * Inicializa o filtro de servlet, capturando o nome da classe de autentica��o
	 */
	@Override
    public void init(FilterConfig config) throws ServletException 
    { 
		this.authenticationServiceClassName = config.getInitParameter(AUTHENTICATION_SERVICE);
    }

	/**
	 * Processa uma chamada ao filtro de servlet
	 */
	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
    {
    	HttpServletRequest request = (HttpServletRequest) req;
    	HttpServletResponse response = (HttpServletResponse) res;
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	if (userService == null)
    		userService = getAuthenticationService();
		
		if (userService != null)
			AuthenticationService.setCurrentUserService(request, response, userService);

		chain.doFilter(req, res);
    }

	/**
	 * Destroi o filtro de servlet
	 */
	@Override
	public void destroy() 
	{
	}
}