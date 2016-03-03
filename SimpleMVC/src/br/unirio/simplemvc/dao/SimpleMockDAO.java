package br.unirio.simplemvc.dao;

/**
 * Mock para a implementação de DAOs para testes
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
	 * Coloca o mock em modo de falha de conexão
	 */
	public void setConnectionFailureMode()
	{
		this.connectionFailureMode = true;
	}
	
	/**
	 * Retira o mock do modo de falha de conexão
	 */
	public void clearConnectionFailureMode()
	{
		this.connectionFailureMode = false;
	}
	
	/**
	 * Verifica se o mock está em modo de falha de conexão
	 */
	public boolean isConnectionFailureMode()
	{
		return this.connectionFailureMode;
	}
}