package br.unirio.inscricaoppgi.model;

import lombok.Getter;

/**
 * Enumera��o dos tipos de usu�rios
 * 
 * @author Marcio
 */
public enum TipoUsuario
{
	CANDIDATO("cand"),
	PROFESSOR("prof"),
	ADMINISTRADOR("adm");
	
	private @Getter String codigo;
	
	TipoUsuario(String codigo)
	{
		this.codigo = codigo;
	}
	
	public static TipoUsuario get(String codigo)
	{
		for (TipoUsuario tipo : TipoUsuario.values())
			if (tipo.getCodigo().compareToIgnoreCase(codigo) == 0)
				return tipo;
		
		return null;
	}
}