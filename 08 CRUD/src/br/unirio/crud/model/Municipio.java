package br.unirio.crud.model;

import lombok.Data;

/**
 * Classe que representa um municipio
 * 
 * @author Marcio Barros
 */
public @Data class Municipio
{
	private int id = -1;
	private String nome = "";
	private Estado estado = null;
	private String latitude = "";
	private String longitude = "";
}