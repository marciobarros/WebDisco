package br.unirio.simplemvc.servlets;


/**
 * Classe abstrata dos servi�os que devem ser oferecidos para o usu�rio
 * 
 * @author Marcio Barros
 */
public interface IUserService 
{
	/**
	 * Retorna um usu�rio, dado seu identificador
	 */
	IUser getUserId(int id);

	/**
	 * Retorna um usu�rio, dado seu e-mail
	 */
	public IUser getUserEmail(String email);
	
	/**
	 * Retorna a URL da a��o para login
	 */
	public String getLoginAction();
	
	/**
	 * Retorna a URL da a��o para troca de senha
	 */
	public String getChangePasswordAction();
}