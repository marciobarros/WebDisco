/**
 * Tag para trasnferencia de usuarios de acordo com seu nivel
 *
 * @author Marcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RedirectUserLevelTag extends TagSupport
{
	private static final long serialVersionUID = 3401513664028269734L;

    private String page = "";

    private String userLevel = "";

    public void setPage (String page)
    {
        this.page = page;
    }

    public void setLevel (String level)
    {
        this.userLevel = level;
    }

    public int doEndTag() throws JspException
    {
    	if (LoggingServices.getInstance().userHasLevel(pageContext, userLevel))
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