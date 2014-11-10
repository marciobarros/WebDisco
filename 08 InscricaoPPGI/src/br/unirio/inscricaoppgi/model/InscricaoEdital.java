package br.unirio.inscricaoppgi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.unirio.inscricaoppgi.gae.datastore.DataObject;

/**
 * Classe que representa a inscrição em um edital de vagas
 * 
 * @author Marcio
 */
public class InscricaoEdital implements DataObject
{
	private @Getter @Setter long id;
	private @Getter @Setter long idCandidato;
	private @Getter @Setter long idEdital;
	private @Getter @Setter Date dataInscricao;
	private @Getter @Setter LinhaPesquisa linhaPesquisaPrincipal;
	private @Getter @Setter LinhaPesquisa linhaPesquisaSecundaria;
	private @Getter @Setter String observacoes;
	private List<TopicoInteresse> topicos;
	private @Setter boolean inscricaoVerificada;
	private @Setter boolean documentosPendente;

	/**
	 * Inicializa a inscrição no edital
	 */
	public InscricaoEdital()
	{
		this.id = -1;
		this.idCandidato = -1;
		this.idEdital = -1;
		this.dataInscricao = new Date();
		this.linhaPesquisaPrincipal = null;
		this.linhaPesquisaSecundaria = null;
		this.observacoes = "";
		this.inscricaoVerificada = false;
		this.documentosPendente = true;
		this.topicos = new ArrayList<TopicoInteresse>();
	}

	/**
	 * Verifica se a inscrição foi verificada
	 */
	public boolean getInscricaoVerificada()
	{
		return inscricaoVerificada;
	}
	
	/**
	 * Verifica se a inscrição possui documentos pendentes
	 */
	public boolean getDocumentosPendente()
	{
		return documentosPendente;
	}
	
	/**
	 * Retorna o número de tópicos de interesse na inscrição
	 */
	public int getNumeroTopicosInteresse()
	{
		return topicos.size();
	}
	
	/**
	 * Retorna um tópico de interesse da inscrição, dado seu índice
	 */
	public TopicoInteresse pegaTopicoInteresseIndice(int indice)
	{
		return topicos.get(indice);
	}
	
	/**
	 * Adiciona um tópico de interesse na inscrição
	 */
	public void adicionaTopicoInteresse(TopicoInteresse topico)
	{
		for (int i = topicos.size()-1; i >= 0; i--)
		{
			TopicoInteresse topicoInteresse = this.topicos.get(i);
			
			if (topicoInteresse.getTopico() == topico.getTopico())
			{
				topicoInteresse.setDescricao(topico.getDescricao());
				return;
			}
		}
		
		this.topicos.add(topico);
	}
	
	/**
	 * Remove um tópico de interesse da inscrição
	 */
	public void removeTopicoInteresse(int indice)
	{
		this.topicos.remove(indice);
	}
	
	/**
	 * Retorna a lista de tópicos de interesse da inscrição
	 */
	public Iterable<TopicoInteresse> getTopicos()
	{
		return topicos;
	}

	/**
	 * Sobe um tópico de interesse na inscrição
	 */
	public void sobeTopicoInteresse(int indice)
	{
		TopicoInteresse topico = this.topicos.get(indice-1);
		this.topicos.set(indice-1, this.topicos.get(indice));
		this.topicos.set(indice, topico);
	}

	/**
	 * Desce um tópico de interesse na inscrição
	 */
	public void desceTopicoInteresse(int indice)
	{
		TopicoInteresse topico = this.topicos.get(indice+1);
		this.topicos.set(indice+1, this.topicos.get(indice));
		this.topicos.set(indice, topico);
	}
}