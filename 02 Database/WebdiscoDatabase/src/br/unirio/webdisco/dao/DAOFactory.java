package br.unirio.webdisco.dao;

import br.unirio.webdisco.dao.compactdisc.CompactDiscDAO;
import br.unirio.webdisco.dao.compactdisc.ICompactDiscDAO;

/**
 * Classe que concentra o acesso aos DAOs do sistema
 */
public class DAOFactory
{
	private static ICompactDiscDAO compactDiscDAO;

	/**
	 * Retorna a inst�ncia do DAO para persist�ncia de CDs
	 */
	public static ICompactDiscDAO getCompactDiscDAO()
	{
		if (compactDiscDAO == null)
			compactDiscDAO = new CompactDiscDAO();
		
		return compactDiscDAO;
	}

	/**
	 * Altera a inst�ncia do DAO para persist�ncia de CDs - m�todo para ser usado em testes
	 */
	public static void setCompactDiscDAO(ICompactDiscDAO compactDiscDAO)
	{
		DAOFactory.compactDiscDAO = compactDiscDAO;
	}
}