package br.unirio.dsw.utils;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Classe com utilitários para gerenciamento de arquivos JSON
 * 
 * @author Marcio Barros
 */
public class JsonUtils
{
	/**
	 * Compara dois elementos JSON
	 */
	public static boolean compara(JsonElement json1, JsonElement json2)
	{
		boolean isEqual = true;

		// Check whether both jsonElement are not null
		if (json1 != null && json2 != null)
		{
			// Check whether both jsonElement are objects
			if (json1.isJsonObject() && json2.isJsonObject())
			{
				Set<Entry<String, JsonElement>> ens1 = ((JsonObject) json1).entrySet();
				Set<Entry<String, JsonElement>> ens2 = ((JsonObject) json2).entrySet();
				JsonObject json2obj = (JsonObject) json2;
				
				if (ens1 != null && ens2 != null && (ens2.size() == ens1.size()))
				{
					// Iterate JSON Elements with Key values
					for (Entry<String, JsonElement> en : ens1)
					{
						isEqual = isEqual && compara(en.getValue(), json2obj.get(en.getKey()));
					}
				} 
				else
				{
					return false;
				}
			}

			// Check whether both jsonElement are arrays
			else if (json1.isJsonArray() && json2.isJsonArray())
			{
				JsonArray jarr1 = json1.getAsJsonArray();
				JsonArray jarr2 = json2.getAsJsonArray();
				
				if (jarr1.size() != jarr2.size())
				{
					return false;
				} 
				else
				{
					int i = 0;
					
					// Iterate JSON Array to JSON Elements
					for (JsonElement je : jarr1)
					{
						isEqual = isEqual && compara(je, jarr2.get(i));
						i++;
					}
				}
			}

			// Check whether both jsonElement are null
			else if (json1.isJsonNull() && json2.isJsonNull())
			{
				return true;
			}

			// Check whether both jsonElement are primitives
			else if (json1.isJsonPrimitive() && json2.isJsonPrimitive())
			{
				if (json1.equals(json2))
				{
					return true;
				} 
				else
				{
					return false;
				}
			} 
			else
			{
				return false;
			}
		} 
		else if (json1 == null && json2 == null)
		{
			return true;
		} 
		else
		{
			return false;
		}
		
		return isEqual;
	}
	
	/**
	 * Gera um retorno de sucesso em ação AJAX sem dados associados
	 */
	public static String ajaxSuccess()
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		return json.toString();
	}
	
	/**
	 * Gera um retorno de sucesso em ação AJAX com dados associados
	 */
	public static String ajaxSuccess(String jsonDados)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		json.addProperty("data", jsonDados);
		return json.toString();
	}
	
	/**
	 * Gera um retorno de sucesso em ação AJAX com dados associados
	 */
	public static String ajaxSuccess(JsonElement jsonDados)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		json.add("data", jsonDados);
		return json.toString();
	}
	
	/**
	 * Gera um retorno de sucesso em ação AJAX com dados associados
	 */
	public static String ajaxSuccess(JsonObject jsonDados)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		json.add("data", jsonDados);
		return json.toString();
	}
	
	/**
	 * Gera um retorno de sucesso em ação AJAX com dados associados
	 */
	public static String ajaxSuccess(JsonArray jsonDados)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "OK");
		json.add("data", jsonDados);
		return json.toString();
	}
	
	/**
	 * Gera um erro em uma ação AJAX
	 */
	public static String ajaxError(String message)
	{
		JsonObject json = new JsonObject();
		json.addProperty("result", "FAIL");
		json.addProperty("message", message);
		return json.toString();
	}
}