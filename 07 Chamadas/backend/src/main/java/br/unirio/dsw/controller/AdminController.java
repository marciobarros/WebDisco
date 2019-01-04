package br.unirio.dsw.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.unirio.dsw.utils.JsonUtils;

/**
 * Controlador das ações de administrador
 * 
 * @author Marcio
 */
@RestController
public class AdminController
{
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admin/test", method = RequestMethod.POST)
	public String admin()
	{
		return JsonUtils.ajaxSuccess();
	}
}