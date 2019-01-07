package br.unirio.dsw.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa uma chamada em uma unidade
 * 
 * @author Marcio Barros
 */
public class Chamada
{
	private @Getter @Setter int id;
	private @Getter @Setter int idUnidade;
	private @Getter @Setter String nome;
	private @Getter @Setter String sigla;
	private @Getter @Setter DateTime dataAberturaInscricoes;
	private @Getter @Setter DateTime dataEncerramentoInscricoes;
	private @Getter @Setter boolean cancelada;
	private @Getter @Setter boolean encerrada;
	private List<CampoChamada> campos;
	private List<String> anexos;
	
	/**
	 * Inicializa uma chamada
	 */
	public Chamada()
	{
		this.id = -1;
		this.nome = "";
		this.sigla = "";
		this.dataAberturaInscricoes = null;
		this.dataEncerramentoInscricoes = null;
		this.idUnidade = -1;
		this.cancelada = false;
		this.encerrada = false;
		this.campos = new ArrayList<CampoChamada>();
		this.anexos = new ArrayList<String>();
	}
	
	/**
	 * Conta o número de campos da chamada
	 */
	public int contaCampos()
	{
		return campos.size();
	}
	
	/**
	 * Retorna um campo da chamada, dado seu índice
	 */
	public CampoChamada pegaCampoIndice(int indice)
	{
		return campos.get(indice);
	}
	
	/**
	 * Retorna a lista de campos da chamada
	 */
	public Iterable<CampoChamada> getCampos()
	{
		return campos;
	}
	
	/**
	 * Adiciona um campo da chamada
	 */
	public void adicionaCampo(CampoChamada campo)
	{
		this.campos.add(campo);
	}
	
	/**
	 * Remove um campos da chamada, dado seu índice
	 */
	public void removeCampo(int indice)
	{
		this.campos.remove(indice);
	}

	/**
	 * Remove todos os campos da chamada
	 */
	public void removeCampos()
	{
		this.campos.clear();
	}
	
	/**
	 * Conta o número de anexos da chamada
	 */
	public int contaAnexos()
	{
		return anexos.size();
	}
	
	/**
	 * Retorna um anexo da chamada, dado seu índice
	 */
	public String pegaAnexoIndice(int indice)
	{
		return anexos.get(indice);
	}
	
	/**
	 * Retorna a lista de anexos da chamada
	 */
	public Iterable<String> getAnexos()
	{
		return anexos;
	}
	
	/**
	 * Adiciona um anexo na chamada
	 */
	public void adicionaAnexo(String opcao)
	{
		this.anexos.add(opcao);
	}
	
	/**
	 * Remove um anexo da chamada, dado seu índice
	 */
	public void removeAnexo(int indice)
	{
		this.anexos.remove(indice);
	}

	/**
	 * Remove todos os anexos da chamada
	 */
	public void removeAnexos()
	{
		this.anexos.clear();
	}
}