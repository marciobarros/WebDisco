package br.unirio.simplemvc.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.authentication.ServletAuthenticationProvider;
import br.unirio.simplemvc.actions.data.ServletDataConnector;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.actions.qualifiers.Export;
import br.unirio.simplemvc.actions.response.ServletResponseConnector;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ResultType;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.utils.URLUTF8Encoder;

@SuppressWarnings({ "unchecked", "serial" })
public class RouterServlet extends HttpServlet
{
	private static final String ENCODING = "UTF-8";
	
	/**
	 * Parâmetros do servlet
	 */
	private static final String ACTIONS_PACKAGE_PARAMETER = "actionsPackage";
	private static final String ACTIONS_LOGLEVEL_PARAMETER = "logLevel";
	private static final String ACTIONS_APPNAME_PARAMETER = "applicationName";

	/**
	 * Extensão utilizada nas ações
	 */
	private static final String ACTIONS_EXTENSION = ".do";

	/**
	 * Lista de pacotes onde podem ser encontradas ações
	 */
	private List<String> actionPackages;

	/**
	 * Nível de log desejado para a aplicação
	 */
	private LogLevel logLevel;
	
	/**
	 * Nome da aplicação, para localizar ações
	 */
	private String applicationName;

	/**
	 * Inicializa o servlet
	 */
	public RouterServlet()
	{
		this.actionPackages = new ArrayList<String>();
		this.logLevel = LogLevel.None;
		this.applicationName = "";
	}

	/**
	 * Captura os parâmetros do servlet
	 */
	@Override
	public void init() throws ServletException
	{
		String actionPaths = getInitParameter(ACTIONS_PACKAGE_PARAMETER);

		if (actionPaths != null)
			for (String path : actionPaths.split(","))
				actionPackages.add(path.trim());

		String logLevel = getInitParameter(ACTIONS_LOGLEVEL_PARAMETER);

		if (logLevel != null)
			this.logLevel = convertLogLevel(logLevel);

		String appName = getInitParameter(ACTIONS_APPNAME_PARAMETER);

		if (appName != null)
			this.applicationName = appName.trim();
	}

	/**
	 * Converte uma string com o nível de log para a representação interna
	 */
	private LogLevel convertLogLevel(String level) throws ServletException
	{
		if (level.compareToIgnoreCase("detail") == 0)
			return LogLevel.Detailed;

		if (level.compareToIgnoreCase("basic") == 0)
			return LogLevel.Basic;

		if (level.compareToIgnoreCase("none") == 0)
			return LogLevel.None;

		throw new ServletException("Unrecognized log level - " + level);
	}

	/**
	 * Logs a basic message
	 */
	private void logBasic(String message)
	{
		if (logLevel == LogLevel.Basic || logLevel == LogLevel.Detailed)
			System.out.println(message);
	}

	/**
	 * Retorna o nome da classe relacionada a URL de requisição do servlet,
	 * usando o método que identifica apenas o nome da classe
	 */
	private ActionMapping getActionByClassName(HttpServletRequest request)
	{
		String url = request.getRequestURL().toString();

		// Remove os parâmetros da URL
		int position = url.indexOf("?");

		if (position != -1)
			url = url.substring(0, position);

		// Remove a extensão da ação
		if (url.endsWith(ACTIONS_EXTENSION))
			url = url.substring(0, url.length() - 3);

		// Remove o protocolo e a barra dupla
		int doubleBarPosition = url.indexOf("//");

		if (doubleBarPosition != -1)
			url = url.substring(doubleBarPosition + 2);

		// Remove o nome do site
		int barPosition = url.indexOf("/");

		if (barPosition != -1)
			url = url.substring(barPosition + 1);
		
		// Verifica se deve remover o nome da aplicação
		if (url.startsWith(applicationName + "/"))
			url = url.substring(applicationName.length() + 1);

		// Troca os diretórios por índices de pacote
		String className = url.replaceAll("/", ".");

		// Insere "Action" antes do nome da ação
		int dotPosition = className.lastIndexOf('.');

		if (dotPosition != -1)
			className = className.substring(0, dotPosition + 1).toLowerCase() + "Action" + className.substring(dotPosition + 1, dotPosition + 2).toUpperCase() + className.substring(dotPosition + 2);
		else
			className = "Action" + className.substring(0, 1).toUpperCase() + className.substring(1);

		return new ActionMapping(className, "execute");
	}

