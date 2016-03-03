/**
 * Tag que processa seu conte�do de acordo com o n�vel do usu�rio logado
 *
 * @author M�rcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckUserLevelTag extends TagSupport
{
	private static final long serialVersionUID = 1L;

    private String userLevel = ""; 

    public void setLevel (String level)
    {
        this.userLevel = level;
    }

    public int doStartTag() throws JspException
    {
    	if (LoggingServices.getInstance().userHasLevel(pageContext, userLevel))
            return (EVAL_BODY_INCLUDE);

        return (SKIP_BODY);
    }
}