package br.unirio.webdisco.action;

import java.util.Map;

import lombok.Setter;

import org.apache.struts2.interceptor.RequestAware;

import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.model.CompactDisc;

import com.opensymphony.xwork2.ActionSupport;

public class CompactDiscService extends ActionSupport implements RequestAware
{
	private static final long serialVersionUID = -4809693536676168731L;

	public static final int PAGE_SIZE = 10;

	private @Setter Map<String, Object> request;
	private @Setter int id;
	private @Setter String title;
	private @Setter double price;
	private @Setter double stock;
	private @Setter int page;
	
	/**
	 * Inicializa o serviço de manipulação de discos
	 */
	public CompactDiscService()
	{
		this.id = -1;
		this.page = 0;
	}

	/**
	 * Verifica se existe uma página anterior
	 */
	public boolean hasPreviousPage()
	{
		return page > 0;
	}
	
	/**
	 * Retorna a página anterior
	 */
	public int getPreviousPage()
	{
		return hasPreviousPage() ? page-1 : 0;
	}

	/**
	 * Verifica se existe uma próxima página
	 */
	public boolean hasNextPage()
	{
		int count = DAOFactory.getCompactDiscDAO().conta();
		return count > (page+1) * PAGE_SIZE;
	}
	
	/**
	 * Retorna a próxima página
	 */
	public int getNextPage()
	{
		return hasNextPage() ? page+1 : page;
	}
	
	/**
	 * Recupera os discos da página atual
	 */
	public String retrieve()
	{
		request.put("discs", DAOFactory.getCompactDiscDAO().lista(page, PAGE_SIZE));
		return SUCCESS;
	}
	
	/**
	 * Edita um disco
	 */
	public String create()
	{
		request.put("disc", new CompactDisc());
		return INPUT;
	}
	
	/**
	 * Edita um disco
	 */
	public String edit()
	{
		request.put("disc", DAOFactory.getCompactDiscDAO().getCompactDiscId(id));
		return INPUT;
	}

	/**
	 * Remove o disco selecionado
	 */
	public String delete ()
	{
		DAOFactory.getCompactDiscDAO().remove(id);
		return SUCCESS;
	}

	/**
	 * Salva o disco que foi editado
	 */
	public String save ()
	{
		CompactDisc cd = new CompactDisc();
		cd.setId(id);
		cd.setTitle(title);
		cd.setPrice(price);
		cd.setStock(stock);
		request.put("disc", cd);

		if (title.length() == 0)
			addFieldError("title", getText("error.title.required"));
		
		if (price <= 0)
			addFieldError("price", getText("error.price.negativezero"));
		
		if (stock < 0)
			addFieldError("stock", getText("error.stock.negative"));
		
		if (hasErrors())
			return INPUT;

		if (cd.getId() <= 0)
			DAOFactory.getCompactDiscDAO().insere(cd);
		else
			DAOFactory.getCompactDiscDAO().atualiza(cd);
		
		return SUCCESS;		
	}
}