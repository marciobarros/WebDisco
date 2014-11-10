package br.unirio.inscricaoppgi.gae.datastore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HashField
{
	private HashMap<String, String> fields;

	/**
	 * Inicializa um campo do tipo hash table
	 */
	public HashField()
	{
		this.fields = new HashMap<String, String>();
	}

	/**
	 * Retorna o número de campos no hash table
	 */
	public int count()
	{
		return fields.size();
	}
	
	/**
	 * Adiciona um campo do tipo string no hash table
	 */
	public void addField(String name, String value)
	{
		fields.put(name, value);
	}
	
	/**
	 * Adiciona um campo do tipo inteiro no hash table
	 */
	public void addField(String name, int value)
	{
		addField(name, Integer.toString(value));
	}
	
	/**
	 * Adiciona um campo do tipo longo no hash table
	 */
	public void addField(String name, long value)
	{
		addField(name, Long.toString(value));
	}
	
	/**
	 * Adiciona um campo do tipo numérico no hash table
	 */
	public void addField(String name, double value)
	{
		addField(name, Double.toString(value));
	}
	
	/**
	 * Adiciona um campo do tipo data no hash table
	 */
	public void addField(String name, Date value)
	{
		addField(name, new SimpleDateFormat("yyyy-MM-dd").format(value));
	}
	
	/**
	 * Adiciona um campo do tipo booleano no hash table
	 */
	public void addField(String name, boolean value)
	{
		addField(name, value ? "true" : "false");
	}

	/**
	 * Retorna o valor de um campo da hash table
	 */
	public String getString(String name)
	{
		return fields.get(name);
	}

	/**
	 * Retorna o valor de um campo inteiro da hash table
	 */
	public int getInt(String name)
	{
		return Integer.parseInt(getString(name));
	}

	/**
	 * Retorna o valor de um campo longo da hash table
	 */
	public long getLong(String name)
	{
		return Long.parseLong(getString(name));
	}

	/**
	 * Retorna o valor de um campo numérico da hash table
	 */
	public double getDouble(String name)
	{
		return Double.parseDouble(getString(name));
	}

	/**
	 * Retorna o valor de um campo data da hash table
	 */
	public Date getDate(String name)
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd").parse(getString(name));
		}
		catch(ParseException pe)
		{
			return null;
		}
	}

	/**
	 * Retorna o valor de um campo booleano da hash table
	 */
	public boolean getBoolean(String name)
	{
		return getString(name).compareTo("true") == 0;
	}

	/**
	 * Retorna o valor de um campo da hash table
	 */
	public String getString(String name, String _default)
	{
		String value = fields.get(name); 
		return (value != null) ? value : _default;
	}

	/**
	 * Retorna o valor de um campo inteiro da hash table
	 */
	public int getInt(String name, int _default)
	{
		String value = fields.get(name); 
		
		if (value == null) 
			return _default;
		
		try
		{
			return Integer.parseInt(value);
		}
		catch (NumberFormatException nfe)
		{
			return _default;
		}
	}

	/**
	 * Retorna o valor de um campo longo da hash table
	 */
	public long getLong(String name, long _default)
	{
		String value = fields.get(name); 
		
		if (value == null) 
			return _default;
		
		try
		{
			return Long.parseLong(value);
		}
		catch (NumberFormatException nfe)
		{
			return _default;
		}
	}

	/**
	 * Retorna o valor de um campo numérico da hash table
	 */
	public double getDouble(String name, double _default)
	{
		String value = fields.get(name); 
		
		if (value == null) 
			return _default;
		
		try
		{
			return Double.parseDouble(value);
		}
		catch (NumberFormatException nfe)
		{
			return _default;
		}
	}

	/**
	 * Retorna o valor de um campo data da hash table
	 */
	public Date getDate(String name, Date _default)
	{
		String value = fields.get(name); 
		
		if (value == null) 
			return _default;
		
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd").parse(value);
		}
		catch (ParseException pe)
		{
			return _default;
		}
	}

	/**
	 * Retorna o valor de um campo booleano da hash table
	 */
	public boolean getBoolean(String name, boolean _default)
	{
		String value = fields.get(name); 
		
		if (value == null) 
			return _default;
		
		return value.compareTo("true") == 0;
	}
	
	/**
	 * Retorna a lista de nomes registrados na tabela
	 */
	public Iterable<String> getNames()
	{
		return fields.keySet();
	}

	/**
	 * Remove todos os campos da tabela
	 */
	public void clear()
	{
		fields.clear();
	}

	/**
	 * Gera a representação string da tabela
	 */
	public String toString()
	{
		return new HashFieldFormatter().toString(this);
	}

	/**
	 * Recupera a tabela a partir de uma string
	 */
	public boolean fromString(String data)
	{
		return new HashFieldParser().parse(data, this);
	}
}