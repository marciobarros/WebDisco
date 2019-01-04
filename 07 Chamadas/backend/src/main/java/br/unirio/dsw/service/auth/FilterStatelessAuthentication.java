package br.unirio.dsw.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.unirio.dsw.model.Usuario;

/**
 * Classe que carrega o header com as credenciais do usuário e coloca como principal no Spring Security
 * 
 * @author Marcio
 */
class FilterStatelessAuthentication extends GenericFilterBean
{
	/**
	 * Serviço de geração de tokens para autenticação de usuário
	 */
	private final TokenAuthenticationService tokenAuthenticationService;
	
	/**
	 * Serviço de acesso aos dados de usuários pelos mecanismos de segurança
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * Inicializa o filtro de servlet com o serviço de geração de tokens de autenticação
	 */
	FilterStatelessAuthentication(TokenAuthenticationService tokenService, UserDetailsService userDetailsService)
	{
		this.tokenAuthenticationService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Executa o filtro, capturando o header e indicando o usuário logado no Spring Security
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		
		String token = request.getHeader(Constants.AUTH_HEADER_NAME);
		
		if (token != null)
		{
			String email = tokenAuthenticationService.parseUserToken(token);

			if (email != null)
			{
				Usuario user = (Usuario) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				
				if (user == null || user.getEmail().compareTo(email) != 0)
					user = userDetailsService.loadUserByUsername(email);					
				
				if (user != null)
				{
					UserAuthentication auth = new UserAuthentication(user);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		
		chain.doFilter(req, res);
	}
}