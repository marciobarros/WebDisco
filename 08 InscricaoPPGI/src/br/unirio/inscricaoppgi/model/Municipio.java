package br.unirio.inscricaoppgi.model;

import lombok.Data;
import br.unirio.inscricaoppgi.gae.datastore.DataObject;

/**
 * Classe que representa um município brasileiro
 * 
 * @author Marcio
 */
public @Data class Municipio implements DataObject
{
	private long id;
	private String nome;
	private String estado;

	public Municipio(String nome, String estado)
	{
		this.id = -1;
		this.nome = nome;
		this.estado = estado;
	}

	public Municipio()
	{
		this("", "");
	}
}