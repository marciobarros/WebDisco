package br.unirio.dsw.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.unirio.dsw.model.Usuario;
import br.unirio.dsw.service.configuration.Configuration;
import br.unirio.dsw.service.dao.UsuarioDAO;
import br.unirio.dsw.service.email.EmailService;
import br.unirio.dsw.utils.CryptoUtils;
import br.unirio.dsw.utils.JsonUtils;
import br.unirio.dsw.utils.ValidationUtils;
import lombok.Data;

/**
 * Controller responsável pelas ações de login
 * 
 * @author marciobarros
 */
@RestController
public class LoginController 
{
//	private static final String LOGIN_ERROR_KEY = "login.reset.password.error.email.invalid"; 
	
    @Autowired
    private MessageSource messageSource;
    
//    @Autowired
//	private PasswordEncoder passwordEncoder;
    
    @Autowired
	private UsuarioDAO userDAO;
    
    @Autowired
	private EmailService emailService;

	/**
	 * Acao chamada quando ocorre um erro de login
	 */
//	@ResponseBody
//	@RequestMapping(value = "/login/error", method = RequestMethod.GET, produces="application/json")
//    public String loginError(HttpServletRequest request, Locale locale) 
//	{
//		Exception exception = (Exception) request.getSession().getAttribute(LOGIN_ERROR_KEY);
//
//		if (exception instanceof BadCredentialsException) 
//			return JsonUtils.ajaxError(messageSource.getMessage("login.login.message.invalid.credentials", null, locale));
//		
//		if (exception instanceof LockedException) 
//			return JsonUtils.ajaxError(messageSource.getMessage("login.login.message.locked.account", null, locale));
//
//		return JsonUtils.ajaxError(messageSource.getMessage("login.login.message.invalid.credentials", null, locale));
//    }

	/**
	 * Acao chamada quando ocorre um login bem sucedido
	 */
	@ResponseBody
	@RequestMapping(value = "/login/success", method = RequestMethod.GET, produces="application/json")
    public String loginSuccess(HttpServletRequest request, Locale locale) 
	{
		return JsonUtils.ajaxSuccess();
    }

	/**
	 * Ação que cria uma nova conta
	 */
	@ResponseBody
	@RequestMapping(value = "/login/create", method = RequestMethod.POST, consumes="application/json")
    public String novaConta(@RequestBody FormCriacaoConta form) 
	{
		if (form.getNome().length() == 0)
			return JsonUtils.ajaxError("O nome não pode ficar vazio.");
		
		if (form.getEmail().length() == 0)
			return JsonUtils.ajaxError("O e-mail não pode ficar vazio.");
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			return JsonUtils.ajaxError("O e-mail não é válido.");
		
		if (userDAO.carregaUsuarioEmail(form.getEmail()) != null)
			return JsonUtils.ajaxError("O e-mail já está registrado no sistema.");
		
		if (!ValidationUtils.validPassword(form.getSenha()))
			return JsonUtils.ajaxError("A senha não é válida.");
		
		if (!form.getSenha().equals(form.getSenhaRepetida()))
			return JsonUtils.ajaxError("A senha repetida não confere com a senha.");
 
//        String encodedPassword = passwordEncoder.encode(form.getSenha());
//        Usuario user = new Usuario(form.getNome(), form.getEmail(), encodedPassword, false);
//        userDAO.criaNovoUsuario(user);
 
//        SecurityUtils.logInUser(registered);
//        ProviderSignInUtils.handlePostSignUp(user.getEmail(), request);
        return JsonUtils.ajaxSuccess();
    }
	
	/**
	 * Ação que envia um e-mail de recuperação de senha
	 */
	@ResponseBody
	@RequestMapping(value = "/login/forgot", method = RequestMethod.POST, consumes="application/json")
    public String novaConta(@RequestBody FormEsqueciSenha form, Locale locale) 
	{
		if (form.getEmail().length() == 0)
			return JsonUtils.ajaxError("O email não pode ficar vazio.");
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			return JsonUtils.ajaxError("O nome não é válido.");
		
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
		
        return JsonUtils.ajaxSuccess();
    }
	
	/**
	 * Ação que troca a senha baseada em reinicialização
	 */
	@ResponseBody
	@RequestMapping(value = "/login/reset", method = RequestMethod.POST, consumes="application/json")
	public String reinicializaSenha(@RequestBody FormReinicializacaoSenha form, Locale locale)
	{
		if (form.getEmail().length() == 0)
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.email.empty", null, locale));
		
		if (!ValidationUtils.validEmail(form.getEmail()))
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.email.invalid", null, locale));
		
		if (form.getToken().length() == 0)
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.token.empty", null, locale));
		
		Usuario user = userDAO.carregaUsuarioEmail(form.getEmail());

		if (user == null)
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.email.unrecognized", null, locale));
		
		if (!userDAO.verificaValidadeTokenLogin(form.getEmail(), form.getToken(), 72))
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.token.invalid", null, locale));
		
		if (!ValidationUtils.validPassword(form.getSenha()))
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.password.invalid", null, locale));
		
		if (!form.getSenha().equals(form.getSenhaRepetida()))
			return JsonUtils.ajaxError(messageSource.getMessage("login.reset.password.error.password.different", null, locale));
 
        String encodedPassword = "";// TODO passwordEncoder.encode(form.getSenha());
        userDAO.atualizaSenha(user.getId(), encodedPassword);
        return JsonUtils.ajaxSuccess();
//        return "redirect:/login?message=login.reset.password.success.created";
	}
	
	/**
	 * Ação que troca a senha do usuário logado
	 */
	@ResponseBody
	@RequestMapping(value = "/login/change", method = RequestMethod.POST, consumes="application/json")
	public String trocaSenha(@RequestBody FormTrocaSenha form, Locale locale)
	{
//		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (usuario == null)
//			return JsonUtils.ajaxError(messageSource.getMessage("login.change.password.error.user.not.logged", null, locale));
//
//        Usuario user = userDAO.carregaUsuarioId(usuario.getId());
//
//        if (!passwordEncoder.matches(form.getSenhaAtual(), user.getPassword()))
//        	return JsonUtils.ajaxError(messageSource.getMessage("login.change.password.invalid.current.password", null, locale));
//		
//		if (!ValidationUtils.validPassword(form.getSenhaNova()))
//			return JsonUtils.ajaxError(messageSource.getMessage("login.change.password.error.password.invalid", null, locale));
//		
//		if (!form.getSenhaNova().equals(form.getSenhaNovaRepetida()))
//			return JsonUtils.ajaxError(messageSource.getMessage("login.change.password.error.password.different", null, locale));
// 
//        String encodedPassword = passwordEncoder.encode(form.getSenhaNova());
//        userDAO.atualizaSenha(usuario.getId(), encodedPassword);
        return JsonUtils.ajaxSuccess();
	}
	
	/**
	 * Ação que troca a senha do usuário logado
	 */
	@ResponseBody
	@RequestMapping(value = "/login/xyz", method = RequestMethod.GET)
	public String trocaSenha(Locale locale)
	{
//		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (usuario == null)
//			return JsonUtils.ajaxError(messageSource.getMessage("login.change.password.error.user.not.logged", null, locale));

        return JsonUtils.ajaxSuccess();
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
@Data class FormEsqueciSenha
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