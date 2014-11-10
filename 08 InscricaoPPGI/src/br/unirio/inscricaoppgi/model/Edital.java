package br.unirio.inscricaoppgi.model;

import java.util.Date;

import lombok.Data;
import br.unirio.inscricaoppgi.gae.datastore.DataObject;

/**
 * Classe que representa um edital de vagas
 * 
 * @author Marcio
 */
public @Data class Edital implements DataObject
{
	private long id;
	private Date dataAbertura;
	private Date dataFechamento;

	public Edital()
	{
		this.id = -1;
		this.dataAbertura = null;
		this.dataFechamento = null;
	}
}