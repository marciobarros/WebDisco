package br.unirio.simplemvc.actions.response;

import java.io.IOException;

/**
 * Response connector used for testing purposes. Concatenates all
 * writes received in a string that can be later compared to
 * expected results.
 * 
 * @author marcio.barros
 */
public class TestResponseConnector implements IResponseConnector
{
	private StringBuffer result;

	/**
	 * Initializes the response connector
	 */
	public TestResponseConnector()
	{
		this.result = new StringBuffer();
	}
	
	/**
	 * Returns action results
	 */
	public String getResult()
	{
		return result.toString();
	}
	
	/**
	 * Writes something to the action results
	 */
	@Override
	public void write(String s) throws IOException
	{
		result.append(s);
	}

	/**
	 * Cleanup all former output
	 */
	public void clear()
	{
		result.setLength(0);		
	}
}