package br.unirio.dsw.service.message;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Classe responsável pela internacionalização das mensagens
 * 
 * @author Marcio
 */
public class ExposedResourceMessageBundleSource extends ReloadableResourceBundleMessageSource
{
	public Properties getMessages(Locale locale)
	{
		return getMergedProperties(locale).getProperties();
	}
}