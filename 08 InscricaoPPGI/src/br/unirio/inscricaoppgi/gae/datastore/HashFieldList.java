package br.unirio.inscricaoppgi.gae.datastore;

import java.util.ArrayList;
import java.util.List;

public class HashFieldList
{
	private List<HashField> fieldsList;

	public HashFieldList()
	{
		this.fieldsList = new ArrayList<HashField>();
	}

	public int count()
	{
		return fieldsList.size();
	}
	
	public void add(HashField fields)
	{
		fieldsList.add(fields);
	}

	public HashField get(int index)
	{
		return fieldsList.get(index);
	}
	
	public Iterable<HashField> list()
	{
		return fieldsList;
	}

	public void clear()
	{
		fieldsList.clear();
	}

	public String toString()
	{
		return new HashFieldListFormatter().toString(this);
	}

	public boolean fromString(String data)
	{
		return new HashFieldListParser().parse(data, this);
	}
}