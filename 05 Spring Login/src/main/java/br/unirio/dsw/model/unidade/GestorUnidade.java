package br.unirio.dsw.model.unidade;

import lombok.Data;

/**
 * Classe que representa um gestor em uma unidade
 * 
 * @author Marcio
 */
public @Data class GestorUnidade
{
	private int id;
	private String nome;

	public GestorUnidade(int id, String nome)
	{
		this.id = id;
		this.nome = nome;
	}
}