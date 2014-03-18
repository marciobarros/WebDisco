package br.unirio.simplemvc.actions.response;

import java.io.IOException;

/**
 * Interface that allows an action to produce an AJAX response. Non-AJAX
 * actions are usually forwarded to JSP pages that will produce their
 * response, so the most common use of this interface is for AJAX actions.
 * 
 * @author marcio.barros
 */
public interface IResponseConnector
{
	/**
	 * Writes something to the response of an AJAX action
	 */
	void write (String s) throws IOException;
}