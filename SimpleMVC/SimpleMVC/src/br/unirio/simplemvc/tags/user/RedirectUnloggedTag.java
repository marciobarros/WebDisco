package br.unirio.simplemvc.tags.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Tag para redirecionamento de usu�rios n�o logados
 *
 * @author M�rcio Barros
 */
@SuppressWarnings("serial")
public class RedirectUnloggedTag extends TagSupport
{
	/**
	 * P�gina para onde o usu�rio ser� redirecionado
	 */
    private String page = "";

    /**
     * Altera a p�gina para onde o usu�rio ser� redirecionado
     */
    public void setPage (String page)
    {
        this.page = page;
    }

    /**
     * Redireciona o usu�rio quando n�o estiver logado 
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