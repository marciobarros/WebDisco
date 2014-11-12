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
public class CheckUserLevelTag extends TagSupport
{
    private final static String ALL = "all";
    private final static String NONE = "none";
    private final static String ANY = "any";

    /**
	 * Nível de usuário desejado
	 */
    private String level = "";

	/**
	 * Indica o tipo de comparação de nível
	 */
    private String type = ALL;

    /**
     * Altera o nível de usuário desejado
     */
    public void setLevel (String level)
    {
        this.level = level;
    }

    /**
     * Altera o tipo de comparação de nível
     */
    public void setType (String type) throws JspException
    {
    	if (!type.equals(ALL) && !type.equals(NONE) && !type.equals(ANY))
    		throw new JspException("Invalid level comparison type: use all, none or any"); 
    	
        this.type = type;
    }

    /**
     * Verifica se o usuário logado possui pelo menos um nível listado
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
	 * Verifica se o usuário logado não possui nenhum nível listado
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
	 * Verifica se o usuário logado tem todos os níveis listados
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