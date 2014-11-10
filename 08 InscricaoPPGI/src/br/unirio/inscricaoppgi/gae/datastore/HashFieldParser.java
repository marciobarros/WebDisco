package br.unirio.inscricaoppgi.gae.datastore;

public class HashFieldParser
{
	private int position;
	private int len;
	private String data;
	
	private void advanceSpaces()
	{
		while (position < len && data.charAt(position) <= 32)
			position++;
	}
	
	private boolean isNumberCharacter(char c)
	{
		return (c >= '0' && c <= '9');
	}

	private boolean isValidIdentifierCharacter(char c)
	{
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || (c == '_');
	}

	private boolean isValueFinishCharacter(char c, boolean hasQuote)
	{
		if (hasQuote)
			return c == '\"';
		
		return c <= 32;
	}

	private String getIdentifier()
	{
		advanceSpaces();
		String id = "";
		
		while (position < len && isValidIdentifierCharacter(data.charAt(position)))
		{
			id += data.charAt(position);
			position++;
		}

		if (position < len && id.length() == 0)
			return null;
		
		if (id.length() > 0 && isNumberCharacter(id.charAt(0)))
			return null;
		
		return id;
	}

	private boolean getEquals()
	{
		advanceSpaces();
		
		if (position < len && data.charAt(position) == '=')
		{
			position++;
			return true;
		}
		
		return false;
	}

	private String getValue()
	{
		advanceSpaces();
		boolean hasQuote = false;
		String value = "";
		
		if (position < len && data.charAt(position) == '\"')
		{
			position++;
			hasQuote = true;
		}
		
		while (position < len && !isValueFinishCharacter(data.charAt(position), hasQuote))
		{
			char c = data.charAt(position);
			position++;
			
			if (hasQuote && c == '\\')
			{
				char c2 = data.charAt(position);
				position++;
				
				if (c2 == '\\' || c2 == '\"')
					value += c2;
				else
					value += c + c2;
			}
			else			
				value += c;
		}
		
		position++;
		return value;
	}

	public boolean parse(String data, HashField fields)
	{
		fields.clear();
		
		if (data == null)
			return true;
		
		this.position = 0;
		this.len = data.length();
		this.data = data;
		
		while (position <= len)
		{
			String name = getIdentifier();
			
			if (name == null)
			{
				fields.clear();
				return false;
			}
			
			if (name.length() == 0)
				break;

			if (!getEquals())
			{
				fields.clear();
				return false;
			}

			String value = getValue();
			
			if (value == null)
			{
				fields.clear();
				return false;
			}
			
			fields.addField(name, value);
		}
		
		return true;
	}
}