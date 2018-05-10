package br.unirio.dsw.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.unirio.dsw.model.CompactDisc;
import br.unirio.dsw.service.dao.CompactDiscDAO;
import br.unirio.dsw.utils.JsonUtils;

/**
 * Controller responsável pelas ações de CD
 * 
 * @author marciobarros
 */
@Controller
public class CompactDiscController
{
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CompactDiscDAO compactDiscDAO;

	/**
	 * Ação que apresenta a página da aplicação
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mostraPagina()
	{
		return "index";
	}

	/**
	 * Ação AJAX que lista todos os CD
	 */
	@ResponseBody
	@RequestMapping(value = "/cd", method = RequestMethod.GET, produces = "application/json")
	public String lista(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("nome") String filtroNome)
	{
		List<CompactDisc> cds = compactDiscDAO.lista(pagina, tamanho, filtroNome);
		int total = compactDiscDAO.conta(filtroNome);

		Gson gson = new Gson();
		JsonArray jsonCompactDisc = new JsonArray();

		for (CompactDisc cd : cds)
			jsonCompactDisc.add(gson.toJsonTree(cd));

		JsonObject root = new JsonObject();
		root.addProperty("Result", "OK");
		root.addProperty("TotalRecordCount", total);
		root.add("Records", jsonCompactDisc);
		return root.toString();
	}

	/**
	 * Ação que salva os dados de um novo um edital
	 */
	@ResponseBody
	@RequestMapping(value = "/cd", method = RequestMethod.POST, consumes="application/json")
	public String salva(@RequestBody CompactDisc cd)
	{
		if (cd.getTitle().length() == 0)
			return JsonUtils.ajaxError("O título não pode ficar vazio.");
		
		if (cd.getPrice() <= 0)
			return JsonUtils.ajaxError("O preço não pode ser negativo ou zero.");
		
		if (cd.getStock() <= 0)
			return JsonUtils.ajaxError("A quantidade em estoque não pode ser negativa.");
 
		if (cd.getId() == -1)
			compactDiscDAO.insere(cd);
		else
			compactDiscDAO.atualiza(cd);
		
		return JsonUtils.ajaxSuccess();
	}

	/**
	 * Ação que remove um CD
	 */
	@ResponseBody
	@RequestMapping(value = "/cd/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") int id, Locale locale)
	{
		CompactDisc cd = compactDiscDAO.getCompactDiscId(id);

		if (cd == null)
			return JsonUtils.ajaxError(messageSource.getMessage("edital.lista.remocao.nao.encontrado", null, locale));

		compactDiscDAO.remove(id);
		return JsonUtils.ajaxSuccess();
	}
}