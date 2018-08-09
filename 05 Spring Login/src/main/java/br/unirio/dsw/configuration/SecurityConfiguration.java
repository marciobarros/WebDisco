package br.unirio.dsw.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import br.unirio.dsw.service.dao.UsuarioDAO;

/**
 * Classe responsável pela configuração de segurança do Spring MVC
 * 
 * @author marciobarros
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
//    private static String REALM = "MY_TEST_REALM";

//    @Autowired
//    private ConnectionFactoryLocator connectionFactoryLocator;
// 
//    @Autowired
//    private UsersConnectionRepository usersConnectionRepository;
// 
//    @Autowired
//    private FacebookConnectionSignup facebookConnectionSignup;

//    @Bean
//    public ProviderSignInController providerSignInController() 
//    {
//        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
//        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new FacebookSignInAdapter());
//    }

	/**
	 * Indica os caminhos que serão ignorados pelos controle de segurança (arquivos CSS e JS)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/static/**");
	}

	/**
	 * Configura o processo de autenticação
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		PasswordEncoder encoder = passwordEncoder();
		
		UserDetailsService userDetailService = userDetailsService();
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
		
		AuthenticationService authenticationService = authenticationService();
		authenticationService.setPasswordEncoder(encoder);
		auth.authenticationProvider(authenticationService);
	}

	/**
	 * Configura o acesso às diversas partes da aplicação
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// Configures form login
		http.csrf().disable()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login/authenticate")
			.defaultSuccessUrl("/login/success")
			.failureUrl("/login/error")
			
			// Configures the logout function
			.and()
			.logout()
			.deleteCookies("JSESSIONID")
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			
			// Configures url based authorization
			.and()
			.authorizeRequests()
			.antMatchers("/auth/**", "/login/**", "/signin/**", "/connect/**", "/signup/**").permitAll()
			.anyRequest().authenticated()
			
			.and()
            .apply(new SpringSocialConfigurer());
	}
    
	/**
	 * Retorna o objeto que codifica senhas na base local
	 */
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}

	/**
	 * Retorna o objeto que permite acesso aos dados dos usuários na base local
	 */
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new LocalAccountUserDetailsService();
	}

    @Bean
    public SocialUserDetailsService socialUserDetailsService() 
    {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }

	/**
	 * Retorna o objeto que realiza a autenticação
	 */
	@Bean
	public AuthenticationService authenticationService()
	{
		AuthenticationService auth = new AuthenticationService();
		auth.setUserDetailsService(userDetailsService());
		return auth;
	}
	
	/**
	 * Classe de acesso aos dados dos usuários na base local para o Spring Security
	 * 
	 * @author marciobarros
	 */
	private class LocalAccountUserDetailsService implements UserDetailsService 
	{
		@Autowired
		private UsuarioDAO userDAO;
		
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	    {
	        return userDAO.carregaUsuarioEmail(username);
	    }
	}
	
	/**
	 * Classe responsável pela autenticação de usuários
	 * 
	 * @author marciobarros
	 */
	private class AuthenticationService extends DaoAuthenticationProvider
	{
		@Autowired
		private UsuarioDAO userDAO;
	
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException
		{
			try
			{
				Authentication auth = super.authenticate(authentication);
				userDAO.registraLoginSucesso(authentication.getName());
				return auth;
			} 
			catch (BadCredentialsException e)
			{
				userDAO.registraLoginFalha(authentication.getName());
				throw e;
			} 
			catch (LockedException e)
			{
				throw e;
			}
		}
	}

	/**
	 * 
	 * @author marcio.barros
	 *
	 */
	private class SimpleSocialUserDetailsService implements SocialUserDetailsService
	{
		private UserDetailsService userDetailsService;

		public SimpleSocialUserDetailsService(UserDetailsService userDetailsService)
		{
			this.userDetailsService = userDetailsService;
		}

		@Override
		public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException
		{
			return (SocialUserDetails) userDetailsService.loadUserByUsername(userId);
		}
	}
}

//class FacebookSignInAdapter implements SignInAdapter 
//{
//    @Override
//    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) 
//    {
//    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("FACEBOOK_USER");
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(connection.getDisplayName(), null, Arrays.asList(authority));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//        return null;
//    }
//}

//@Service
//class FacebookConnectionSignup implements ConnectionSignUp
//{
//	@Autowired
//	private UsuarioDAO userDAO;
//	
//	@Override
//	public String execute(Connection<?> connection) 
//	{
//		Usuario usuario = new Usuario(connection.getDisplayName(), connection.getDisplayName(), "FACEBOOK_SOURCE", false);
//		userDAO.criaNovoUsuario(usuario);
//		return usuario.getNome();
//	}
//}