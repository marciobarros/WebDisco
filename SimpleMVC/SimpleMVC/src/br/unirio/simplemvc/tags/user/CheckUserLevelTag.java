package br.unirio.simplemvc.tags.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Analisa o conte�do da tag somente se n�o houver usu�rio logado
 *
 * @author M�rcio Barros
 */
@SuppressWarnings("serial")
public class CheckUserLevelTag extends TagSupport
{
    private final static String ALL = "all";
    private final static String NONE = "none";
    private final static String ANY = "any";

    /**
	 * N�vel de usu�rio desejado
	 */
    private String level = "";

	/**
	 * Indica o tipo de compara��o de n�vel
	 */
    private String type = ALL;

    /**
     * Altera o n�vel de usu�rio desejado
     */
    public void setLevel (String level)
    {
        this.level = level;
    }

    /**
     * Altera o tipo de compara��o de n�vel
     */
    public void setType (String type) throws JspException
    {
    	if (!type.equals(ALL) && !type.equals(NONE) && !type.equals(ANY))
    		throw new JspException("Invalid level comparison type: use all, none or any"); 
    	
        this.type = type;
    }

    /**
     * Verifica se o usu�rio logado possui pelo menos um n�vel listado
     */
	private boolean checkAnyLevel(IUser user)
	{
		String[] levels = level.split(",");
		
		for (String level : levels)
			if (user.checkLevel(level.trim()))
				return true;
		
		return false;
	}

	/**
	 * Verifica se o usu�rio logado n�o possui nenhum n�vel listado
	 */
	private boolean checkNoneLevels(IUser user)
	{
		String[] levels = level.split(",");
		
		for (String level : levels)
			if (user.checkLevel(level.trim()))
				return false;
		
		return true;
	}

	/**
	 * Verifica se o usu�rio logado tem todos os n�veis listados
	 */
	private boolean checkAllLevels(IUser user)
	{
		String[] levels = level.split(",");
		
		for (String level : levels)
			if (!user.checkLevel(level.trim()))
				return false;
		
		return true;
	}
 
    /**
     * Processa a tag
     */
    public int doStartTag() throws JspException
    {
		IUser user = AuthenticationService.getUser(pageContext.getRequest());

		if (user == null)
			return SKIP_BODY;

		if (type.equals(ALL))
			return checkAllLevels(user) ? EVAL_BODY_INCLUDE : SKIP_BODY;
		
		if (type.equals(NONE))
			return checkNoneLevels(user) ? EVAL_BODY_INCLUDE : SKIP_BODY;
		
		if (type.equals(ANY))
			return checkAnyLevel(user) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    	
        return SKIP_BODY;
    }
}