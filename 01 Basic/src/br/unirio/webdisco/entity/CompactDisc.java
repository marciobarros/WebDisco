/*
 * Classe que representa um CD de m�sicas
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
	 * Retorna o t�tulo do CD
	 */
	
	public String getTitle() 
	{
		return title;
	}

	
	/**
	 * Altera o t�tulo do CD
	 */
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	
	/**
	 * Retorna o pre�o do CD
	 */
	
	public double getPrice() 
	{
		return price;
	}
	
	
	/**
	 * Altera o pre�o do CD 
	 */
	
	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	
	/**
	 * Retorna o n�mero de CDs em estoque
	 */
	
	public double getStock() 
	{
		return stock;
	}
	
	
	/**
	 * Altera o n�mero de CDs em estoque
	 */
	
	public void setStock(double stock) 
	{
		this.stock = stock;
	}
}