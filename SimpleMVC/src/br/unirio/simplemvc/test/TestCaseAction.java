package br.unirio.simplemvc.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.TestAuthenticationProvider;
import br.unirio.simplemvc.actions.data.TestDataConnector;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.actions.response.TestResponseConnector;
import br.unirio.simplemvc.actions.smtp.TestMailSender;
import br.unirio.simplemvc.json.MyJSONObject;

/**
 * Class that represents a test case for web actions
 * 
 * @author marcio.barros
 */
public class TestCaseAction extends TestCase
{
	/**
	 * The authentication connector to be used by the action
	 */
	protected TestAuthenticationProvider authConnector;
	
	/**
	 * The data connector through which the action will collect parameter, cookies, and attributes
	 */
	protected TestDataConnector dataConnector;
	
	/**
	 * The mail connector through which messages sent by the action will be stored
	 */
	protected TestMailSender mailConnector;
	
	/**
	 * The connector to collect response data
	 */
	protected TestResponseConnector outConnector;
	
	/**
	 * Clears the context under which the action will be executed
	 */
	@Override
	public void setUp()
	{
		this.authConnector = new TestAuthenticationProvider();
		this.dataConnector = new TestDataConnector();
		this.mailConnector = new TestMailSender();
		this.outConnector  = new TestResponseConnector();
	}
	
	/**
	 * Executes an action, storing eventual error messages
	 */
	protected String execute(Action action, String methodName)
	{
		action.setAuthenticationProvider(authConnector);
		action.setDataConnector(dataConnector);
		action.setResponseConnector(outConnector);
		//GerenciadorEmail.getInstance().setMailSender(mailConnector);
		
		mailConnector.clear();
		outConnector.clear();

		String result;

		try
		{
			Method actionMethod = action.getClass().getMethod(methodName, new Class[0]);

			try
			{
				result = (String) actionMethod.invoke(action, new Object[0]);

			} catch (InvocationTargetException e)
			{
				String field = (e.getTargetException().getClass() == ActionException.class) ? ((ActionException) e.getTargetException()).getField() : "";
				String message = e.getTargetException().getMessage(); 

				if (message == null || message.length() == 0)
					message = "Erro inesperado";

				Ajax ajaxAnnotation = (Ajax) actionMethod.getAnnotation(Ajax.class);

				if (ajaxAnnotation != null)
					result = action.ajaxError(message);
				else
					result = action.addError(field, message);

				/*if (message != null && message.length() > 0)
					result = action.addError(field, e.getTargetException().getMessage());
				else
					result = action.addError(field, "Erro inesperado");*/
			}
			
		} catch (Exception e)
		{
			result = Action.NONE;
		}

		return result;
	}

	/**
	 * Creates large strings for check length testing
	 */
	protected String bigString(int size)
	{
		StringBuilder sb = new StringBuilder();
		
		while (size >= 10)
		{
			sb.append("aaaaaaaaaa");
			size -= 10;
		}
		
		while (size > 0)
		{
			sb.append("a");
			size--;
		}
		
		return sb.toString();
	}
	
	/**
	 * Verifica se ocorreu um determinado erro em uma ação AJAX 
	 */
 	protected void assertAjaxError(String message)
	{
		MyJSONObject json = new MyJSONObject(outConnector.getResult());
		assertEquals("FAIL", json.getString("Result"));
		assertEquals(message, json.getString("message"));
	}
	
	/**
	 * Verifica se ocorreu sucesso em uma ação AJAX 
	 */
 	protected void assertAjaxSuccess()
	{
		MyJSONObject json = new MyJSONObject(outConnector.getResult());
		assertEquals("OK", json.getString("Result"));
	}

	/**
	 * Retorna a primeira mensagem de erro da ação
	 */
	protected String getErrorMessage(Action action)
	{
		if (action.getErrors() == null)
			return "";
		
		return action.getErrors().iterator().next().getMessage();
	}
}