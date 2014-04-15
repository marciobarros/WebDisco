package br.unirio.inscricaoppgi.reports.inscritos;

import lombok.Data;
import br.unirio.inscricaoppgi.model.InscricaoEdital;
import br.unirio.inscricaoppgi.model.Usuario;

/**
 * Classe que representa uma linha no relatório de inscritos
 * 
 * @author Marcio
 */
public @Data class LinhaRelatorio
{
	private InscricaoEdital inscricao;
	private Usuario candidato;

	public LinhaRelatorio(InscricaoEdital inscricao, Usuario candidato)
	{
		this.inscricao = inscricao;
		this.candidato = candidato;
	}
}