package br.unirio.dsw.view;

import br.unirio.dsw.model.CompactDisc;
import lombok.Data;

/**
 * Classe do formulário de registro de CD
 * 
 * @author marciobarros
 */
public @Data class CompactDiscForm
{
	private int id;
	private String title;
	private double price;
	private double stock;
	
	/**
	 * Inicializa o formulário em branco
	 */
	public CompactDiscForm()
	{
		this.id = -1;
		this.title = "";
		this.price = 0.0;
		this.stock = 1.0;
	}
	
	/**
	 * Inicializa o formulário com as informações do CD
	 */
	public CompactDiscForm(CompactDisc cd)
	{
		this.id = cd.getId();
		this.title = cd.getTitle();
		this.price = cd.getPrice();
		this.stock = cd.getStock();
	}
}