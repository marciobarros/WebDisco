package br.unirio.simplemvc.actions.data;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Implementation of the data connector for actions enacted by a servlet
 * 
 * @author marcio.barros
 */
public class ServletDataConnector implements IDataConnector
{
	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;

	/**
	 * Initializes the servlet data connector
	 */
	public ServletDataConnector(HttpServletRequest request, HttpServletResponse response)
	{
		this.servletRequest = request;
		this.servletResponse = response;
	}

	/**
	 * Gets the value stored in a parameter
	 */
	@Override
	public String getParameter(String name)
	{
		String value = servletRequest.getParameter(name);
		
		if (value == null)
			return null;
		
		return xssPrevent(value);
	}

	/**
	 * Determina se um caractere é indicação de potencial ataque de XSS
	 */
	public boolean xssCharacter(char c)
	{
		return (c == '>' || c == '<' || c == '\n' || c == '\r');
	}
	
	/**
	 * Remove os caracteres que podem indicar potencial ataque de XSS de uma string
	 */
	public String xssPrevent(String s)
	{
		int len = s.length();
		
		while (len > 0 && xssCharacter(s.charAt(len-1)))
		{
			s = s.substring(0, len-1);
			len--;
		}
		
		for (int i = len-1; i >= 0; i--)
			if (xssCharacter(s.charAt(i)))
				s = s.substring(0, i) + s.substring(i+1);
		
		return s;
	}

	/**
	 * Gets the value stored in a cookie
	 */
	@Override
	public String getCookie(String name)
	{
		Cookie[] cookies = servletRequest.getCookies();
		
		if (cookies == null)
			return null;
		
		for (Cookie cookie : cookies)
			if (cookie.getName().compareTo(name) == 0)
				return cookie.getValue();
		
		return null;
	}

	/**
	 * Sets the value to be stored in a cookie
	 */
	@Override
	public void setCookie(String name, String value)
	{
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
		servletResponse.addCookie(cookie);
	}

	/**
	 * Gets the value stored in a request attribute
	 */
	@Override
	public Object getAttribute(String name)
	{
		return servletRequest.getAttribute(name);
	}
	
	/**
	 * Sets the value to be stored in a request attribute
	 */
	@Override
	public void setAttribute(String name, Object value)
	{
		servletRequest.setAttribute(name, value);
	}

	/**
	 * Gets the file items uploaded by a form
	 */
	@Override
	public FileItemIterator getFileIterator()
	{
		try
		{
			ServletFileUpload upload = new ServletFileUpload();
			return upload.getItemIterator(servletRequest);
		} catch (IOException e)
		{
			return null;
		} catch (FileUploadException e)
		{
			return null;
		}
	}
}