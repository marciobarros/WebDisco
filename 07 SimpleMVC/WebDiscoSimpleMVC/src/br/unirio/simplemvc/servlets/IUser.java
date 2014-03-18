package br.unirio.simplemvc.servlets;

/**
 * Classe que representa um usu�rio abstrato
 * 
 * @author Marcio Barros
 */
public interface IUser 
{
	/**
	 * Retorna o identificador do usu�rio
	 */
	public int getId();
	
	/**
	 * Retorna o nome do usu�rio
	 */
	public String getName();
	
	/**
	 * Retorna o e-mail do usu�rio
	 */
	public String getEmail();
	
	/**
	 * Verifica se o usu�rio deve trocar a senha
	 */
	public boolean mustChangePassword();

	/**
	 * Verifica se o usu�rio est� ativo
	 */
	public boolean isActive();
	
	/**
	 * Verifica se o usu�rio tem um determinado n�vel de acesso no sistema
	 * 
	 * @param level		N�vel de acesso desejado
	 */
	public boolean checkLevel(String level);
}