package br.unirio.simplemvc.actions;

/**
 * Classe que representa um erro ocorrido durante a execução de uma ação
 * 
 * @author Marcio Barros
 */
public class ActionError 
{
	/**
	 * Identificador de erros globais, sem campo associado
	 */
	public final static String GLOBAL = "";

	/**
	 * Campo associado ao erro
	 */
	private String field;
	
	/**
	 * Mensagem associada ao erro
	 */
	private String message;
	
	/**
	 * Inicializa um erro gerado durante uma ação e associado a um campo
	 * 
	 * @param field		Campo associado ao erro
	 * @param message	Mensagem associada ao erro
	 */
	public ActionError(String field, String message)
	{
		this.field = field;
		this.message = message;
	}
	
	/**
	 * Inicializa um erro global gerado durante uma ação
	 * 
	 * @param message	Mensagem associada ao erro
	 */
	public ActionError(String message)
	{
		this.field = GLOBAL;
		this.message = message;
	}
	
	/**
	 * Retorna o campo associado ao erro
	 */
	public String getField()
	{
		return field;
	}

	/**
	 * Retorna a mensagem associada ao erro
	 */
	public String getMessage()
	{
		return message;
	}
}