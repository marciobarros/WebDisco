/**
 * Tag que processa seu conteudo de acordo com o nivel do usuario logado
 *
 * @author Marcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import lombok.Setter;

public class CheckUserLevelTag extends TagSupport
{
	private static final long serialVersionUID = 1L;

    private @Setter String level = ""; 

    public int doStartTag() throws JspException
    {
    	if (LoggingServices.getInstance().userHasLevel(pageContext, level))
            return EVAL_BODY_INCLUDE;

        return SKIP_BODY;
    }
}