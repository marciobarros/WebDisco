package br.unirio.inscricaoppgi.gae.datastore;

public class HashFieldListParser
{
	private int position;
	private int len;
	private String data;
	
	private void advanceSpaces()
	{
		while (position < len && data.charAt(position) <= 32)
			position++;
	}

	private boolean getItemOpening()
	{
		advanceSpaces();
		
		if (position < len && data.charAt(position) == '[')
		{
			position++;
			return true;
		}
		
		return false;
	}

	private String getItem()
	{
		boolean withinQuote = false;
		int startPosition = position;
		
		while (position < len)
		{
			char c = data.charAt(position);
			position++;
			
			if (c == '\"')
				withinQuote = !withinQuote;
			
			else if (c == '\\')
				position++;
			
			else if (!withinQuote && c == ']')
			{
				return data.substring(startPosition, position-1);
			}
		}
		
		return null;
	}

	public boolean parse(String data, HashFieldList fieldsList)
	{
		fieldsList.clear();
		
		if (data == null)
			return true;
		
		this.position = 0;
		this.len = data.length();
		this.data = data;
		
		while (position < len)
		{
			if (!getItemOpening())
			{
				fieldsList.clear();
				return false;
			}
			
			String item = getItem();
			
			if (item == null)
			{
				fieldsList.clear();
				return false;
			}

			HashFieldParser parser = new HashFieldParser();
			HashField fields = new HashField();
			
			if (!parser.parse(item, fields))
			{
				fieldsList.clear();
				return false;
			}
			
			fieldsList.add(fields);
		}
		
		return true;
	}
}