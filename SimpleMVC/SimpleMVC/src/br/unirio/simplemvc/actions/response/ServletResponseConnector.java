package br.unirio.simplemvc.actions.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Response connector for actions that sends its results through the
 * servlet response object
 * 
 * @author marcio.barros
 */
public class ServletResponseConnector implements IResponseConnector
{
	private HttpServletResponse response;
	
	/**
	 * Initializes the response connector for servlets
	 */
	public ServletResponseConnector(HttpServletResponse response)
	{
		this.response = response;
	}
	
	/**
	 * Writes something to the response of an AJAX action. Sends
	 * the AJAX results through the servlet response object.
	 */
	@Override
	public void write(String s) throws IOException
	{
		response.getWriter().println(s);
	}
}