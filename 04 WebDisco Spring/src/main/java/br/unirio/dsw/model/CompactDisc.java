package br.unirio.dsw.model;

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
}