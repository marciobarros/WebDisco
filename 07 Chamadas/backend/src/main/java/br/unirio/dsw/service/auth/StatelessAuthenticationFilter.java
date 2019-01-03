package br.unirio.dsw.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Classe que carrega o header com as credenciais do usuário e coloca como principal no Spring Security
 * 
 * @author Marcio
 */
class StatelessAuthenticationFilter extends GenericFilterBean
{
	/**
	 * Serviço de geração de tokens para autenticação de usuário
	 */
	private final TokenAuthenticationService tokenAuthenticationService;

	/**
	 * Inicializa o filtro de servlet com o serviço de geração de tokens de autenticação
	 */
	StatelessAuthenticationFilter(TokenAuthenticationService service)
	{
		this.tokenAuthenticationService = service;
	}

	/**
	 * Executa o filtro, capturando o header e indicando o usuário logado no Spring Security
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
		chain.doFilter(req, res);
	}
}