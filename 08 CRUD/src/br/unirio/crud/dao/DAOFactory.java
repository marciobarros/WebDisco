package br.unirio.crud.dao;

import br.unirio.crud.dao.municipio.IMunicipioDAO;
import br.unirio.crud.dao.municipio.MunicipioDAO;
import br.unirio.crud.dao.municipio.TestMunicipioDAO;
import br.unirio.crud.dao.usuario.IUsuarioDAO;
import br.unirio.crud.dao.usuario.TestUsuarioDAO;
import br.unirio.crud.dao.usuario.UsuarioDAO;

/**
 * Classe que concentra o acesso aos DAOs do sistema
 */
public class DAOFactory
{
	private static IMunicipioDAO municipioDAO;
	private static IUsuarioDAO usuarioDAO;

	/**
	 * Retorna a inst�ncia do DAO para persist�ncia dos municipios
	 */
	public static IMunicipioDAO getMunicipioDAO()
	{
		if (municipioDAO == null)
			municipioDAO = new MunicipioDAO();
		
		return municipioDAO;
	}

	/**
	 * Altera a inst�ncia do DAO para persist�ncia de municipios - m�todo para ser usado em testes
	 */
	public static void setMunicipioForTesting()
	{
		DAOFactory.municipioDAO = new TestMunicipioDAO();
	}

	/**
	 * Retorna a inst�ncia do DAO para persist�ncia dos usu�rios
	 */
	public static IUsuarioDAO getUsuarioDAO()
	{
		if (usuarioDAO == null)
			usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO;
	}

	/**
	 * Altera a inst�ncia do DAO para persist�ncia de usu�rios - m�todo para ser usado em testes
	 */
	public static void setUsuarioForTesting()
	{
		DAOFactory.usuarioDAO = new TestUsuarioDAO();
	}
}