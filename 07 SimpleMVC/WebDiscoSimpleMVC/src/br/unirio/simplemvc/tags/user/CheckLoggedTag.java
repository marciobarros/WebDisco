package br.unirio.simplemvc.tags.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Analisa o conte�do da tag somente se houver algum usu�rio logado
 *
 * @author M�rcio Barros
 */
@SuppressWarnings("serial")
public class CheckLoggedTag extends TagSupport
{
	public int doStartTag() throws JspException
    {
		IUser user = AuthenticationService.getUser(pageContext.getRequest());
        return (user != null) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}