package br.unirio.inscricaoppgi.reports.inscritos;

import java.util.ArrayList;
import java.util.List;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.InscricaoEdital;
import br.unirio.inscricaoppgi.model.LinhaPesquisa;
import br.unirio.inscricaoppgi.model.Usuario;

/**
 * Classe que gera o relatório de inscritos
 * 
 * @author Marcio
 */
public class FiltroInscritos
{
	/**
	 * Executa o relatório de inscritos
	 */
	public List<LinhaRelatorio> executa(String filtroNome, LinhaPesquisa filtroLinha, int filtroEdital, int filtroDocumentacao, int filtroInscricao)
	{
		filtroNome = filtroNome.toUpperCase();
		List<LinhaRelatorio> resultado = new ArrayList<LinhaRelatorio>();
		List<InscricaoEdital> inscricoes = DAOFactory.getInscricaoEditalDAO().pegaInscricoesEdital(filtroEdital);
		
		for (InscricaoEdital inscricao : inscricoes)
		{
			if (filtroLinha != null && inscricao.getLinhaPesquisaPrincipal() != filtroLinha)
				continue;
			
			if (filtroDocumentacao != -1 && !verificaStatusDocumentacao(inscricao, filtroDocumentacao))
				continue;
			
			if (filtroInscricao != -1 && !verificaStatusInscricao(inscricao, filtroInscricao))
				continue;
			
			Usuario candidato = DAOFactory.getUsuarioDAO().get(inscricao.getIdCandidato());
			
			if (candidato == null)
				continue;
			
			if (filtroNome.length() != 0 && !candidato.getNome().toUpperCase().contains(filtroNome))
				continue;
			
			resultado.add(new LinhaRelatorio(inscricao, candidato));
		}
		
		return resultado;
	}

	/**
	 * Verifica se o status de documentação é compatível com o filtro selecionado
	 */
	private boolean verificaStatusDocumentacao(InscricaoEdital inscricao, int statusDocumentacao)
	{
		if (statusDocumentacao == 0)
			return !inscricao.getDocumentosPendente();
		
		if (statusDocumentacao == 1)
			return inscricao.getDocumentosPendente();
		
		return false;
	}

	/**
	 * Verifica se o status de inscrição é compatível com o filtro selecionado
	 */
	private boolean verificaStatusInscricao(InscricaoEdital inscricao, int statusInscricao)
	{
		if (statusInscricao == 0)
			return inscricao.getInscricaoVerificada();
		
		if (statusInscricao == 1)
			return !inscricao.getInscricaoVerificada();
		
		return false;
	}
}