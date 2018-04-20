package br.unirio.dsw.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.unirio.dsw.model.CompactDisc;
import br.unirio.dsw.service.dao.CompactDiscDAO;
import br.unirio.dsw.view.CompactDiscForm;

/**
 * Controller responsável pelas ações de CD
 * 
 * @author marciobarros
 */
@Controller
public class CompactDiscController 
{
	private static final String CDLIST_KEY = "cdlist";
	
	private static final String CD_KEY = "cd";
	
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
	private CompactDiscDAO compactDiscDAO;
 
	/**
	 * Ação que redireciona o usuário para a lista de CD
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView lista()
	{
		List<CompactDisc> cdlist = compactDiscDAO.lista();
		ModelAndView model = new ModelAndView();
		model.addObject(CDLIST_KEY, cdlist);
		model.setViewName("list");
		return model;
	}

	/**
	 * Ação que cria um novo CD
	 */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView cria()
    {
		CompactDisc cd = new CompactDisc ();
		ModelAndView model = new ModelAndView();
		model.addObject(CD_KEY, cd);
		model.setViewName("form");
		return model;
    }

	/**
	 * Ação que edita um CD
	 */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edita(@PathVariable("id") int id)
    {
		CompactDisc cd = compactDiscDAO.getCompactDiscId(id);
		ModelAndView model = new ModelAndView();
		model.addObject(CD_KEY, cd);
		model.setViewName("form");
		return model;
    }

	/**
	 * Ação que salva um CD
	 */
    @RequestMapping(value = "/salva", method = RequestMethod.POST)
    public ModelAndView salva(CompactDiscForm form, Locale locale)
    {
		ModelAndView model = new ModelAndView();

		if (form.getTitle() == null || form.getTitle().length() == 0)
		{
			model.addObject("error", messageSource.getMessage("erro.formulario.titulo.vazio", null, locale));
			model.setViewName("form");
			return model;
		}

		if (form.getPrice() <= 0.0)
		{
			model.addObject("error", messageSource.getMessage("erro.formulario.preco.maior.zero", null, locale));
			model.setViewName("form");
			return model;
		}

		if (form.getStock() < 0.0)
		{
			model.addObject("error", messageSource.getMessage("erro.formulario.quantidade.maiorigual.zero", null, locale));
			model.setViewName("form");
			return model;
		}

		CompactDisc cd = new CompactDisc();
		cd.setId(form.getId());
		cd.setTitle(form.getTitle());
		cd.setPrice(form.getPrice());
		cd.setStock(form.getStock());

		if (form.getId() == -1)
			compactDiscDAO.insere(cd);
		else
			compactDiscDAO.atualiza(cd);
		
		model.setViewName("redirect:list");
		return model;
    }
	
    /**
     * Ação que remove um CD
     */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable("id") int id)
	{
		compactDiscDAO.remove(id);
		return "redirect:list";
	}
}