package br.unirio.dsw.model;

import java.util.HashMap;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa a inscrição em uma chamada
 * 
 * @author Marcio Barros
 */
public class InscricaoChamada
{
	private @Getter @Setter int id;
	private @Getter @Setter Chamada chamada;
	private @Getter @Setter int idUsuario;
	private @Getter @Setter DateTime dataRegistro;
	private @Getter @Setter DateTime dataInscricao;
	private @Getter @Setter boolean cancelada;
	private HashMap<CampoChamada, String> valoresCampos;
	
	/**
	 * Inicializa uma inscrição em chamada
	 */
	public InscricaoChamada(Chamada chamada)
	{
		this.id = -1;
		this.chamada = chamada;
		this.idUsuario = -1;
		this.dataRegistro = DateTime.now();
		this.dataInscricao = null;
		this.cancelada = false;
		this.valoresCampos = new HashMap<CampoChamada, String>();
	}
	
	/**
	 * Retorna o valor de um campo da chamada na inscrição
	 */
	public String pegaValorCampo(CampoChamada campo)
	{
		String valor = valoresCampos.get(campo);
		return (valor != null) ? valor: "";
	}

	/**
	 * Altera o valor de um campo da chamada na inscrição
	 */
	public void mudaValorCampo(CampoChamada campo, String valor)
	{
		valoresCampos.put(campo, valor);
	}
}