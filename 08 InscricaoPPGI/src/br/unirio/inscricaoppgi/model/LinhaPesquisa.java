package br.unirio.inscricaoppgi.model;

import lombok.Getter;

/**
 * Enumeração das linhas de pesquisa do PPGI
 * 
 * @author Marcio
 */
public enum LinhaPesquisa
{
	RCR("Representação de Conhecimento e Raciocínio", "RCR"),
	DR("Distribuição e Redes", "DR"),
	SAN("Sistemas de Apoio a Negócios", "SAN");
	
	private @Getter String nome;
	private @Getter String codigo;
	
	LinhaPesquisa(String nome, String codigo)
	{
		this.nome = nome;
		this.codigo = codigo;
	}
	
	public static LinhaPesquisa get(String codigo)
	{
		for (LinhaPesquisa linha : values())
			if (linha.getCodigo().compareToIgnoreCase(codigo) == 0)
				return linha;
		
		return null;
	}
}