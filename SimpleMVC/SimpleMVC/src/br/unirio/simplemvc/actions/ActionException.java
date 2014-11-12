package br.unirio.simplemvc.actions;

/**
 * Classe que representa uma exceção associada a ação
 * 
 * @author Marcio Barros
 */
public class ActionException extends Exception
{
	private static final long serialVersionUID = -1206927566605170004L;
	
	private String field;

	/**
	 * Inicializa uma exceção gerada por uma ação
	 * 
	 * @param field		Campo associado à exceção
	 * @param message	Mensagem associada à exceção
	 */
	public ActionException(String field, String message)
	{
		super(message);
		this.field = field; 
	}

	/**
	 * Inicializa uma exceção gerada por uma ação
	 * 
	 * @param message	Mensagem associada à exceção
	 */
	public ActionException(String message)
	{
		this(ActionError.GLOBAL, message);
	}
	
	/**
	 * Retorna o campo associado à ação
	 */
	public String getField()
	{
		return field;
	}
}