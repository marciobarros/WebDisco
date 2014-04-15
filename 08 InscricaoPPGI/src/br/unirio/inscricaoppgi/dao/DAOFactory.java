package br.unirio.inscricaoppgi.dao;

/**
 * F�brica que controla o acesso aos objetos que tratam de persist�ncia de dados
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
	 * Retorna o objeto que trata da persist�ncia de usu�rios
	 */
	public static UsuarioDAO getUsuarioDAO()
	{
		if (usuarioDAO == null)
			usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO;
	}

	/**
	 * Retorna o objeto que trata da persist�ncia dos logins de usu�rios
	 */
	public static UsuarioLoginDAO getUsuarioLoginDAO()
	{
		if (loginUsuarioDAO == null)
			loginUsuarioDAO = new UsuarioLoginDAO();
		
		return loginUsuarioDAO;
	}

	/**
	 * Retorna o objeto que trata da persist�ncia dos tokens de login
	 */
	public static UsuarioTokenSenhaDAO getUsuarioTokenSenhaDAO()
	{
		if (tokenSenhaUsuarioDAO == null)
			tokenSenhaUsuarioDAO = new UsuarioTokenSenhaDAO();
		
		return tokenSenhaUsuarioDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persist�ncia dos editais
	 */
	public static EditalDAO getEditalDAO()
	{
		if (editalDAO == null)
			editalDAO = new EditalDAO();
		
		return editalDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persist�ncia das inscri��es em editais
	 */
	public static InscricaoEditalDAO getInscricaoEditalDAO()
	{
		if (inscricaoEdicaoDAO == null)
			inscricaoEdicaoDAO = new InscricaoEditalDAO();
		
		return inscricaoEdicaoDAO;
	}
	
	/**
	 * Retorna o objeto que trata da persist�ncia de munic�pios
	 */
	public static MunicipioDAO getMunicipioDAO()
	{
		if (municipioDAO == null)
			municipioDAO = new MunicipioDAO();
		
		return municipioDAO;
	}
}