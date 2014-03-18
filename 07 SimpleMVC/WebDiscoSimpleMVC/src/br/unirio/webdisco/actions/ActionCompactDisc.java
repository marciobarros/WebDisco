package br.unirio.webdisco.actions;

import java.util.List;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ResultType;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.model.CompactDisc;

public class ActionCompactDisc extends Action
{
	public static final int PAGE_SIZE = 10;
	
	/**
	 * Recupera os discos da página atual
	 */
	@DisableUserVerification
	@Any("/jsp/listdisc.jsp")
	public String retrieve()
	{
		int page = getIntParameter("page", 0);

		List<CompactDisc> list = DAOFactory.getCompactDiscDAO().lista(page, PAGE_SIZE);
		int count = DAOFactory.getCompactDiscDAO().conta();
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
		
		setAttribute("discs", list);
		setAttribute("page", page);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		return SUCCESS;
	}
	
	/**
	 * Cria um novo disco
	 */
	@DisableUserVerification
	@Any("/jsp/formdisc.jsp")
	public String create()
	{
		setAttribute("cd", new CompactDisc());
		return SUCCESS;
	}
	
	/**
	 * Edita um disco
	 */
	@DisableUserVerification
	@Error(type=ResultType.Redirect, value="/compactDisc/retrieve.do")
	@Success("/jsp/formdisc.jsp")
	public String edit() throws ActionException
	{
		int id = getIntParameter("id", -1);
		CompactDisc cd = DAOFactory.getCompactDiscDAO().getCompactDiscId(id);
		check(cd != null, "O CD selecionado não está registrado no sistema");
		setAttribute("cd", cd);
		return SUCCESS;
	}

	/**
	 * Remove o disco selecionado
	 */
	@DisableUserVerification
	@Any(type=ResultType.Redirect, value="/compactDisc/retrieve.do")
	public String delete() throws ActionException
	{
		int id = getIntParameter("id", -1);
		CompactDisc cd = DAOFactory.getCompactDiscDAO().getCompactDiscId(id);
		check(cd != null, "O CD selecionado não está registrado no sistema");
	
		DAOFactory.getCompactDiscDAO().remove(id);
		addRedirectNotice("O CD selecionado foi removido com sucesso");
		return SUCCESS;
	}

	/**
	 * Salva o disco que foi editado
	 */
	@DisableUserVerification
	@Success(type=ResultType.Redirect, value="/compactDisc/retrieve.do")
	@Error("/jsp/formdisc.jsp")
	public String save() throws ActionException
	{
		int id = getIntParameter("id", -1);
		CompactDisc cd;
		
		if (id > 0)
		{
			cd = DAOFactory.getCompactDiscDAO().getCompactDiscId(id);
			check(cd != null, "O CD selecionado não está registrado no sistema");
		}
		else
			cd = new CompactDisc();
		
		cd.setTitle(getParameter("title", ""));
		cd.setPrice(getDoubleParameter("price", 0.0));
		cd.setStock(getDoubleParameter("stock", 0.0));
		setAttribute("cd", cd);

		check(cd.getTitle().length() > 0, "O título do CD não pode ser vazio");
		check(cd.getPrice() > 0, "O preço do CD deve ser maior do que zero");
		check(cd.getStock() >= 0, "A quantidade em estoque do CD deve ser maior ou igual a zero");

		if (cd.getId() <= 0)
			DAOFactory.getCompactDiscDAO().insere(cd);
		else
			DAOFactory.getCompactDiscDAO().atualiza(cd);
		
		return SUCCESS;		
	}
}