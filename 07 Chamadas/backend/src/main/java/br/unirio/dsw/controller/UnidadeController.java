package br.unirio.dsw.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import br.unirio.dsw.model.Unidade.GestorUnidade;
import br.unirio.dsw.model.Usuario;
import br.unirio.dsw.service.dao.UnidadeDAO;
import br.unirio.dsw.service.dao.UsuarioDAO;
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

	@Autowired
	private UsuarioDAO usuarioDAO;

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
	public String salva(@RequestBody Unidade unidade, Locale locale)
	{
		if (unidade.getSigla().length() == 0)
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.codigo.nao.vazio", null, locale));
		
		if (unidade.getSigla().length() > 10)
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.codigo.menor.10char", null, locale));
		
		if (unidade.getNome().length() <= 0)
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.nome.nao.vazio", null, locale));
		
		if (unidade.getNome().length() > 80)
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.codigo.menor.80char", null, locale));
 
		for (int i = 0; i < unidade.contaGestores(); i++)
		{
			GestorUnidade gestor = unidade.pegaGestorIndice(i);
			
			if (verificaDuplicado(unidade, gestor, i))
				return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.gestor.duplicado", new Object[]{ gestor.getNome() }, locale));
			
			Usuario usuarioGestor = usuarioDAO.carregaUsuarioId(gestor.getId());
			
			if (usuarioGestor == null)
				return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.erro.gestor.nao.encontrado", new Object[]{ gestor.getNome() }, locale));
		}
		
		if (unidade.getId() == -1)
			unidadeDAO.cria(unidade);
		else
			unidadeDAO.atualiza(unidade);
		
		return JsonUtils.ajaxSuccess();
	}

	/**
	 * Verifica se um gestor de unidade está duplicado
	 */
	private boolean verificaDuplicado(Unidade unidade, GestorUnidade gestor, int posicao)
	{
		for (int i = 0; i < posicao; i++)
		{
			GestorUnidade atual = unidade.pegaGestorIndice(i);
			
			if (atual.getId() == gestor.getId())
				return true;
		}
		
		return false;
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
			return JsonUtils.ajaxError(messageSource.getMessage("unidade.cadastro.remocao.nao.encontrado", null, locale));

		unidadeDAO.remove(id);
		return JsonUtils.ajaxSuccess();
	}
}