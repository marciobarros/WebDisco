package br.unirio.simplemvc.tags.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Tag para redirecionamento de usuários não logados
 *
 * @author Márcio Barros
 */
@SuppressWarnings("serial")
public class RedirectUnloggedTag extends TagSupport
{
	/**
	 * Página para onde o usuário será redirecionado
	 */
    private String page = "";

    /**
     * Altera a página para onde o usuário será redirecionado
     */
    public void setPage (String page)
    {
        this.page = page;
    }

    /**
     * Redireciona o usuário quando não estiver logado 
     */
    public int doEndTag() throws JspException
    {
		IUser user = AuthenticationService.getUser(pageContext.getRequest());

		if (user != null)
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