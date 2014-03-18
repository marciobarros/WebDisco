package br.unirio.simplemvc.tags.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Analisa o conteúdo da tag somente se não houver usuário logado
 *
 * @author Márcio Barros
 */
@SuppressWarnings("serial")
public class CheckUnloggedTag extends TagSupport
{
    public int doStartTag() throws JspException
    {
		IUser user = AuthenticationService.getUser(pageContext.getRequest());
        return (user == null) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }
}