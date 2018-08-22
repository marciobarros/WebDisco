package br.unirio.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.service.dao.UsuarioDAO;
import br.unirio.dsw.service.dao.UsuarioDAO.ResumoUsuario;

/**
 * Controller responsável pelas ações de usuários
 * 
 * @author marciobarros
 */
@RestController
public class UsuarioController 
{
    @Autowired
	private UsuarioDAO userDAO;

	/**
	 * Acao que gera uma lista de resumos de usuários
	 */
	@ResponseBody
	@RequestMapping(value = "/usuario/resumo", method = RequestMethod.GET, produces="application/json")
	public String lista(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<ResumoUsuario> resumos = userDAO.listaResumos(pagina, tamanho, filtroNome);
		int total = userDAO.contaResumos(filtroNome);

		Gson gson = new Gson();
		JsonArray jsonResumos = new JsonArray();

		for (ResumoUsuario resumo : resumos)
			jsonResumos.add(gson.toJsonTree(resumo));

		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonResumos);
		return root.toString();
    }
}