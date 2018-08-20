package br.unirio.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public void signup() 
	{
	}
}