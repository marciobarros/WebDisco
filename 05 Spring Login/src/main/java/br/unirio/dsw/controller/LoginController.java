package br.unirio.dsw.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.unirio.dsw.configuration.Configuration;
import br.unirio.dsw.model.usuario.Usuario;
import br.unirio.dsw.service.dao.UsuarioDAO;
import br.unirio.dsw.service.email.EmailService;
import br.unirio.dsw.utils.CryptoUtils;
import br.unirio.dsw.utils.ValidationUtils;
import lombok.Data;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@Controller
public class LoginController 
{
	private static final String LOGIN_ERROR_KEY = "login.reset.password.error.email.invalid"; 
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    @Autowired
	private UsuarioDAO userDAO;
    
    @Autowired
	private EmailService emailService;

	/**
	 * Ação de chegada no sistema
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mostraPaginaRaiz()
	{
		return "login/index";
	}
	
	/**
	 * Ação que mostra a página de login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String mostraPaginaLogin()
	{
		return "login/index";
	}

	/**
	 * Ação que mostra a página inicial da aplicação
	 */
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public String mostraPaginaHomepage()
	{
		return "homepage/index";
	}

	/**
	 * Acao chamada pelo Spring Security quando ocorre um login bem sucedido
	 */
	@RequestMapping(value = "/login/success", method = RequestMethod.GET)
    public String loginSuccess(HttpServletRequest request, Locale locale) 
	{
        return "redirect:/homepage";
    }

	/**
	 * Acao chamada pelo Spring Security quando ocorre um erro de login
	 */
	@RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public String loginError(HttpServletRequest request, Locale locale) 
	{
		Exception exception = (Exception) request.getSession().getAttribute(LOGIN_ERROR_KEY);

		// TODO: o sistema não está entrando neste IF quando uma conta está bloqueada
		if (exception instanceof LockedException) 
	        return "redirect:/login?message=login.login.message.locked.account";

        return "redirect:/login?message=login.login.message.invalid.credentials";
    }
	
	/**
	 * Ação que mostra a página de criação de conta
	 */
	@RequestMapping(value = "/login/create", method = RequestMethod.GET)
	public ModelAndView mostraPaginaCriacaoConta()
	{
		ModelAndView model = new ModelAndView();
		model.addObject("form", new FormCriacaoConta());
		return model;
	}

	/**
	 * Ação que cria uma nova conta
	 */
	@RequestMapping(value = "/login/create", method = RequestMethod.POST)
    public String salvaNovaConta(@ModelAttribute("form") FormCriacaoConta form, BindingResult result, Locale locale) 
	{
		if (form.getNome().length() == 0)
			result.addError(new FieldError("form", "nome", "O nome não pode ficar vazio."));
		
		if (form.getEmail().length() == 0)
			result.addError(new FieldError("form", "email", "O e-mail não pode ficar vazio."));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			result.addError(new FieldError("form", "email", "O e-mail não é válido."));
		
		if (userDAO.carregaUsuarioEmail(form.getEmail()) != null)
			result.addError(new FieldError("form", "email", "O e-mail já está registrado no sistema."));
		
		if (!ValidationUtils.validPassword(form.getSenha()))
			result.addError(new FieldError("form", "senha", "A senha não é válida."));
		
		if (!form.getSenha().equals(form.getSenhaRepetida()))
			result.addError(new FieldError("form", "senhaRepetida", "A senha repetida não confere com a senha."));
		
        if (result.hasErrors())
            return "/login/create";

        String encodedPassword = passwordEncoder.encode(form.getSenha());
        Usuario user = new Usuario(form.getNome(), form.getEmail(), encodedPassword, false);
        userDAO.criaNovoUsuario(user);
 
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/homepage?message=login.new.account.success.created";
    }
	
	/**
	 * Ação que mostra a página de recuperação de senha
	 */
	@RequestMapping(value = "/login/forgot", method = RequestMethod.GET)
	public ModelAndView mostraPaginaRecuperacaoSenha()
	{
		ModelAndView model = new ModelAndView();
		model.addObject("form", new FormRecuperacaoSenha());
		return model;
	}
	
