package br.unirio.simplemvc.actions.data;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;

/**
 * Interface that provides data to be consumed or registers data produced by an action
 * 
 * @author marcio.barros
 */
public interface IDataConnector
{
	/**
	 * Gets the value of a parameter
	 */
	String getParameter(String name);
	
	/**
	 * Gets the value of a cookie
	 */
	String getCookie(String name);
	
	/**
	 * Sets the value of a cookie
	 */
	void setCookie(String name, String value);
	
	/**
	 * Gets the value of a request attribute
	 */
	Object getAttribute(String name);
	
	/**
	 * Sets the value of an attribute
	 */
	void setAttribute(String name, Object value);
	
	/**
	 * Gets the file items uploaded by a form
	 */
	FileItemIterator getFileIterator();
}