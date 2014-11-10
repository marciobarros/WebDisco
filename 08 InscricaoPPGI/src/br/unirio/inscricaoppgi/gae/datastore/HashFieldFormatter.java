package br.unirio.inscricaoppgi.gae.datastore;

public class HashFieldFormatter
{
	private String encode(String s)
	{
		if (s == null)
			return "\"\"";
		
		return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
	}
	
	public String toString(HashField hs)
	{
		StringBuffer sb = new StringBuffer();
		
		for (String name : hs.getNames())
		{
			if (sb.length() > 0)
				sb.append(' ');
			
			String value = hs.getString(name);
			sb.append(name + "=" + encode(value));
		}
		
		return sb.toString();
	}
}