package br.unirio.inscricaoppgi.model;

import lombok.Data;

/**
 * T�pico de interesse dentro de uma inscri��o em edital
 * 
 * @author Marcio
 */
public @Data class TopicoInteresse
{
	private TopicoPesquisa topico;
	private String descricao;

	public TopicoInteresse()
	{
		this(null, "");
	}
	
	public TopicoInteresse(TopicoPesquisa topico, String descricao)
	{
		this.topico = topico;
		this.descricao = descricao;
	}
}