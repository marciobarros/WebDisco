package br.unirio.simplemvc.servlets;

/**
 * Classe que representa um usuário abstrato
 * 
 * @author Marcio Barros
 */
public interface IUser 
{
	/**
	 * Retorna o identificador do usuário
	 */
	public int getId();
	
	/**
	 * Retorna o nome do usuário
	 */
	public String getName();
	
	/**
	 * Retorna o e-mail do usuário
	 */
	public String getEmail();
	
	/**
	 * Verifica se o usuário deve trocar a senha
	 */
	public boolean mustChangePassword();

	/**
	 * Verifica se o usuário está ativo
	 */
	public boolean isActive();
	
	/**
	 * Verifica se o usuário tem um determinado nível de acesso no sistema
	 * 
	 * @param level		Nível de acesso desejado
	 */
	public boolean checkLevel(String level);
}