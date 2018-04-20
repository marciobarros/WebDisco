package br.unirio.dsw.view;

import lombok.Data;

/**
 * Classe do formulário de registro de CD
 * 
 * @author marciobarros
 */
public @Data class CompactDiscForm
{
	private int id = -1;
	private String title = "";
	private double price = 0.0;
	private double stock = 1.0;
}