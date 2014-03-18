package br.unirio.simplemvc.tags.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Classe que representa uma tag para um seletor simples
 * 
 * @author Marcio Barros
 */
public class TagSimpleSelector extends TagSupport
{
	private static final long serialVersionUID = -5532187889876611476L;

	/**
	 * Nome do campo seletor
	 */
	private String name;
	
	/**
	 * Classe CSS do campo seletor
	 */
	private String classe;
	
	/**
	 * Estilo CSS do campo seletor
	 */
	private String estilo;
	
	/**
	 * Lista de nomes apresentados na lista
	 */
	private String[] names;

	/**
	 * Lista de valores separados por ponto e vírgula
	 */
	private String[] values;

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
	 * Altera a classe CSS do campo seletor
	 */
	public void setClass(String _class)
	{
		this.classe = _class;
	}

	/**
	 * Altera o estilo CSS do campo seletor
	 */
	public void setStyle(String style)
	{
		this.estilo = style;
	}

	/**
	 * Indica os nomes das opções do seletor
	 */
	public void setOptionNames(String names)
	{
		this.names = names.split(";");
	}

	/**
	 * Indica os valores das opções do seletor
	 */
	public void setOptionValues(String values)
	{
		this.values = values.split(";");
	}

	/**
	 * Indica os nomes das opções do seletor
	 */
	public void setColNames(String key)
	{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		this.names = (String[]) request.getAttribute(key);
	}

	/**
	 * Indica os valores das opções do seletor
	 */
	public void setColValues(String key)
	{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		this.values = (String[]) request.getAttribute(key);
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
		JspWriter out = pageContext.getOut();

		if (values != null && values.length != names.length)
			throw new JspException("O número de valores está diferente do número de campos no seletor '" + name + "'");

		try
		{
			out.print("<select name=\"" + StringEscapeUtils.escapeHtml4(name) + "\" size=\"1\" id=\"" + StringEscapeUtils.escapeHtml4(name) + "\"");
			
			if (estilo != null && estilo.length() > 0)
				out.print(" style=\"" + StringEscapeUtils.escapeHtml4(estilo) + "\"");
			
			if (classe != null && classe.length() > 0)
				out.print(" class=\"" + StringEscapeUtils.escapeHtml4(classe) + "\"");
			
			out.println(">");
				
			for (int i = 0; i < names.length; i++)
			{
				String currentValue = (values != null) ? values[i] : ("" + i);
				out.print("<option value='" + StringEscapeUtils.escapeHtml4(currentValue) + "'");
				
				if (currentValue.compareToIgnoreCase(this.value) == 0)
					out.print(" SELECTED");

				out.println(">" + StringEscapeUtils.escapeHtml4(names[i]) + "</option>");
			}

			out.println("</select>");
		} catch (Exception e)
		{
			throw new JspException(e);
		}

		return (SKIP_BODY);
	}
}