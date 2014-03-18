package br.unirio.simplemvc.dao;

/**
 * Class that represents the result of selectors
 * 
 * @author marcio.barros
 */
public class SelectorData
{
	private int id;
	private String name;
	private String description;
	
	/**
	 * Initializes the instance represented in the selector
	 */
	public SelectorData(int id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Returns the instance's identification 
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Returns the instance's name 
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the instance's description 
	 */
	public String getDescription()
	{
		return description;
	}
}