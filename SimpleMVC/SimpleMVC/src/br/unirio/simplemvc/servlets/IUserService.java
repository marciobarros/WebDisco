package br.unirio.simplemvc.servlets;


/**
 * Classe abstrata dos serviços que devem ser oferecidos para o usuário
 * 
 * @author Marcio Barros
 */
public interface IUserService 
{
	/**
	 * Retorna um usuário, dado seu identificador
	 */
	IUser getUserId(int id);

	/**
	 * Retorna um usuário, dado seu e-mail
	 */
	public IUser getUserEmail(String email);
	
	/**
	 * Retorna a URL da ação para login
	 */
	public String getLoginAction();
	
	/**
	 * Retorna a URL da ação para troca de senha
	 */
	public String getChangePasswordAction();
}