package br.unirio.dsw.controller;

import java.awt.TrayIcon.MessageType;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Account;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import br.unirio.dsw.service.dao.UsuarioDAO;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@Controller
public class BaseController 
{
	/**
	 * Ação que redireciona o usuário para a página inicial da aplicação
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mostraLoginRaiz()
	{
		return "login/index";
	}
	
	/**
	 * Ação que redireciona o usuário para a página inicial da aplicação
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String mostraLogin()
	{
		return "login/index";
	}
	
	/**
	 * Ação que redireciona o usuário para a página de criação de conta
	 */
	@RequestMapping(value = "/login/new", method = RequestMethod.GET)
	public String mostraPaginaCriacaoConta()
	{
		return "login/create";
	}

	/**
	 * Ação que redireciona o usuário para a página inicial da aplicação
	 */
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public String mostraHomepage()
	{
		return "homepage/index";
	}

	/**
	 * Ação que redireciona o usuário para a página de edição de unidades
	 */
	@RequestMapping(value = "/unidade", method = RequestMethod.GET)
	public String mostraUnidades()
	{
		return "unidade/index";
	}

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public void signin() 
	{
	}
    
//    @Autowired
//	private UsuarioDAO userDAO;
//
//	private final ProviderSignInUtils providerSignInUtils;
//
//	@Inject
//	public BaseController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) 
//	{
//		this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//	}
//
//	@RequestMapping(value="/signup", method=RequestMethod.GET)
//	public SignupForm signupForm(WebRequest request) {
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
//		if (connection != null) {
//			request.setAttribute("message", new Message(MessageType.INFO, "Your " + StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social Showcase account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
//			return SignupForm.fromProviderUser(connection.fetchUserProfile());
//		} else {
//			return new SignupForm();
//		}
//	}
//
//	@RequestMapping(value="/signup", method=RequestMethod.POST)
//	public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
//		if (formBinding.hasErrors()) {
//			return null;
//		}
//		Account account = createAccount(form, formBinding);
//		if (account != null) {
//			SignInUtils.signin(account.getUsername());
//			providerSignInUtils.doPostSignUp(account.getUsername(), request);
//			return "redirect:/";
//		}
//		return null;
//	}
//
//	// internal helpers
//	
//	private Account createAccount(SignupForm form, BindingResult formBinding) {
//		try {
//			Account account = new Account(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName());
//			accountRepository.createAccount(account);
//			return account;
//		} catch (UsernameAlreadyInUseException e) {
//			formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
//			return null;
//		}
//	}
}