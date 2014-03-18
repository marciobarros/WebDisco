package br.unirio.simplemvc.dao;

/**
 * Mock para a implementa��o de DAOs para testes
 * 
 * @author marcio.barros
 */
public class SimpleMockDAO
{
	private boolean connectionFailureMode;

	/**
	 * Inicializa o mock
	 */
	public SimpleMockDAO()
	{
		this.connectionFailureMode = false;
	}
	
	/**
	 * Coloca o mock em modo de falha de conex�o
	 */
	public void setConnectionFailureMode()
	{
		this.connectionFailureMode = true;
	}
	
	/**
	 * Retira o mock do modo de falha de conex�o
	 */
	public void clearConnectionFailureMode()
	{
		this.connectionFailureMode = false;
	}
	
	/**
	 * Verifica se o mock est� em modo de falha de conex�o
	 */
	public boolean isConnectionFailureMode()
	{
		return this.connectionFailureMode;
	}
}