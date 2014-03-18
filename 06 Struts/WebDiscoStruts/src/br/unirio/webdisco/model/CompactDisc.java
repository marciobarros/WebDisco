/*
 * Classe que representa um CD
 *  
 * @author marcio.barros
 */

package br.unirio.webdisco.model;

public class CompactDisc
{
	private int id;
	private String title;
	private double price;
	private double stock;
	
	/**
	 * Inicializa um CD 
	 */
	public CompactDisc() 
	{
		this(0, "", 0.0, 1.0);
	}
	
	/**
	 * Inicializa um CD 
	 */
	public CompactDisc(int id, String title, double price, double stock) 
	{
		this.id = id;
		this.title = title;
		this.price = price;
		this.stock = stock;
	}
	
	/**
	 * Retorna o ID do CD
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Muda o ID do CD
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Retorna o título do CD
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * Altera o título do CD
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	/**
	 * Retorna o preço do CD
	 */
	public double getPrice() 
	{
		return price;
	}
	
	/**
	 * Altera o preço do CD 
	 */
	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	/**
	 * Retorna o número de CDs em estoque
	 */
	public double getStock() 
	{
		return stock;
	}
	
	/**
	 * Altera o número de CDs em estoque
	 */
	public void setStock(double stock) 
	{
		this.stock = stock;
	}
}