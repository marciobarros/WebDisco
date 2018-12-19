package br.unirio.dsw.service.message;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

/**
 * Classe que implementa o serviço de tradução
 * 
 * @author marcio.barros
 */
@Service("messageSource")
public class TranslationService extends ReloadableResourceBundleMessageSource
{
	/**
	 * Inicializa o serviço de tradução
	 */
	public TranslationService()
	{
		setBasename("classpath:i18n/messages");
		setUseCodeAsDefaultMessage(true);
	}
	
	/**
	 * Retorna uma mensagem traduzida
	 */
	public Properties getMessages(Locale locale)
	{
		return getMergedProperties(locale).getProperties();
	}
}