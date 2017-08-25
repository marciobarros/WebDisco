/**
 * Analisa o conteudo da tag somente se nao houver usuario logado
 *
 * @author Marcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckUnloggedTag extends TagSupport
{
	private static final long serialVersionUID = 6768386267014402936L;

    public int doStartTag() throws JspException
    {
    	if (!LoggingServices.getInstance().isUserLogged(pageContext))
                return (EVAL_BODY_INCLUDE);

        return (SKIP_BODY);
    }
}