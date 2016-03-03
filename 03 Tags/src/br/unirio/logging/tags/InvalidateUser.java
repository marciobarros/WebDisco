/**
 * Tag para invalidar um usuário registrado no sistema
 * 
 * @author Márcio Barros
 * @version 1.0 
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class InvalidateUser extends TagSupport
{
	private static final long serialVersionUID = 4770840665621482377L;

	public int doEndTag() throws JspException
	{
		LoggingServices.getInstance().invalidateUser(pageContext);
		return (EVAL_PAGE);
	}
}