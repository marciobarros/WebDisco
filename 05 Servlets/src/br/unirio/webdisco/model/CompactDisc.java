package br.unirio.webdisco.model;

import java.io.Serializable;

/**
 * Classe que representa um CD
 * 
 * @author marcio.barros
 */
public class CompactDisc implements Serializable
{
	private static final long serialVersionUID = -4118217013589353261L;

	private int id;
	private String title;
	private double price;
	private double stock;
	
	/**
	 * Inicializa um CD 
	 */
	public CompactDisc() 
	{
		this.id = -1;
		this.title = "";
		this.price = 0.0;
		this.stock = 1.0;
	}
	
	/**
	 * Retorna o identificador do CD
	 */
	public int getId() 
	{
		return id;
	}
	
	/**
	 * Altera o identificador do CD
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