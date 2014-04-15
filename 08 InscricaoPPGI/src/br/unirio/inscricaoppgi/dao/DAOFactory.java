package br.unirio.inscricaoppgi.dao;

/**
 * Fábrica que controla o acesso aos objetos que tratam de persistência de dados
 * 
 * @author Marcio
 */
public class DAOFactory
{
	private static UsuarioDAO usuarioDAO;
	private static UsuarioLoginDAO loginUsuarioDAO;
	private static UsuarioTokenSenhaDAO tokenSenhaUsuarioDAO;
	private static EditalDAO editalDAO;
	private static InscricaoEditalDAO inscricaoEdicaoDAO;
	private static MunicipioDAO municipioDAO;

	/**
	 * Retorna o objeto que trata da persistência de usuários
	 */
	public static UsuarioDAO getUsuarioDAO()
	{
		if (usuarioDAO == null)
			usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO;
	}

	/**
	 * Retorna o objeto que trata da persistência dos logins de usuários
	 */
	public static UsuarioLoginDAO getUsuarioLoginDAO()
	{
		if (loginUsuarioDAO == null)
			loginUsuarioDAO = new UsuarioLoginDAO();
		
		return loginUsuarioDAO;
	}

	/**
	 * Retorna o objeto que trata da persistência dos tokens de login
	 */
	public static UsuarioTokenSenhaDAO getUsuarioTokenSenhaDAO()
	{
		if (tokenSenhaUsuarioDAO == null)
			tokenSenhaUsuarioDAO = new UsuarioTokenSenhaDAO();
		
		return tokenSenhaUsuarioDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persistência dos editais
	 */
	public static EditalDAO getEditalDAO()
	{
		if (editalDAO == null)
			editalDAO = new EditalDAO();
		
		return editalDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persistência das inscrições em editais
	 */
	public static InscricaoEditalDAO getInscricaoEditalDAO()
	{
		if (inscricaoEdicaoDAO == null)
			inscricaoEdicaoDAO = new InscricaoEditalDAO();
		
		return inscricaoEdicaoDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persistência de municípios
	 */
	public static MunicipioDAO getMunicipioDAO()
	{
		if (municipioDAO == null)
			municipioDAO = new MunicipioDAO();
		
		return municipioDAO;
	}
}