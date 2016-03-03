package br.unirio.simplemvc.tags.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Classe que representa uma tag para um campo tipo check-box
 * 
 * @author Marcio Barros
 */
public class TagCheckbox extends TagSupport
{
	private static final long serialVersionUID = -5532187889876611476L;

	/**
	 * Nome do campo check-box
	 */
	private String name;

	/**
	 * Indica se a check-box está ligada
	 */
	private String value = "";

	/**
	 * Altera o nome do campo check-box
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Indica se a check-box está ligada
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Programa o conteúdo da tag
	 */
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();

		try
		{
			out.print("<input name=\"" + StringEscapeUtils.escapeHtml4(name) + "\" id=\"" + StringEscapeUtils.escapeHtml4(name) + "\" type=\"checkbox\" value=\"ON\"");

			if (value.compareToIgnoreCase("ON") == 0 || value.compareToIgnoreCase("true") == 0)
				out.print(" checked");
			
			out.println("/>");
		} catch (Exception e)
		{
			throw new JspException(e);
		}

		return (SKIP_BODY);
	}
}