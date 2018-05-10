package br.unirio.dsw.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe que permite acesso aos dados de configuracao do sistema (.properties)
 * 
 * @author marcio.barros
 */
public class Configuration 
{
	private static Properties configuracao = null;

	/**
	 * Carrega as configuracoes do arquivo
	 */
	private static void carregaConfiguracao()
	{
		configuracao = new Properties();
		
		try 
		{
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties");
			
			if (is != null)
				configuracao.load(is);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retorna a string de conexão ao banco de dados
	 */
	public static String getDatabaseConnection()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_STRING").trim(); 
	}

	/**
	 * Retorna o usuário do banco de dados
	 */
	public static String getDatabaseUser()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_USER").trim(); 
	}

	/**
	 * Retorna a senha de acesso ao banco de dados
	 */
	public static String getDatabasePassword()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("CONNECTION_PASSWORD").trim(); 
	}
}