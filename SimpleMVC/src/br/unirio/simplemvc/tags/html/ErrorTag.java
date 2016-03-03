package br.unirio.simplemvc.tags.html;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionError;

/**
 * Classe que apresenta os erros ligados a uma determinada ação
 * 
 * @author Marcio Barros
 */
@SuppressWarnings("serial")
public class ErrorTag extends TagSupport
{
	/**
	 * Nome do campo associado à ação
	 */
	private String name;
	
	/**
	 * Altera o campo associado à ação
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Apresenta os erros associados ao campo selecionado 
	 */
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
    {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		
		try 
		{
			List<ActionError> errors = (List<ActionError>) request.getAttribute(Action.ERRORS_KEY);

			if (errors != null)
				for (ActionError value : errors)
					if (name == null || name.length() == 0 || value.getField().compareToIgnoreCase(name) == 0)
						pageContext.getOut().print("<p class=\"error\">" + StringEscapeUtils.escapeHtml4(value.getMessage()) + "</p>");
			
			String errorParameter = request.getParameter(Action.ERRORS_KEY);
			
			if (errorParameter != null)
				pageContext.getOut().print("<p class=\"error\">" + StringEscapeUtils.escapeHtml4(errorParameter) + "</p>");
		} 
		catch (IOException e) 
		{
			new JspException(e.getMessage());
		}

        return (SKIP_BODY);
    }
}