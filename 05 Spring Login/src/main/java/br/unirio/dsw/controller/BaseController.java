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
	 * Ação que redireciona o usuário para a página de edição de unidades
	 */
	@RequestMapping(value = "/unidade", method = RequestMethod.GET)
	public String mostraUnidades()
	{
		return "unidade/index";
	}
}