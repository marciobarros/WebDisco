package br.unirio.dsw.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe que configura o Spring Security
 * 
 * @author Marcio
 */
@EnableWebSecurity
@Configuration
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter
{
	/**
	 * Serviço de acesso aos dados dos usuários pelos mecanismos de segurança
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Serviço de geração de tokens para usuários
	 */
	private TokenAuthenticationService tokenAuthenticationService;

	/**
	 * Inicializa o configurador dos mecanismos de segurança
	 */
	public StatelessAuthenticationSecurityConfig()
	{
		super(true);
		String secretKey = br.unirio.dsw.service.configuration.Configuration.getChaveSecretaTokenAutenticacao();
		this.tokenAuthenticationService = new TokenAuthenticationService(secretKey);
	}

	/**
	 * Configura a segurança para a aplicação
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.exceptionHandling().and()
				.anonymous().and()
				.servletApi().and()
				.authorizeRequests()

				// acesso sem login
				.antMatchers(HttpMethod.POST, "/login/create").permitAll()
				.antMatchers(HttpMethod.POST, "/login/forgot").permitAll()
				.antMatchers(HttpMethod.POST, "/login/reset").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()

				// acesso para administradores
				.antMatchers("/admin/**").hasRole("ADMIN")

				// acesso para usuários comuns
				.anyRequest().hasRole("BASIC").and()

				// filtro de login
				.addFilterBefore(new FilterStatelessLogin("/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

				// filter de reconhecimento de token
				.addFilterBefore(new FilterStatelessAuthentication(tokenAuthenticationService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Configura os recursos de acesso aos usuários
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	/**
	 * Retorna o objeto responsável pela criptografia one-way de senhas
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	/**
	 * Retorna o objeto de acesso aos dados dos usuários para a segurança
	 */
	@Override
	protected UserDetailsService userDetailsService()
	{
		return userDetailsService;
	}
}