package br.unirio.dsw.service.auth;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.unirio.dsw.model.Usuario;

/**
 * Classe responsável por responder ao comando de LOGIN. Uma instância desta classe é registrada no Spring
 * Security junto a URL de login, sendo chamada quando a URL é buscada no servidor
 * 
 * @author Marcio
 */
class FilterStatelessLogin extends AbstractAuthenticationProcessingFilter
{
	/**
	 * Chave de acesso ao e-mail do usuário que está tentando se logar
	 */
	private static final String ATTEMPTING_USER_EMAIL_KEY = "email";  
	
	/**
	 * Serviço de geração de tokens para usuários logados
	 */
	private final TokenAuthenticationService tokenAuthenticationService;
	
	/**
	 * Serviço de acesso aos dados de usuários pelos mecanismos de segurança
	 */
	private final UserDetailsService userDetailsService;

	/**
	 * Inicializa o filtro de servlet
	 */
	FilterStatelessLogin(String urlMapping, TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService, AuthenticationManager authManager)
	{
		super(new AntPathRequestMatcher(urlMapping));
		this.tokenAuthenticationService = tokenAuthenticationService;
		this.userDetailsService = userDetailsService;
		setAuthenticationManager(authManager);
	}

	/**
	 * Recebe o email e a senha do usuário e tenta fazer um login 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException
	{
		// Pega a string com email e senha
		InputStream is = request.getInputStream();
		String content = IOUtils.toString(is, "ascii");
		
		// Transforma a string recebida em JSON
		JsonParser parser = new JsonParser();
		JsonObject jsonLogin = parser.parse(content).getAsJsonObject();
		
		// Captura o email
		JsonElement jsonEmail = jsonLogin.get("email");
		String email = (jsonEmail != null) ? jsonEmail.getAsString() : "";
		
		// Captura a senha
		JsonElement jsonSenha = jsonLogin.get("senha");
		String senha = (jsonSenha != null) ? jsonSenha.getAsString() : "";
		
		// Limpa a memória de sessão
		request.getSession().invalidate();
		
		// Guarda o email na requisição para casos de falha de login
		request.setAttribute(ATTEMPTING_USER_EMAIL_KEY, email);

		// Tenta fazer o login
		UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(email, senha);
		return getAuthenticationManager().authenticate(loginToken);
	}

	/**
	 * Método chamado caso o login termine com sucesso - retorna uma requisição de sucesso 200
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException
	{
		// Registra o login com sucesso
		userDetailsService.registraLoginSucesso(authentication.getName());
		
		// Pega o usuário que logou no sistema
		Usuario user = userDetailsService.loadUserByUsername(authentication.getName());
		
		// Salva o usuário na memória de sessão
		request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
		
		// Adiciona o token do usuário na resposta
		String token = tokenAuthenticationService.createAuthenticationToken(user.getEmail());
		response.addHeader(Constants.AUTH_HEADER_NAME, token);
		
		// Cria e registra uma autenticação para o usuário
		UserAuthentication userAuthentication = new UserAuthentication(user);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}

	/**
	 * Método chamado caso o login termine com falha - retorna um erro 403
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
	{
		// Pega o email do usuário na memória de requisição 
		String email = (String) request.getAttribute(ATTEMPTING_USER_EMAIL_KEY);

		// Registra o login com falha
		userDetailsService.registraLoginFalha(email);
		
		// Adiciona um header na resposta com o motivo da falha
		response.setHeader(Constants.ERROR_HEADER_NAME, failed.getLocalizedMessage());

		// Chama a superclasse
		super.unsuccessfulAuthentication(request, response, failed);
	}
}