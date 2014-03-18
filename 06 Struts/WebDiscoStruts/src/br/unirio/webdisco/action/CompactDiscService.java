package br.unirio.webdisco.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.model.CompactDisc;

import com.opensymphony.xwork2.ActionSupport;

public class CompactDiscService extends ActionSupport implements RequestAware
{
	private static final long serialVersionUID = -4809693536676168731L;

	public static final int PAGE_SIZE = 10;

	private Map<String, Object> request;
	private int currentId;
	private int currentPage;
	private String currentTitle;
	private double currentPrice;
	private double currentStock;
	
	/**
	 * Inicializa o serviço de manipulação de discos
	 */
	public CompactDiscService()
	{
		this.currentId = -1;
		this.currentPage = 0;
	}

	/**
	 * Indica a memória de requisição para a ação
	 */
	@Override
	public void setRequest(Map<String, Object> request) 
	{
		this.request = request;
	}
	
	/**
	 * Indica o ID de um disco desejado
	 */
	public void setId(int id)
	{
		this.currentId = id;
	}
	
	/**
	 * Indica o título de um disco
	 */
	public void setTitle(String title)
	{
		this.currentTitle = title;
	}
	
	/**
	 * Indica o preço de um disco
	 */
	public void setPrice(double price)
	{
		this.currentPrice = price;
	}
	
	/**
	 * Indica o estoque de um disco
	 */
	public void setStock(double stock)
	{
		this.currentStock = stock;
	}

	/**
	 * Indica a página que está sendo navegada
	 */
	public void setPage(int page)
	{
		this.currentPage = page;
	}
	
	/**
	 * Verifica se existe uma página anterior
	 */
	public boolean hasPreviousPage()
	{
		return currentPage > 0;
	}
	
	/**
	 * Retorna a página anterior
	 */
	public int getPreviousPage()
	{
		return hasPreviousPage() ? currentPage-1 : 0;
	}

	/**
	 * Verifica se existe uma próxima página
	 */
	public boolean hasNextPage()
	{
		int count = DAOFactory.getCompactDiscDAO().conta();
		return count > (currentPage+1) * PAGE_SIZE;
	}
	
	/**
	 * Retorna a próxima página
	 */
	public int getNextPage()
	{
		return hasNextPage() ? currentPage+1 : currentPage;
	}
	
	/**
	 * Recupera os discos da página atual
	 */
	public String retrieve()
	{
		request.put("discs", DAOFactory.getCompactDiscDAO().lista(currentPage, PAGE_SIZE));
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
		request.put("disc", DAOFactory.getCompactDiscDAO().getCompactDiscId(currentId));
		return INPUT;
	}

	/**
	 * Remove o disco selecionado
	 */
	public String delete ()
	{
		DAOFactory.getCompactDiscDAO().remove(currentId);
		return SUCCESS;
	}

	/**
	 * Salva o disco que foi editado
	 */
	public String save ()
	{
		CompactDisc cd = new CompactDisc();
		cd.setId(currentId);
		cd.setTitle(currentTitle);
		cd.setPrice(currentPrice);
		cd.setStock(currentStock);
		request.put("disc", cd);

		if (currentTitle.length() == 0)
			addFieldError("title", getText("error.title.required"));
		
		if (currentPrice <= 0)
			addFieldError("price", getText("error.price.negativezero"));
		
		if (currentStock < 0)
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