package br.unirio.dsw.utils;

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