/**
 * Tag para redirecionamento de usuários não logados
 *
 * @author Márcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RedirectUnloggedTag extends TagSupport
{
	private static final long serialVersionUID = -4420519410964036298L;

    private String page = "";

    public void setPage (String page)
    {
        this.page = page;
    }

    public int doEndTag() throws JspException
    {
    	if (LoggingServices.getInstance().isUserLogged(pageContext))
    		return (EVAL_PAGE);
    	
        try
        {
            pageContext.forward(page);
        }
        catch (Exception e)
        {
            throw new JspException(e.toString());
        }

        return (SKIP_PAGE);
    }
}