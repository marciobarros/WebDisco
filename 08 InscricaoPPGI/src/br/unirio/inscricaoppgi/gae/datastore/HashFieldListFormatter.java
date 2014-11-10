package br.unirio.inscricaoppgi.gae.datastore;

public class HashFieldListFormatter
{
	public String toString(HashFieldList hsf)
	{
		StringBuffer sb = new StringBuffer();
		HashFieldFormatter formatter = new HashFieldFormatter();
		
		for (HashField hs : hsf.list())
		{
			if (sb.length() > 0)
				sb.append(' ');
			
			sb.append("[" + formatter.toString(hs) + "]");
		}
		
		return sb.toString();
	}
}