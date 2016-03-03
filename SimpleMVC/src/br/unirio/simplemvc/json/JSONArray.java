package br.unirio.simplemvc.json;

/**
 * Class that represents an array of objects in JSON format
 * 
 * @author marcio.barros
 */
public class JSONArray 
{
	private StringBuilder sb;
	private int count;

	/**
	 * Initializes the JSON array
	 */
	public JSONArray()
	{
		this.sb = new StringBuilder("[");
		this.count = 0;
	}
	
	/**
	 * Adds a comma to separate items, if needed
	 */
	private void comma()
	{
		if (sb.length() > 1)
			sb.append(",");
	}

	/**
	 * Adds an integer value to the array
	 */
	public JSONArray add(int v)
	{
		comma();
		sb.append(v);
		count++;
		return this;
	}

	/**
	 * Adds a long integer value to the array
	 */
	public JSONArray add(long v)
	{
		comma();
		sb.append(v);
		count++;
		return this;
	}

	/**
	 * Adds a boolean value to the array
	 */
	public JSONArray add(boolean b)
	{
		comma();
		sb.append(b);
		count++;
		return this;
	}

	/**
	 * Adds an array to the array
	 */
	public JSONArray add(JSONArray arr)
	{
		comma();
		sb.append(arr.toString());
		count++;
		return this;
	}
	
	/**
	 * Adds a string to the array
	 */
	public JSONArray add(String s)
	{
		comma();
		sb.append("\"" + s.replace("\"", "\\\"") + "\"");
		count++;
		return this;
	}
	
	/**
	 * Adds a JSON object to the array
	 */
	public JSONArray add(JSONObject obj)
	{
		comma();
		sb.append(obj.toString());
		count++;
		return this;
	}
	
	/**
	 * Retorna o número de elementos no vetor
	 */
	public int length()
	{
		return count;
	}
	
	/**
	 * Returns the string that represents the array
	 */
	public String toString()
	{
		return sb.toString() + "]";
	}
}