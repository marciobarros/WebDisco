package br.unirio.simplemvc.dao;

public enum OrderingType
{
	Ascending("ASC"),
	Descending("DESC");
	
	private String clause;
	
	private OrderingType(String s)
	{
		this.clause = s;
	}
	
	public String getClause()
	{
		return this.clause;
	}

	public static OrderingType get(String name)
	{
		return name.contains("DESC") ? OrderingType.Descending : OrderingType.Ascending;
	}
}