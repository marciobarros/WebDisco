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
	 * Retorna a instância do DAO para persistência de CDs
	 */
	public static ICompactDiscDAO getCompactDiscDAO()
	{
		if (compactDiscDAO == null)
			compactDiscDAO = new CompactDiscDAO();
		
		return compactDiscDAO;
	}

	/**
	 * Altera a instância do DAO para persistência de CDs - método para ser usado em testes
	 */
	public static void setCompactDiscDAO(ICompactDiscDAO compactDiscDAO)
	{
		DAOFactory.compactDiscDAO = compactDiscDAO;
	}
}