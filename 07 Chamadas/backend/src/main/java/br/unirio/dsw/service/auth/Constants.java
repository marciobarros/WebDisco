package br.unirio.dsw.service.auth;

/**
 * Constantes utilizadas no processo de autenticação
 * 
 * @author Marcio
 */
class Constants
{
	/**
	 * Nome do header que será enviado como resposta ao login e recebido em cada ação autenticada
	 */
	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	
	/**
	 * Chave do objeto que representa o usuário logado na memória de sessão
	 */
	public static final String SESSION_USER_KEY = "LOGGED-USER";
	
	/**
	 * Nome do header contendo a mensagem de erro de autenticação
	 */
	public static final String ERROR_HEADER_NAME = "error";
}