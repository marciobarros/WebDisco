package br.unirio.webdisco.model;

import lombok.Data;

/**
 * Classe que representa um CD
 *  
 * @author marcio.barros
 */
public @Data class CompactDisc
{
	private int id = -1;
	private String title = "";
	private double price = 0.0;
	private double stock = 1.0;

	public CompactDisc() 
	{
		this(-1, "", 0.0, 1.0);
	}

	public CompactDisc(int id, String title, double price, double stock) 
	{
		this.id = id;
		this.title = title;
		this.price = price;
		this.stock = stock;
	}
}