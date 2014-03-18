package br.unirio.simplemvc.tags.user;

import javax.servlet.http.HttpServletResponse;
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
public class RedirectUserLevelTag extends TagSupport
{
	/**
	 * Página para onde o usuário será redirecionado
	 */
    private String page = "";

	/**
	 * Nível de usuário desejado
	 */
    private String level = "";

    /**
     * Altera a página para onde o usuário será redirecionado
     */
    public void setPage (String page)
    {
        this.page = page;
    }

    /**
     * Altera o nível de usuário desejado
     */
    public void setLevel (String level)
    {
        this.level = level;
    }

    /**
     * Redireciona o usuário quando não estiver logado 
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