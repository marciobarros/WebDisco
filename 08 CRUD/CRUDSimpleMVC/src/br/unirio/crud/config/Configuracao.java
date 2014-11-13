package br.unirio.crud.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe que permite acesso aos dados de configuração do sistema, que estão
 * armazenados em um arquivo .properties na raiz
 * 
 * @author marcio.barros
 */
public class Configuracao 
{
	private static Properties configuracao = null;
	
	/**
	 * Carrega as configurações do disco
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
	 * Retorna o endereço do servidor de e-mails
	 */
	public static String getSmtpGatewayHostname()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_HOSTNAME").trim(); 
	}

	/**
	 * Retorna o usuário do servidor de e-mails
	 */
	public static String getSmtpGatewayUser()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_USER").trim(); 
	}

	/**
	 * Retorna a senha do usuário no servidor de e-mails
	 */
	public static String getSmtpGatewayPassword()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_SENHA").trim(); 
	}
	
	/**
	 * Retorna o endereço de origem dos e-mail
	 */
	public static String getEmailOrigem()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("EMAIL_ORIGEM").trim(); 
	}
	
	/**
	 * Retorna o endereço do host
	 */
	public static String getHostname()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("HOSTNAME").trim(); 
	}

	/**
	 * Retorna o prefixo de notificação de e-mail
	 */
	public static String getPrefixoNotificacaoEmail()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("MAIL_NOTICE").trim(); 
	}

	/**
	 * Retorna o nome do ambiente
	 */
	public static String getNomeAmbiente()
	{
		if (configuracao == null)
			carregaConfiguracao();
		
		return configuracao.getProperty("NOME_AMBIENTE").trim(); 
	}
	
	/**
	 * Verifica se está executando em ambiente de homologação
	 */
	public static boolean getAmbienteHomologacao()
	{
		return getNomeAmbiente().trim().length() > 0; 
	}
}