package br.unirio.inscricaoppgi.model;

import lombok.Getter;

/**
 * Enumeração dos sexos dos candidatos
 * 
 * @author Marcio
 */
public enum Sexo
{
	MASCULUNO("Masculino", "M"),
	FEMININO("Feminino", "F");
	
	private @Getter String codigo;
	private @Getter String nome;
	
	Sexo(String nome, String codigo)
	{
		this.nome = nome;
		this.codigo = codigo;
	}
	
	public static Sexo get(String codigo)
	{
		for (Sexo tipo : Sexo.values())
			if (tipo.getCodigo().compareToIgnoreCase(codigo) == 0)
				return tipo;
		
		return null;
	}
}