	/**
	 * Ação que envia um e-mail de recuperação de senha
	 */
	@RequestMapping(value = "/login/forgot", method = RequestMethod.POST)
    public String recuperacaoSenha(@ModelAttribute("form") FormRecuperacaoSenha form, BindingResult result, Locale locale) 
	{
		if (form.getEmail().length() == 0)
			result.addError(new FieldError("form", "email", "O email não pode ficar vazio."));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			result.addError(new FieldError("form", "email", "O email não é válido."));
		
        if (result.hasErrors())
            return "/login/forgot";
		
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
	 * Ação que mostra a página de reinicialização de senha
	 */
	@RequestMapping(value = "/login/reset", method = RequestMethod.GET)
	public ModelAndView mostraPaginaReinicializacaoSenha()
	{
		ModelAndView model = new ModelAndView();
		model.addObject("form", new FormReinicializacaoSenha());
		return model;
	}
	
	/**
	 * Ação que troca a senha baseada em reinicialização
	 */
	@RequestMapping(value = "/login/reset", method = RequestMethod.POST)
	public String reinicializaSenha(@ModelAttribute("form") FormReinicializacaoSenha form, BindingResult result, Locale locale)
	{
		if (form.getEmail().length() == 0)
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.email.empty", null, locale)));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.email.invalid", null, locale)));
		
		if (form.getToken().length() == 0)
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.token.empty", null, locale)));
		
		Usuario user = userDAO.carregaUsuarioEmail(form.getEmail());

		if (user == null)
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.email.unrecognized", null, locale)));
		
		if (!userDAO.verificaValidadeTokenLogin(form.getEmail(), form.getToken(), 72))
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.token.invalid", null, locale)));
		
		if (!ValidationUtils.validPassword(form.getSenha()))
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.reset.password.error.password.invalid", null, locale)));
		
		if (!form.getSenha().equals(form.getSenhaRepetida()))
			result.addError(new FieldError("form", "senhaRepetida", messageSource.getMessage("login.reset.password.error.password.different", null, locale)));
			
        if (result.hasErrors())
            return "/login/reset";
 
        String encodedPassword = passwordEncoder.encode(form.getSenha());
        userDAO.atualizaSenha(user.getId(), encodedPassword);
        return "redirect:/login?message=login.reset.password.success.created";
	}
	
	/**
	 * Ação que mostra a página de troca de senha
	 */
	@RequestMapping(value = "/login/change", method = RequestMethod.GET)
	public ModelAndView mostraPaginaTrocaSenha()
	{
		ModelAndView model = new ModelAndView();
		model.addObject("form", new FormTrocaSenha());
		return model;
	}
	
	/**
	 * Ação que troca a senha do usuário logado
	 */
	@RequestMapping(value = "/login/change", method = RequestMethod.POST)
	public String trocaSenha(@ModelAttribute("form") FormTrocaSenha form, BindingResult result, Locale locale)
	{
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (usuario == null)
			result.addError(new FieldError("form", "senha", messageSource.getMessage("login.change.password.error.user.not.logged", null, locale)));

        Usuario user = userDAO.carregaUsuarioId(usuario.getId());

        if (!passwordEncoder.matches(form.getSenhaAtual(), user.getPassword()))
        	result.addError(new FieldError("form", "senha", messageSource.getMessage("login.change.password.invalid.current.password", null, locale)));
		
		if (!ValidationUtils.validPassword(form.getSenhaNova()))
			result.addError(new FieldError("form", "senhaNova", messageSource.getMessage("login.change.password.error.password.invalid", null, locale)));
		
		if (!form.getSenhaNova().equals(form.getSenhaNovaRepetida()))
			result.addError(new FieldError("form", "senhaNovaRepetida", messageSource.getMessage("login.change.password.error.password.different", null, locale)));
 			
        if (result.hasErrors())
            return "/login/change";
 
        String encodedPassword = passwordEncoder.encode(form.getSenhaNova());
        userDAO.atualizaSenha(usuario.getId(), encodedPassword);
        return "redirect:/homepage?message=login.change.password.success.created";
	}
}

/**
 * Classe que representa o formulario de criacao de nova conta
 */
@Data class FormCriacaoConta
{
	private String nome;
	private String email;
	private String senha;
	private String senhaRepetida;
}

/**
 * Classe que representa o formulario de esquecimento de senha
 */
@Data class FormRecuperacaoSenha
{
	private String email;
}

/**
 * Classe que representa o formulario de reinicializacao de senha
 */
@Data class FormReinicializacaoSenha
{
	private String email;
	private String token;
	private String senha;
	private String senhaRepetida;
}

/**
 * Classe que representa o formulario de troca de senha
 */
@Data class FormTrocaSenha
{
	private String senhaAtual;
	private String senhaNova;
	private String senhaNovaRepetida;
}