package br.unirio.dsw.model;

import lombok.Getter;

/**
 * Enumeração dos tipos de campos de uma chamada
 * 
 * @author Marcio Barros
 */
public enum TipoCampoChamada
{
	Titulo(0),
	AreaTexto(1),
	LinhaTexto(2),
	Anexo(3),
	Data(4),
	NumeroInteiro(5),
	NumeroReal(6),
	Combo(7);
	
	private @Getter int codigo;
	
	private TipoCampoChamada(int codigo)
	{
		this.codigo = codigo;
	}
	
	public TipoCampoChamada pegaCodigo(int codigo)
	{
		for (TipoCampoChamada tipo : values())
			if (tipo.getCodigo() == codigo)
				return tipo;
		
		return null;
	}
}