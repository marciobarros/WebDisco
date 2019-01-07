package br.unirio.dsw.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um campo em uma chamada
 * 
 * @author Marcio Barros
 */
public class CampoChamada
{
	private @Getter @Setter String titulo;
	private @Getter @Setter TipoCampoChamada tipo;
	private @Getter @Setter boolean opcional;
	private @Getter @Setter int decimais;
	private List<String> opcoes;

	/**
	 * Inicializa um campo de chamada
	 */
	public CampoChamada()
	{
		this.titulo = "";
		this.tipo = null;
		this.opcional = true;
		this.decimais = 0;
		this.opcoes = new ArrayList<String>();
	}
	
	/**
	 * Conta o número de opções do campo
	 */
	public int contaOpcoes()
	{
		return opcoes.size();
	}
	
	/**
	 * Retorna uma opção do campo, dado seu índice
	 */
	public String pegaOpcaoIndice(int indice)
	{
		return opcoes.get(indice);
	}
	
	/**
	 * Retorna a lista de opções do campo
	 */
	public Iterable<String> getOpcoes()
	{
		return opcoes;
	}
	
	/**
	 * Adiciona uma opção no campo
	 */
	public void adicionaOpcao(String opcao)
	{
		this.opcoes.add(opcao);
	}
	
	/**
	 * Remove uma opção do campo, dado seu índice
	 */
	public void removeOpcao(int indice)
	{
		this.opcoes.remove(indice);
	}

	/**
	 * Remove todas as opções do campo
	 */
	public void removeOpcoes()
	{
		this.opcoes.clear();
	}
}