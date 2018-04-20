package br.unirio.dsw.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.unirio.dsw.configuration.Configuration;
import br.unirio.dsw.model.usuario.Usuario;
import br.unirio.dsw.service.dao.UsuarioDAO;
import br.unirio.dsw.service.email.EmailService;
import br.unirio.dsw.utils.CryptoUtils;
import br.unirio.dsw.utils.ValidationUtils;
import br.unirio.dsw.view.login.ChangePasswordForm;
import br.unirio.dsw.view.login.ForgotPasswordForm;
import br.unirio.dsw.view.login.RegistrationForm;
import br.unirio.dsw.view.login.ResetPasswordForm;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@Controller
public class LoginController 
{
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    @Autowired
	private UsuarioDAO userDAO;
    
    @Autowired
	private EmailService emailService;
 
	/**
	 * Ação que redireciona o usuário para a página inicial da aplicação
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mostraHomepage(HttpServletRequest request)
	{
//		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "homepage/Index";
	}

	/**
	 * Ação que redireciona o usuário para a tela de login
	 */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView mostraPaginaLogin(@RequestParam(value = "error", required = false) String error, HttpServletRequest request) 
    {
		ModelAndView model = new ModelAndView();

		if (error != null)
			model.addObject("error", pegaMensagemErro(request, "SPRING_SECURITY_LAST_EXCEPTION"));

		model.setViewName("login/login");
		return model;
    }

    /**
     * Retorna a última mensagem de erro do processo de login
     */
	private String pegaMensagemErro(HttpServletRequest request, String key){

		Exception exception = (Exception) request.getSession().getAttribute(key);

		if (exception instanceof BadCredentialsException) 
			return "login.login.message.invalid.credentials";
		
		if (exception instanceof LockedException) 
			return "login.login.message.locked.account";

		return "login.login.message.invalid.credentials";
	}
	
    /**
     * Ação que redireciona o usuário para a tela de criação de conta
     */
	@RequestMapping(value = "/login/create", method = RequestMethod.GET)
	public ModelAndView mostraPaginaRegistro()
	{
		ModelAndView model = new ModelAndView();
        model.addObject("user", new RegistrationForm());
        model.setViewName("login/create");
		return model;
	}
	
	/**
	 * Ação que cria a conta de um novo usuário
	 */
	@RequestMapping(value = "/login/create", method = RequestMethod.POST)
    public String salvaNovoUsuario(@ModelAttribute("user") RegistrationForm form, BindingResult result, Locale locale) 
	{
		if (form.getName().length() == 0)
			result.addError(new FieldError("user", "name", messageSource.getMessage("login.new.account.error.name.empty", null, locale)));
		
		if (form.getEmail().length() == 0)
			result.addError(new FieldError("user", "email", messageSource.getMessage("login.new.account.error.email.empty", null, locale)));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			result.addError(new FieldError("user", "email", messageSource.getMessage("login.new.account.error.email.invalid", null, locale)));
		
		if (userDAO.carregaUsuarioEmail(form.getEmail()) != null)
			result.addError(new FieldError("user", "email", messageSource.getMessage("login.new.account.error.email.taken", null, locale)));
		
		if (!ValidationUtils.validPassword(form.getPassword()))
			result.addError(new FieldError("user", "password", messageSource.getMessage("login.new.account.error.password.invalid", null, locale)));
		
		if (!form.getPassword().equals(form.getRepeatPassword()))
			result.addError(new FieldError("user", "password", messageSource.getMessage("login.new.account.error.password.different", null, locale)));
		
        if (result.hasErrors())
            return "login/create";
 
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        Usuario user = new Usuario(form.getName(), form.getEmail(), encodedPassword, false);
        userDAO.criaNovoUsuario(user);
 
//        SecurityUtils.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
        return "redirect:/login?message=login.new.account.success.created";
    }
    
    /**
     * Ação que redireciona o usuário para a tela de esquecimento de senha
     */
	@RequestMapping(value = "/login/forgot", method = RequestMethod.GET)
	public ModelAndView mostraPaginaRecuperacaoSenha()
	{
		ModelAndView model = new ModelAndView();
        model.addObject("form", new ForgotPasswordForm());
        model.setViewName("login/forgot");
		return model;
	}

