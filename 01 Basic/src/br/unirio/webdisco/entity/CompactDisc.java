/*
 * Classe que representa um CD de músicas
 *  
 * @author marcio.barros
 */

package br.unirio.webdisco.entity;

import java.io.Serializable;

public class CompactDisc implements Serializable
{
	private static final long serialVersionUID = -4118217013589353261L;

	private String title;
	private double price;
	private double stock;
	
	
	/**
	 * Inicializa um CD 
	 */
	
	public CompactDisc() 
	{
		title = "";
		price = 0.0;
		stock = 1.0;
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