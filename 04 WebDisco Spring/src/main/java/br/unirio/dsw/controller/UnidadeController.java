package br.unirio.dsw.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.unirio.dsw.view.FormGestorUnidade;
import br.unirio.dsw.view.FormUnidade;

@Controller
@RequestMapping("/unidade")
public class UnidadeController 
{
	@RequestMapping(value="/lista/{page}", method=RequestMethod.GET)
	public ModelAndView lista(@PathVariable("page") int page)
	{
		// TODO carrega as 10 unidades a partir da pagina "page" 
		// e vai para a view que apresenta a lista. Retorna o
		// número de unidades atualmente cadastradas.

		// MaV: page
		// MaV: count
		// MaV: list
		// VIEW: unidades/list
		
		return null;
	}

	@RequestMapping(value="/nova", method=RequestMethod.GET)
	public ModelAndView nova()
	{
		// TODO criar um formulário de unidade vazio e
		// passar para a página de fomulário de unidade

		// MaV: formUnidade
		// VIEW: unidades/formUnidade
		
		return null;
	}

	@RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") int id)
	{
		// TODO carregar a unidade do banco de dados,
		// criar um formulário com base na unidade e
		// passar para a página de fomulário de unidade

		// MaV: formUnidade
		// VIEW: unidades/formUnidade
		
		return null;
	}

	@RequestMapping(value="/salva", method=RequestMethod.POST)
	public String salva(@ModelAttribute("form") FormUnidade form, BindingResult result, Locale locale)
	{
		// TODO criticar os dados da nova unidade, salvar
		// os dados no banco e redirecionar para a ação de
		// listar as unidades
		
		return null;
	}

	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public String remove(@PathVariable("id") int id)
	{
		// TODO remove a unidade selecionada e redireciona
		// para a ação de lista
		return null;
	}

	@RequestMapping(value="/novoGestor/{idUnidade}", method=RequestMethod.GET)
	public ModelAndView novoGestor(@PathVariable("idUnidade") int idUnidade)
	{
		// TODO criar um formulário de gestor vazio e
		// passar para a página de formulário de gestor

		// MaV: formGestor
		// MaV: lista de usuários (id, nome)
		// VIEW: unidades/formGestor
		
		return null;
	}

	@RequestMapping(value="/salvaGestor", method=RequestMethod.POST)
	public String salvaGestor(@ModelAttribute("form") FormGestorUnidade form, BindingResult result, Locale locale)
	{
		// TODO criticar os dados do novo gestor, salvar
		// os dados no banco e redirecionar para a ação de
		// editar a unidade selecionada
		return null;
	}

	@RequestMapping(value="/removeGestor/{idUnidade}/{idGestor}", method=RequestMethod.GET)
	public String remove(@PathVariable("idUnidade") int idUnidade, @PathVariable("idGestor") int idGestor)
	{
		// TODO remove o gestor selecionado da unidade 
		// selecionada e redireciona para a página de 
		// edição da unidade selecionada
		return null;
	}
}