	/**
	 * Ação que envia um token para troca de senha
	 */
	@RequestMapping(value = "/login/forgot", method = RequestMethod.POST)
	public String enviaTokenRecuperacaoSenha(@ModelAttribute("form") RegistrationForm form, BindingResult result, Locale locale)
	{
		if (form.getEmail().length() == 0)
	    	result.addError(new FieldError("form", "email", messageSource.getMessage("login.forgot.password.error.email.empty", null, locale)));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
	    	result.addError(new FieldError("form", "email", messageSource.getMessage("login.forgot.password.error.email.invalid", null, locale)));

        if (result.hasErrors())
            return "login/forgot";
		
		Usuario user = userDAO.carregaUsuarioEmail(form.getEmail());

		if (user != null)
		{
			String token = CryptoUtils.createToken();
			userDAO.salvaTokenLogin(user.getId(), token);
			
			String url = Configuration.getHostname() + "/login/reset.do?token=" + token + "&email=" + user.getUsername();		
			String title = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.titulo", null, locale);
			String contents = messageSource.getMessage("login.forgot.password.email.inicializacao.senha.corpo", new String[] { url }, locale);
			emailService.sendToUser(user.getNome(), user.getUsername(), title, contents);
		}
		
        return "redirect:/login?message=login.forgot.password.success.email.sent";
	}
	
	/**
	 * Ação que prepara o formulário de reset de senha
	 */
	@RequestMapping(value = "/login/reset", method = RequestMethod.GET)
	public ModelAndView mostraPaginaReinicializacaoSenha(@ModelAttribute("email") String email, @ModelAttribute("token") String token)
	{
		ResetPasswordForm form = new ResetPasswordForm();
		form.setEmail(email);
		form.setToken(token);
		
		ModelAndView model = new ModelAndView();
        model.addObject("form", form);
        model.setViewName("login/reset");
		return model;
	}
	
	/**
	 * Ação que troca a senha baseada em reinicialização
	 */
	@RequestMapping(value = "/login/reset", method = RequestMethod.POST)
	public String reinicializaSenha(@ModelAttribute("form") ResetPasswordForm form, BindingResult result, Locale locale)
	{
		if (form.getEmail().length() == 0)
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.email.empty", null, locale)));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.email.invalid", null, locale)));
		
		if (form.getToken().length() == 0)
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.token.empty", null, locale)));
		
		Usuario user = userDAO.carregaUsuarioEmail(form.getEmail());

		if (user == null)
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.email.unrecognized", null, locale)));
		
		if (!userDAO.verificaValidadeTokenLogin(form.getEmail(), form.getToken(), 72))
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.token.invalid", null, locale)));
		
		if (!ValidationUtils.validPassword(form.getNewPassword()))
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.reset.password.error.password.invalid", null, locale)));
		
		if (!form.getNewPassword().equals(form.getRepeatNewPassword()))
	    	result.addError(new FieldError("form", "repeatNewPassword", messageSource.getMessage("login.reset.password.error.password.different", null, locale)));
		
        if (result.hasErrors())
            return "login/reset";
 
        String encodedPassword = passwordEncoder.encode(form.getNewPassword());
        userDAO.atualizaSenha(user.getId(), encodedPassword);
        return "redirect:/login?message=login.reset.password.success.created";
	}
	
	/**
	 * Ação que prepara o formulário de troca de senha
	 */
	@RequestMapping(value = "/login/change", method = RequestMethod.GET)
	public ModelAndView mostraPaginaTrocaSenha()
	{
		ChangePasswordForm form = new ChangePasswordForm();
		
		ModelAndView model = new ModelAndView();
        model.addObject("form", form);
        model.setViewName("login/change");
		return model;
	}
	
	/**
	 * Ação que troca a senha do usuário logado
	 */
	@RequestMapping(value = "/login/change", method = RequestMethod.POST)
	public String trocaSenha(@ModelAttribute("form") ChangePasswordForm form, BindingResult result, Locale locale)
	{
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (usuario == null)
	    	result.addError(new FieldError("form", "currentPassword", messageSource.getMessage("login.change.password.error.user.not.logged", null, locale)));

        Usuario user = userDAO.carregaUsuarioId(usuario.getId());

        if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword()))
	    	result.addError(new FieldError("form", "currentPassword", messageSource.getMessage("login.change.password.invalid.current.password", null, locale)));
		
		if (!ValidationUtils.validPassword(form.getNewPassword()))
	    	result.addError(new FieldError("form", "newPassword", messageSource.getMessage("login.change.password.error.password.invalid", null, locale)));
		
		if (!form.getNewPassword().equals(form.getRepeatNewPassword()))
	    	result.addError(new FieldError("form", "repeatNewPassword", messageSource.getMessage("login.change.password.error.password.different", null, locale)));
		
        if (result.hasErrors())
            return "login/change";
 
        String encodedPassword = passwordEncoder.encode(form.getNewPassword());
        userDAO.atualizaSenha(usuario.getId(), encodedPassword);
        return "redirect:/?message=login.change.password.success.created";
	}
}