	/**
	 * Retorna o nome da classe relacionada a URL de requisição do servlet,
	 * usando o método que identifica o nome da classe e do método
	 */
	private ActionMapping getActionByClassNameAndMethod(HttpServletRequest request)
	{
		String url = request.getRequestURL().toString();

		// Remove os parâmetros da URL
		int position = url.indexOf("?");

		if (position != -1)
			url = url.substring(0, position);

		// Remove a extensão da ação
		if (url.endsWith(ACTIONS_EXTENSION))
			url = url.substring(0, url.length() - 3);

		// Remove o protocolo e a barra dupla
		int doubleBarPosition = url.indexOf("//");

		if (doubleBarPosition != -1)
			url = url.substring(doubleBarPosition + 2);

		// Remove o nome do site
		int barPosition = url.indexOf("/");

		if (barPosition != -1)
			url = url.substring(barPosition + 1);
		
		// Verifica se deve remover o nome da aplicação
		if (url.startsWith(applicationName + "/"))
			url = url.substring(applicationName.length() + 1);

		// Troca os diretórios por índices de pacote
		String className = url.replaceAll("/", ".");

		// Insere "Action" antes do nome da ação
		int dotPosition = className.lastIndexOf('.');

		if (dotPosition == -1)
		{
			className = "Action" + className.substring(0, 1).toUpperCase() + className.substring(1);
			return new ActionMapping(className, "execute");
		}

		String methodName = className.substring(dotPosition + 1, dotPosition + 2).toLowerCase() + className.substring(dotPosition + 2);
		className = className.substring(0, dotPosition);//.toLowerCase();

		// Insere "Action" antes do nome da ação
		dotPosition = className.lastIndexOf('.');

		if (dotPosition != -1)
			className = className.substring(0, dotPosition + 1).toLowerCase() + "Action" + className.substring(dotPosition + 1, dotPosition + 2).toUpperCase() + className.substring(dotPosition + 2);
		else
			className = "Action" + className.substring(0, 1).toUpperCase() + className.substring(1);

		return new ActionMapping(className, methodName);
	}
	
	/**
	 * Adiciona os parâmetros de uma ação em uma URL
	 * 
	 * @param url URL que será configurada com os parâmetros
	 * @param action Ação que contém os parâmetros
	 */
	private String getParameterizedURL(String url, Action action)
	{
		if (!action.hasRedirectParameters())
			return url;

		char separator = (!url.contains("?")) ? '?' : '&';

		for (String name : action.getRedirectParameterNames())
		{
			url += separator + URLUTF8Encoder.encode(name) + '=' + URLUTF8Encoder.encode(action.getRedirectParameter(name));
			separator = '&';
		}

		return url;
	}

