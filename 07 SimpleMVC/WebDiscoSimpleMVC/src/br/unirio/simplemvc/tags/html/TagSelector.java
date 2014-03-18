package br.unirio.simplemvc.tags.html;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Classe que representa uma tag para um campo seletor de cadastros
 * 
 * @author Marcio Barros
 */
public class TagSelector extends TagSupport
{
	private static final long serialVersionUID = -5532187889876611476L;

	/**
	 * Nome do campo seletor
	 */
	private String name;

	/**
	 * Nome da coleção de elementos na requisição
	 */
	private String listName;
	
	/**
	 * Campo de um elemento da coleção que representa seu nome
	 */
	private String nameField;

	/**
	 * Campo de um elemento da coleção que representa seu valor
	 */
	private String valueField;

	/**
	 * Valor selecionado na lista
	 */
	private String value;

	/**
	 * Altera o nome do campo seletor
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Altera o nome da coleção de elementos na requisição
	 */
	public void setListName(String listName)
	{
		this.listName = listName;
	}

	/**
	 * Altera o campo de um elemento da coleção que representa seu nome
	 */
	public void setNameField(String nameField)
	{
		this.nameField = nameField;
	}

	/**
	 * Altera o campo de um elemento da coleção que representa seu valor
	 */
	public void setValueField(String valueField)
	{
		this.valueField = valueField;
	}

	/**
	 * Altera o valor selecionado na lista
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
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut();

		List<?> collection = (List<?>) request.getAttribute(listName);

		if (collection == null)
			return SKIP_BODY;

		try
		{
			out.println("<select name=\"" + StringEscapeUtils.escapeHtml4(name) + "\" id=\"" + StringEscapeUtils.escapeHtml4(name) + "\" size=\"1\">");

			for (int i = 0; i < collection.size(); i++)
			{
				Object data = collection.get(i);
				Method fValueMethod = data.getClass().getDeclaredMethod("get" + Character.toUpperCase(valueField.charAt(0)) + valueField.substring(1));
				Method fNameMethod = data.getClass().getDeclaredMethod("get" + Character.toUpperCase(nameField.charAt(0)) + nameField.substring(1));

				String value = fValueMethod.invoke(data).toString();
				String name = fNameMethod.invoke(data).toString();

				out.print("<option value='" + StringEscapeUtils.escapeHtml4(value) + "'");

				if (value.compareToIgnoreCase(this.value) == 0)
					out.print(" SELECTED");

				out.println(">" + StringEscapeUtils.escapeHtml4(name) + "</option>");
			}

			out.println("</select>");
		} catch (Exception e)
		{
			throw new JspException(e);
		}

		return (SKIP_BODY);
	}
}