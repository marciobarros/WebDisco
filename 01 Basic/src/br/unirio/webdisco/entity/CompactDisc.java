package br.unirio.webdisco.entity;

import lombok.Data;

/**
 * Classe que representa um CD de músicas
 *  
 * @author marcio.barros
 */
public @Data class CompactDisc
{
	private String title = "";
	private double price = 0.0;
	private double stock = 1.0;
}