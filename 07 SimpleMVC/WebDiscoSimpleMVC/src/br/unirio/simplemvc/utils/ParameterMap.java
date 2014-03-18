package br.unirio.simplemvc.utils;

import java.util.HashMap;

/**
 * Classe que representa um mapa de parâmetros
 * 
 * @author marcio.barros
 */
public class ParameterMap extends HashMap<String, String[]>
{
	private static final long serialVersionUID = 6398910463153103533L;

	/**
	 * Adiciona um parâmetro do tipo string no mapa
	 */
	public ParameterMap add(String name, String value)
	{
		String[] values = this.get(name);
		
		if (values != null)
		{
			String[] newValues = new String[values.length + 1];
			
			for (int i = 0; i < values.length; i++)
				newValues[i] = values[i];
			
			newValues[values.length] = value;
		}
		else
			values = new String[] { value };

		this.put(name, values);
		return this;
	}

	/**
	 * Adiciona um parâmetro do tipo inteiro no mapa
	 */
	public ParameterMap add(String name, int value)
	{
		return this.add(name, Integer.toString(value));
	}
}