	/**
	 * Conclui a execução de uma ação enviando a requisição para outra página ou ação
	 * 
	 * @param url URL para onde deve ser enviada a requisição
	 * @param action Instância da ação
	 * @param context Contexto do servlet onde a ação está sendo executada
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean actionForward(String url, Action action, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (action.hasErrors())
			request.setAttribute(Action.ERRORS_KEY, action.getErrors());

		if (action.hasNotices())
			request.setAttribute(Action.NOTICES_KEY, action.getNotices());

		url = getParameterizedURL(url, action);
		RequestDispatcher rd = context.getRequestDispatcher(url);
		rd.forward(request, response);
		return true;
	}

	/**
	 * Conclui a execução de uma ação criando uma nova requisição a partir de
	 * uma página ou ação
	 * 
	 * @param url URL para onde deve ser enviada a requisição
	 * @param action Instância da ação
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean actionRedirect(String url, Action action, HttpServletResponse response) throws ServletException, IOException
	{
		url = getParameterizedURL(url, action);
		response.sendRedirect(url);
		return true;
	}

	/**
	 * Processa um resultado indicativo de erro em uma ação
	 * 
	 * @param action Instância da ação
	 * @param method Método que representa a ação
	 * @param context Contexto do servlet onde a ação está sendo executada
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean processErrorAnnotation(Action action, Method method, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Error annotation = (Error) method.getAnnotation(Error.class);

		if (annotation == null)
			return false;

		String url = annotation.value();
		ResultType type = annotation.type();
		
		if (url.compareToIgnoreCase(Action.ACTION_DEPENDENT) == 0)
		{
			url = action.getActionDependentURL();
			type = action.getActionDependentType();
		}
		
		if (type == ResultType.Forward)
			return actionForward(url, action, context, request, response);
		else
			return actionRedirect(url, action, response);
	}

	/**
	 * Processa um resultado indicativo de sucesso em uma ação
	 * 
	 * @param action Instância da ação
	 * @param method Método que representa a ação
	 * @param context Contexto do servlet onde a ação está sendo executada
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean processSuccessAnnotation(Action action, Method method, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Success annotation = (Success) method.getAnnotation(Success.class);

		if (annotation == null)
			return false;

		String url = annotation.value();
		ResultType type = annotation.type();
		
		if (url.compareToIgnoreCase(Action.ACTION_DEPENDENT) == 0)
		{
			url = action.getActionDependentURL();
			type = action.getActionDependentType();
		}
		
		if (type == ResultType.Forward)
			return actionForward(url, action, context, request, response);
		else
			return actionRedirect(url, action, response);
	}

	/**
	 * Processa um resultado independente de sucesso ou erro em uma ação
	 * 
	 * @param action Instância da ação
	 * @param method Método que representa a ação
	 * @param context Contexto do servlet onde a ação está sendo executada
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean processAnyAnnotation(Action action, Method method, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Any annotation = (Any) method.getAnnotation(Any.class);

		if (annotation == null)
			return false;

		String url = annotation.value();
		ResultType type = annotation.type();
		
		if (url.compareToIgnoreCase(Action.ACTION_DEPENDENT) == 0)
		{
			url = action.getActionDependentURL();
			type = action.getActionDependentType();
		}
		
		if (type == ResultType.Forward)
			return actionForward(url, action, context, request, response);
		else
			return actionRedirect(url, action, response);
	}

	/**
	 * Executa o método que representa a ação
	 * 
	 * @param action Instância da ação
	 * @param method Método que representa a ação
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean executeActionMethod(Action action, String method, HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		ServletContext context = getServletContext();
		action.setRequestURL(request.getRequestURL().toString());
		action.setDataConnector(new ServletDataConnector(request, response));
		action.setAuthenticationProvider(new ServletAuthenticationProvider(request, response));
		action.setResponseConnector(new ServletResponseConnector(response));
			
		try
		{
			String result;
			Method actionMethod = action.getClass().getMethod(method, new Class[0]);

			try
			{
				Export exportAnnotation = (Export) actionMethod.getAnnotation(Export.class);

				if (exportAnnotation != null)
				{
					response.setContentType("text/csv");
					response.setHeader("Content-Disposition", "attachment;filename=\"export.csv\"");
					response.getWriter().write("\uFEFF");	// UTF-8 BOM marker
				}

				DisableUserVerification annotation = (DisableUserVerification) actionMethod.getAnnotation(DisableUserVerification.class);

				if (annotation == null)
				{
					IUserService service = AuthenticationService.getUserService(request);
					IUser user = AuthenticationService.getUser(request);
					
					if (user == null)
						return actionForward(service.getLoginAction(), action, context, request, response);
					
					if (user.mustChangePassword())
						return actionForward(service.getChangePasswordAction(), action, context, request, response);
					
					if (!user.isActive())
					{
						action.addError("O usuário selecionado não está ativo no sistema");
						return actionForward(service.getLoginAction(), action, context, request, response);
					}
				}

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
			}
			
			if (processAnyAnnotation(action, actionMethod, context, request, response))
				return true;

			if (result.compareToIgnoreCase(Action.NONE) == 0)
				return true;

			if (result.compareToIgnoreCase(Action.SUCCESS) == 0)
				return processSuccessAnnotation(action, actionMethod, context, request, response);

			if (result.compareToIgnoreCase(Action.ERROR) == 0)
				return processErrorAnnotation(action, actionMethod, context, request, response);

		} catch (Exception e)
		{
			throw new ServletException(e.getMessage(), e);
		}

		return false;
	}

	/**
	 * Instancia e executa a classe que representa a ação
	 * 
	 * @param mapping Mapeamento da ação em uma classe e método
	 * @param request Requisição recebida pelo servlet
	 * @param response Resposta gerada pelo servlet
	 */
	private boolean executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		for (String actionPath : actionPackages)
		{
			try
			{
				Class<Action> clazz = (Class<Action>) Class.forName(actionPath + "." + mapping.getClassName());
				Action action = clazz.newInstance();
				return executeActionMethod(action, mapping.getMethodName(), request, response);

			} catch (ClassNotFoundException e)
			{
				logBasic ("Action class not found: " + actionPath + "." + mapping.getClassName() + " ...");
			} catch (InstantiationException e)
			{
				throw new ServletException("Instantiation error: " + e.getMessage(), e);
			} catch (IllegalAccessException e)
			{
				throw new ServletException("Illegal access error: " + e.getMessage(), e);
			}
		}		
		
		return false;
	}

	/**
	 * Instancia e retorna um objeto de ação
	 */
	private void findAction(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		if (actionPackages.size() == 0)
			throw new ServletException("No action package was indicated during servlet initialization");

		ActionMapping context = getActionByClassNameAndMethod(request);
		
		if (executeAction(context, request, response))
			return;

		context = getActionByClassName(request);
		
		if (executeAction(context, request, response))
			return;

		throw new ServletException("Action class not found: " + request.getRequestURL().toString());
	}

	/***
	 * Processa o servlet via POST
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");
		response.setContentType("text/html; charset=" + ENCODING);
		findAction(request, response);
	}

	/***
	 * Processa o servlet via GET
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate, no-store");
		response.setContentType("text/html; charset=" + ENCODING);
		findAction(request, response);
	}
}

/**
 * Nível em que os logs da aplicação são registrados e apresentados
 */
enum LogLevel
{
	Basic, Detailed, None
}

/**
 * Classe que representa um par de classe e método que descrevem uma ação
 */
class ActionMapping
{
	private String className;

	private String methodName;

	public ActionMapping(String clazz, String methodName)
	{
		this.className = clazz;
		this.methodName = methodName;
	}

	public String getClassName()
	{
		return className;
	}

	public String getMethodName()
	{
		return methodName;
	}
}