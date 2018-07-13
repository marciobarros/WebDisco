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
	public String mostraHomepage()
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
}