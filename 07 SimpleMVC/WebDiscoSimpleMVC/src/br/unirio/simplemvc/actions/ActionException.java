package br.unirio.simplemvc.actions;

/**
 * Classe que representa uma exce��o associada a a��o
 * 
 * @author Marcio Barros
 */
public class ActionException extends Exception
{
	private static final long serialVersionUID = -1206927566605170004L;
	
	private String field;

	/**
	 * Inicializa uma exce��o gerada por uma a��o
	 * 
	 * @param field		Campo associado � exce��o
	 * @param message	Mensagem associada � exce��o
	 */
	public ActionException(String field, String message)
	{
		super(message);
		this.field = field; 
	}

	/**
	 * Inicializa uma exce��o gerada por uma a��o
	 * 
	 * @param message	Mensagem associada � exce��o
	 */
	public ActionException(String message)
	{
		this(ActionError.GLOBAL, message);
	}
	
	/**
	 * Retorna o campo associado � a��o
	 */
	public String getField()
	{
		return field;
	}
}