package br.unirio.dsw.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	private static final String CD_KEY = "form";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CompactDiscDAO compactDiscDAO;

	/**
	 * Ação que apresenta a lista de CDs
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
		CompactDiscForm form = new CompactDiscForm();
		ModelAndView model = new ModelAndView();
		model.addObject(CD_KEY, form);
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
		CompactDiscForm form = new CompactDiscForm(cd);
		ModelAndView model = new ModelAndView();
		model.addObject(CD_KEY, form);
		model.setViewName("form");
		return model;
	}

	/**
	 * Ação que salva um CD
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String salva(@ModelAttribute("form") CompactDiscForm form, BindingResult result, Locale locale)
	{
		if (form.getTitle().length() == 0)
			result.addError(new FieldError("form", "title", messageSource.getMessage("cd.title.empty", null, locale)));
		
		if (form.getPrice() <= 0)
			result.addError(new FieldError("form", "price", messageSource.getMessage("cd.price.negativezero", null, locale)));
		
		if (form.getStock() <= 0)
			result.addError(new FieldError("form", "stock", messageSource.getMessage("cd.stock.negative", null, locale)));
		
        if (result.hasErrors())
            return "form";
 
		CompactDisc cd = new CompactDisc();
		cd.setId(form.getId());
		cd.setTitle(form.getTitle());
		cd.setPrice(form.getPrice());
		cd.setStock(form.getStock());

		if (form.getId() == -1)
			compactDiscDAO.insere(cd);
		else
			compactDiscDAO.atualiza(cd);
		
		return "redirect:/";
	}

	/**
	 * Ação que remove um CD
	 */
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable("id") int id)
	{
		compactDiscDAO.remove(id);
		return "redirect:/";
	}
}