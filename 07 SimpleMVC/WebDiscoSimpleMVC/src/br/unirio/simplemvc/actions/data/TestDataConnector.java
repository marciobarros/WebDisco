package br.unirio.simplemvc.actions.data;

import java.util.HashMap;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;

/**
 * Implementation of a data connector for testing purposes
 * 
 * @author marcio.barros
 */
public class TestDataConnector implements IDataConnector
{
	private HashMap<String, String> parameters;
	private HashMap<String, Object> attributes;
	private HashMap<String, String> cookies;

	/**
	 * Initializes the testing data connector
	 */
	public TestDataConnector()
	{
		this.parameters = new HashMap<String, String>();
		this.attributes = new HashMap<String, Object>();
		this.cookies = new HashMap<String, String>();
	}
	
	/**
	 * Externally sets the value of a parameter - the test method may call
	 * this method to setup data to be consumed by the action
	 */
	public TestDataConnector setupParameter(String name, String value)
	{
		this.parameters.put(name, value);
		return this;
	}
	
	/**
	 * Externally sets the value of an attribute - the test method may call
	 * this method to setup data to be consumed by the action
	 */
	public TestDataConnector setupAttribute(String name, Object value)
	{
		this.attributes.put(name, value);
		return this;
	}
	
	/**
	 * Externally sets the value of a cookie - the test method may call
	 * this method to setup data to be consumed by the action
	 */
	public TestDataConnector setupCookie(String name, String value)
	{
		this.cookies.put(name, value);
		return this;
	}

	/**
	 * Gets the value of a parameter
	 */
	@Override
	public String getParameter(String name)
	{
		return parameters.get(name);
	}

	/**
	 * Gets the value of a cookie
	 */
	@Override
	public String getCookie(String name)
	{
		return cookies.get(name);
	}

	/**
	 * Sets the value of a cookie
	 */
	@Override
	public void setCookie(String name, String value)
	{
		this.cookies.put(name, value);
	}

	/**
	 * Gets the value of an attribute
	 */
	@Override
	public Object getAttribute(String name)
	{
		return attributes.get(name);
	}

	/**
	 * Sets the value of an attribute
	 */
	@Override
	public void setAttribute(String name, Object value)
	{
		this.attributes.put(name, value);
	}

	/**
	 * Gets the file items uploaded by a form
	 */
	@Override
	public FileItemIterator getFileIterator()
	{
		return null;
	}
}