package br.unirio.simplemvc.tags.user;

import javax.servlet.http.HttpServletResponse;
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
public class RedirectUserLevelTag extends TagSupport
{
	/**
	 * P�gina para onde o usu�rio ser� redirecionado
	 */
    private String page = "";

	/**
	 * N�vel de usu�rio desejado
	 */
    private String level = "";

    /**
     * Altera a p�gina para onde o usu�rio ser� redirecionado
     */
    public void setPage (String page)
    {
        this.page = page;
    }

    /**
     * Altera o n�vel de usu�rio desejado
     */
    public void setLevel (String level)
    {
        this.level = level;
    }

    /**
     * Redireciona o usu�rio quando n�o estiver logado 
     */
    public int doEndTag() throws JspException
    {
		IUser user = AuthenticationService.getUser(pageContext.getRequest());

		if (user != null && user.checkLevel(level))
    		return (EVAL_PAGE);
    	
        try
        {
            ((HttpServletResponse)pageContext.getResponse()).sendRedirect(page);
        }
        catch (Exception e)
        {
            throw new JspException(e.toString());
        }

        return (SKIP_PAGE);
    }
}