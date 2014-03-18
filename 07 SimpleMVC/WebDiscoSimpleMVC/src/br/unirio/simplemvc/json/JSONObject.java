package br.unirio.simplemvc.json;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that represents an object in JSON format
 * 
 * @author marcio.barros
 */
public class JSONObject 
{
	private StringBuilder sb;

	/**
	 * Initializes the JSON object
	 */
	public JSONObject()
	{
		this.sb = new StringBuilder("{");
	}
	
	/**
	 * Adds a comma to separate object fields, if required
	 */
	private void comma()
	{
		if (sb.length() > 1)
			sb.append(",");
	}

	/**
	 * Adds an integer-valued field in the object
	 */
	public JSONObject add(String name, int value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value);
		return this;
	}

	/**
	 * Adds an long-integer-valued field in the object
	 */
	public JSONObject add(String name, long value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value);
		return this;
	}

	/**
	 * Adds a floating-point-valued field in the object
	 */
	public JSONObject add(String name, double value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value);
		return this;
	}

	/**
	 * Adds a boolean field in the object
	 */
	public JSONObject add(String name, boolean value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value);
		return this;
	}

	/**
	 * Adds a date time field in the object
	 */
	public JSONObject add(String name, Date value)
	{
		comma();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sb.append("\"" + name.replace("\"", "\\\"") + "\": \"" + sdf.format(value) + "\"");
		return this;
	}

	/**
	 * Adds an array field in the object
	 */
	public JSONObject add(String name, JSONArray value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value.toString());
		return this;
	}

	/**
	 * Adds a JSON-object field in the object
	 */
	public JSONObject add(String name, JSONObject value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":" + value.toString());
		return this;
	}
	
	/**
	 * Adds a string field in the object
	 */
	public JSONObject add(String name, String value)
	{
		comma();
		sb.append("\"" + name.replace("\"", "\\\"") + "\":\"" + value.replace("\"", "\\\"") + "\"");
		return this;
	}
	
	/**
	 * Returns the string that represents the JSON object
	 */
	public String toString()
	{
		return sb.toString() + "}";
	}
}