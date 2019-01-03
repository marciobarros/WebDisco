package br.unirio.dsw.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.unirio.dsw.model.Unidade;
import br.unirio.dsw.service.dao.UnidadeDAO;
import br.unirio.dsw.utils.JsonUtils;

/**
 * Controller responsável pelo registro de unidades
 * 
 * @author marciobarros
 */
@RestController
public class UnidadeController
{
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UnidadeDAO unidadeDAO;

	/**
	 * Ação que lista todas as unidades
	 */
	@ResponseBody
	@RequestMapping(value = "/unidade", method = RequestMethod.GET, produces = "application/json")
	public String lista(@ModelAttribute("page") int pagina, @ModelAttribute("size") int tamanho, @ModelAttribute("sigla") String filtroSigla, @ModelAttribute("nome") String filtroNome, Locale locale)
	{
		List<Unidade> unidades = unidadeDAO.lista(pagina-1, tamanho, filtroSigla, filtroNome);
		int total = unidadeDAO.conta(filtroSigla, filtroNome);

		JsonObject root = new JsonObject();
		root.addProperty("count", total);
		root.add("items", new Gson().toJsonTree(unidades));
		return JsonUtils.ajaxSuccess(root);
	}

	/**
	 * Ação que salva os dados de uma nova unidade
	 */
	@ResponseBody
	@RequestMapping(value = "/unidade", method = RequestMethod.POST, consumes="application/json")
	public String salva(@RequestBody Unidade unidade)
	{
		if (unidade.getSigla().length() == 0)
			return JsonUtils.ajaxError("O código não pode ficar vazio.");
		
		if (unidade.getSigla().length() > 10)
			return JsonUtils.ajaxError("O código não deve ter mais do que 10 caracteres.");
		
		if (unidade.getNome().length() <= 0)
			return JsonUtils.ajaxError("O nome não pode ficar vazio.");
		
		if (unidade.getNome().length() > 80)
			return JsonUtils.ajaxError("O nome não pode ter mais do que 80 caracteres.");
 
		// TODO; testar os gestores
		
		if (unidade.getId() == -1)
			unidadeDAO.cria(unidade);
		else
			unidadeDAO.atualiza(unidade);
		
		return JsonUtils.ajaxSuccess();
	}

	/**
	 * Ação que remove uma unidade
	 */
	@ResponseBody
	@RequestMapping(value = "/unidade/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") int id, Locale locale)
	{
		Unidade unidade = unidadeDAO.carregaUnidadeId(id);

		if (unidade == null)
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.lista.remocao.nao.encontrado", null, locale));

		unidadeDAO.remove(id);
		return JsonUtils.ajaxSuccess();
	}

	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admin/test", method = RequestMethod.POST)
	public String admin()
	{
		return JsonUtils.ajaxSuccess();
	}
}