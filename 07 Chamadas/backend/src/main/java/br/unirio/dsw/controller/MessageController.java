package br.unirio.dsw.controller;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.unirio.dsw.service.message.TranslationService;
import br.unirio.dsw.utils.JsonUtils;

/**
 * Controller responsável pelo gerenciamento de mensagens e tradução
 * 
 * @author marciobarros
 */
@RestController
public class MessageController
{
    @Autowired
    private TranslationService messageSource;

	/**
	 * Ação AJAX que retorna todas as mensagens de um namespace
	 */
	@RequestMapping(value = "/translation", method = RequestMethod.GET, produces = "application/json")
	public String getMessages(@RequestParam String namespace, Locale locale)
	{
		Properties properties = messageSource.getMessages(locale);
		JsonObject jsonMessages = new JsonObject();
		
		for (Object oKey : properties.keySet())
		{
			String key = (String) oKey;
			
			if (key.startsWith(namespace))
			{
				String value = (String) properties.get(oKey);
				jsonMessages.addProperty(key, value);
			}
		}
		
		return JsonUtils.ajaxSuccess(jsonMessages);
	}
}