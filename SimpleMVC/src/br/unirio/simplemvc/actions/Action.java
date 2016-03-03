package br.unirio.simplemvc.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.unirio.simplemvc.actions.authentication.IAuthenticationProvider;
import br.unirio.simplemvc.actions.data.IDataConnector;
import br.unirio.simplemvc.actions.response.IResponseConnector;
import br.unirio.simplemvc.actions.results.ResultType;
import br.unirio.simplemvc.json.JSONArray;
import br.unirio.simplemvc.json.JSONObject;
import br.unirio.simplemvc.servlets.IUser;
import br.unirio.simplemvc.utils.Conversion;
import br.unirio.simplemvc.utils.StringUtils;
import br.unirio.simplemvc.utils.Validadores;

/**
 * Classe que representa uma ação abstrata
 * 
 * @author Marcio Barros
 */
public class Action
{
	/**
	 * Chave de acesso para a lista de notificações na requisição
	 */
	public static final String NOTICES_KEY = "notices";

	/**
	 * Chave de acesso para a lista de erros na requisição
	 */
	public static final String ERRORS_KEY = "errors";

	/**
	 * Tipo de URL para continuidade da ação
	 */
	public static final String ACTION_DEPENDENT = "ACTION_DEPENDENT";
	
	/**
	 * Constante que identifica o resultado de conclusão bem sucedida da ação
	 */
	public static final String SUCCESS = "SUCCESS";

	/**
	 * Constante que identifica o resultado de conclusão mau sucedida da ação
	 */
	public static final String ERROR = "ERROR";

	/**
	 * Constante que identifica o resultado de conclusão bem sucedida sem redirecionamento
	 */
	public static final String NONE = "";

	/**
	 * Parâmetros que serão adicionado ao retorno gerado pela ação
	 */
	private HashMap<String, String> parameters;

	/**
	 * Lista de erros registrados durante a ação
	 */
	private List<ActionError> errors;

	/**
	 * Lista de avisos registrados durante a ação
	 */
	private List<String> notices;
	
	/**
	 * Requisição que disparou a ação
	 */
	private String requestURL;

	/**
	 * Provedor de dados para a ação
	 */
	private IDataConnector dataConnector;
	
	/**
	 * Provedor de autenticação da ação
	 */
	private IAuthenticationProvider authenticationProvider;

	/**
	 * Conector de resposta para ações AJAX
	 */
	private IResponseConnector responseConnector;
	
	/**
	 * URL de destino da ação para resultados dependentes de ação 
	 */
	private String actionDependentURL;
	
	/**
	 * Tipo de transição da ação para resultados dependentes de ação 
	 */
	private ResultType actionDependentType;
	
	/**
	 * Linha que será exportada
	 */
	private StringBuffer currentExportLine;

	/**
	 * Inicializa a ação
	 */
	public Action()
	{
		this.parameters = new HashMap<String, String>();
		this.errors = new ArrayList<ActionError>();
		this.notices = new ArrayList<String>();
		this.actionDependentURL = "";
		this.actionDependentType = ResultType.Forward;
		this.currentExportLine = new StringBuffer();
	}
	
	/**
	 * Programa o canal de dados da requisição pela ação
	 */
	public Action setDataConnector(IDataConnector provider)
	{
		this.dataConnector = provider;
		return this;
	}
	
	/**
	 * Programa o canal de autenticação de usuários da ação
	 */
	public Action setAuthenticationProvider(IAuthenticationProvider provider)
	{
		this.authenticationProvider = provider;
		return this;
	}
	
	/**
	 * Programa o canal de resposta da ação
	 */
	public Action setResponseConnector(IResponseConnector connector)
	{
		this.responseConnector = connector;
		return this;
	}
	
	/**
	 * Indica a URL que disparou a ação
	 */
	public Action setRequestURL(String url)
	{
		this.requestURL = url;
		return this;
	}
	
	/**
	 * Retorna a URL que disparou a ação
	 */
	public String getRequestURL()
	{
		return requestURL;
	}
	
	/**
	 * Verifica se a ação registrou algum erro
	 */
	public boolean hasErrors()
	{
		return !errors.isEmpty();
	}
	
	/**
	 * Retorna a lista de erros registrados na ação
	 */
	public Iterable<ActionError> getErrors()
	{
		return errors;
	}
	
	/**
	 * Retorna a primeira mensagem de erro gerada pela ação
	 */
	public String getFirstErrorMessage()
	{
		if (errors.size() > 0)
			return errors.get(0).getMessage();
		
		return null;
	}
	
	/**
	 * Adiciona um erro gerado durante a ação e associado a um campo
	 * 
	 * @param field Campo associado ao erro
	 * @param message Mensagem associada ao erro
	 */
	public String addError(String field, String message)
	{
		errors.add(new ActionError(field, message));
		return ERROR;
	}

	/**
	 * Adiciona um erro global gerado durante a ação
	 * 
	 * @param message Mensagem associada ao erro
	 */
	public String addError(String message)
	{
		errors.add(new ActionError(message));
		return ERROR;
	}
	
	/**
	 * Verifica se a ação registrou algum aviso
	 */
	public boolean hasNotices()
	{
		return !notices.isEmpty();
	}
	
	/**
	 * Retorna a lista de avisos registrados na ação
	 */
	public Iterable<String> getNotices()
	{
		return notices;
	}

	/**
	 * Adiciona uma mensagem de aviso gerada durante a ação
	 * 
	 * @param message Mensagem de aviso
	 * @return
	 */
	public String addNotice(String message)
	{
		notices.add(message);
		return SUCCESS;
	}

	/**
	 * Adiciona uma mensagem de aviso gerada durante a ação para redirecionamentos
	 * 
	 * @param message Mensagem de aviso
	 * @return
	 */
	public String addRedirectNotice(String message)
	{
		addParameter(NOTICES_KEY,  message);
		return SUCCESS;
	}

	/**
	 * Retorna o valor de um cookie
	 */
	public String getCookie(String name)
	{
		return dataConnector.getCookie(name);
	}
	
	/**
	 * Retorna o valor de um cookie, considerando um valor default
	 */
	public String getCookie(String name, String def)
	{
		String result = getCookie(name);
		return (result != null) ? result : def;
	}
	
	/**
	 * Adiciona um cookie na resposta da ação
	 */
	public void addCookie(String name, String value)
	{
		dataConnector.setCookie(name, value);
	}

	/**
	 * Retorna o valor de um atributo da requisição
	 */
	public Object getAttribute(String name)
	{
		return dataConnector.getAttribute(name);
	}

	/**
	 * Muda o valor de um atributo da requisição
	 */
	public void setAttribute(String name, Object value)
	{
		dataConnector.setAttribute(name, value);
	}
	
	/**
	 * Verifica se a ação possui parâmetros
	 */
	public boolean hasRedirectParameters()
	{
		return !parameters.isEmpty();
	}
	
	/**
	 * Retorna os nomes dos parâmetros registrados na ação
	 */
	public Iterable<String> getRedirectParameterNames()
	{
		return parameters.keySet();
	}
	
	/**
	 * Retorna o valor de um parâmetro de redirect, dado seu nome
	 */
	public String getRedirectParameter(String name)
	{
		return parameters.get(name);
	}

	/**
	 * Retorna o valor de um parâmetro
	 * 
	 * @param name Nome do parâmetro desejado
	 */
	public String getParameter(String name)
	{
		String parameter = dataConnector.getParameter(name);
		
		if (parameter != null)
			return parameter.trim();
		
		return null;
	}

	/**
	 * Retorna o valor de um parâmetro
	 * 
	 * @param name Nome do parâmetro desejado
	 */
	public String getParameter(String name, String def)
	{
		String parameter = getParameter(name);
		return (parameter != null) ? parameter : def;
	}

	/**
	 * Retorna o valor de um parâmetro inteiro
	 */
	public boolean getBooleanParameter(String name, boolean def)
	{
		String parameter = getParameter(name);
		return (parameter != null) ? Conversion.safeParseBoolean(parameter) : def;
	}

	/**
	 * Retorna o valor de um parâmetro inteiro
	 */
	public int getIntParameter(String name, int def)
	{
		return Conversion.safeParseInteger(getParameter(name), def);
	}

	/**
	 * Retorna o valor de um parâmetro longo
	 */
	public long getLongParameter(String name, long def)
	{
		return Conversion.safeParseLong(getParameter(name), def);
	}

	/**
	 * Retorna o valor de um parâmetro double
	 */
	public double getDoubleParameter(String name, double def)
	{
		return Conversion.safeParseDouble(getParameter(name), def);
	}

	/**
	 * Retorna o valor de um parâmetro do tipo data/tempo
	 */
	protected DateTime getDateTimeParameter(String nome)
	{
		String sData = getParameter(nome);
		
		if (sData == null)
			return null;
		
		return Validadores.validaData(sData);
	}

	/**
	 * Retorna o valor de um parâmetro do tipo data/tempo
	 */
	protected DateTime getDateTimeParameter(String nome, DateTime dataDefault)
	{
		String sData = getParameter(nome);
		
		if (sData == null)
			return dataDefault;
		
		DateTime data = Validadores.validaData(sData);
		return (data != null) ? data : dataDefault; 
	}
	
	/**
	 * Adiciona um parâmetro do tipo string no retorno da ação
	 * 
	 * @param name Nome do parâmetro
	 * @param value Valor do parâmetro
	 */
	protected void addParameter(String name, String value)
	{
		parameters.put(name, value);
	}

	/**
	 * Adiciona um parâmetro do tipo data no retorno da ação
	 * 
	 * @param name Nome do parâmetro
	 * @param value Valor do parâmetro
	 */
	protected void addParameter(String name, DateTime value)
	{
		DateTimeFormatter sdf = DateTimeFormat.forPattern("yyyy-MM-dd");
		addParameter(name, sdf.print(value));
	}

	/**
	 * Adiciona um parâmetro do tipo inteiro no retorno da ação
	 * 
	 * @param name Nome do parâmetro
	 * @param value Valor do parâmetro
	 */
	protected void addParameter(String name, int value)
	{
		addParameter(name, Integer.toString(value));
	}

	/**
	 * Adiciona um parâmetro do tipo inteiro longo no retorno da ação
	 * 
	 * @param name Nome do parâmetro
	 * @param value Valor do parâmetro
	 */
	protected void addParameter(String name, long value)
	{
		addParameter(name, Long.toString(value));
	}

	/**
	 * Adiciona um parâmetro numérico no retorno da ação
	 * 
	 * @param name Nome do parâmetro
	 * @param value Valor do parâmetro
	 */
	protected void addParameter(String name, double value)
	{
		addParameter(name, Double.toString(value));
	}

	/**
	 * Retorna a URL de destino da ação em caso de resultados dependentes de ação
	 */
	public String getActionDependentURL()
	{
		return this.actionDependentURL;
	}

	/**
	 * Retorna o tipo de transição da ação em caso de resultados dependentes de ação
	 */
	public ResultType getActionDependentType()
	{
		return this.actionDependentType;
	}

	/**
	 * Altera a URL de destino da ação em caso de resultados dependentes de ação - forwards
	 */
	protected String setActionDependentURL(String url)
	{
		this.actionDependentURL = url;
		this.actionDependentType = ResultType.Forward;
		return SUCCESS;
	}

	/**
	 * Altera a URL de destino da ação em caso de resultados dependentes de ação
	 */
	protected String setActionDependentURL(ResultType type, String url)
	{
		this.actionDependentURL = url;
		this.actionDependentType = type;
		return SUCCESS;
	}

	/**
	 * Returna o iterador para acesso a arquivos enviados no formulário
	 */
	public Object getFileIterator()
	{
		return this.dataConnector.getFileIterator();
	}

	/**
	 * Registra o login de um usuário no sistema
	 */
	protected void setCurrentUser(IUser participante)
	{
		if (authenticationProvider != null)
			authenticationProvider.setCurrentUser(participante);
	}

	/**
	 * Faz logout com o usuário atual
	 */
	protected void invalidateCurrentUser()
	{
		if (authenticationProvider != null)
			authenticationProvider.invalidadeCurrentUser();
	}
	
	/**
	 * Verifica se o usuário está logado para executar a ação
	 */
	protected IUser checkLogged() throws ActionException
	{
		IUser usuario = testLogged();
		
		if (usuario == null)
			throw new ActionException("O participante deve estar logado para executar esta ação.");
		
		return usuario;
	}

	/**
	 * Verifica se o usuário está logado para executar a ação
	 */
	protected IUser testLogged()
	{
		if (authenticationProvider == null)
			return null;
		
		IUser usuario = authenticationProvider.getCurrentUser();
		
		if (usuario != null)
			return usuario;
		
		return null;
	}

	/**
	 * Retorna o objeto que representa o canal de saída de dados
	 */
	protected IResponseConnector getResponse()
	{
		return responseConnector;
	}

	/**
	 * Verifica se o usuário tem permissão de acesso para executar a ação
	 */
	protected void checkUserLevel(String level) throws ActionException
	{
		check(authenticationProvider != null, "O usuário deve estar logado para executar esta ação");
		IUser user = authenticationProvider.getCurrentUser();
		check(user != null, "O usuário deve estar logado para executar esta ação");
		check(user.checkLevel(level), "O usuário deve ser '" + level + "' para executar esta ação");
	}

	/**
	 * Verifica uma regra de negócio, gerando exceção em caso de falha
	 */
	protected void check(boolean bool, String message) throws ActionException
	{
		if (!bool)
			throw new ActionException(message);
	}

	/**
	 * Verifica uma regra de negócio, gerando exceção em caso de falha
	 */
	protected void check(boolean bool, String field, String message) throws ActionException
	{
		if (!bool)
			throw new ActionException(field, message);
	}

	/**
	 * Verifica uma regra de negócio, gerando exceção em caso de falha
	 */
	protected void checkNonEmpty(String valor, String message) throws ActionException
	{
		check(valor != null && valor.length() > 0, message);
	}

	/**
	 * Verifica o tamanho máximo de uma string, gerando exceção em caso de falha
	 */
	protected void checkLength(String valor, int tamanho, String sujeito) throws ActionException
	{
		check(valor.length() <= tamanho, sujeito + " não pode ter mais do que " + tamanho + " caracteres");
	}

	/**
	 * Verifica uma regra de negócio, gerando exceção em caso de falha
	 */
	protected void checkNonEmpty(String valor, String campo, String message) throws ActionException
	{
		check(valor != null && valor.length() > 0, campo, message);
	}
	
	/**
	 * Verifica se um e-mail atende ao formato padronizado
	 */
	protected void checkEmail(String valor, String message) throws ActionException
	{
		String EMAIL_PATTERN = "^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(valor.toUpperCase());
		check(matcher.matches(), message);
	}
	
	/**
	 * Verifica se um telefone atende ao formato padronizado
	 */
	protected void checkPhone(String valor, String message) throws ActionException
	{
		String PHONE_PATTERN = "^(\\([1-9][1-9]\\)\\s)?[2-9][0-9]{3,4}-?[0-9]{4}$"; 
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		Matcher matcher = pattern.matcher(valor.toUpperCase());
		check(matcher.matches(), message);
	}

	/**
	 * Gera um resultado na saída de uma ação
	 */
	protected void write(String s) throws ActionException
	{
		try
		{
			responseConnector.write(s);
		}
		catch(IOException e)
		{
			throw new ActionException("I/O Error: " + e.getMessage());
		}
	}

	/**
	 * Escreve um header separado por ponto-e-vírgula na saída da ação
	 */
	protected void writeExportHeader(String header) throws ActionException
	{
		String[] tokens = header.split(";");
		
		for(String token : tokens)
			writeExport(token);
		
		nextLineExport();
	}
	
	/**
	 * Gera uma string para exportação na saída da ação
	 */
	protected void writeExport(String s) throws ActionException
	{
		String resultado = "\"" + s.replace("\"", "\\\"") + "\"";

		if (this.currentExportLine.length() > 0)
			resultado = ";" + resultado;
		
		this.currentExportLine.append(resultado);
	}

	/**
	 * Gera um inteiro para exportação na saída da ação
	 */
	protected void writeExport(int value) throws ActionException
	{
		writeExport(Integer.toString(value));
	}

	/**
	 * Gera um número real para exportação na saída da ação
	 */
	protected void writeExport(double value, int decimals) throws ActionException
	{
		writeExport(StringUtils.createNumberFormat(decimals).format(value));
	}

	/**
	 * Gera uma data para exportação na saída da ação
	 */
	protected void write(DateTime data) throws ActionException
	{
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/yyyy");
		writeExport(sdf.print(data));
	}
	
	/**
	 * Salta uma linha no arquivo de exportação
	 */
	protected void nextLineExport() throws ActionException
	{
		write(this.currentExportLine.toString()); 
		this.currentExportLine.setLength(0);
	}

	/**
	 * Gera um resultado no log do sistema
	 */
	protected void log(String s)
	{
		System.out.println(s);
	}
	
	/**
	 * Adiciona um item de texto no início de um vetor de strings
	 */
	private String[] addString(String[] valores, String novaOpcao)
	{
		String[] resultado = new String[valores.length + 1];
		resultado[0] = novaOpcao;
		
		for (int i = 0; i < valores.length; i++)
			resultado[i+1] = valores[i];
		
		return resultado;
	}
	
	/**
	 * Adiciona um item "Todos" no início de um vetor de strings
	 */
	protected String[] addAll(String[] valores)
	{
		return addString(valores, "Todos");
	}
	
	/**
	 * Adiciona um item "Todos" no início de um vetor de strings
	 */
	protected String[] addAll(String[] valores, String nome)
	{
		return addString(valores, nome);
	}
	
	/**
	 * Adiciona um item "Nenhum" no início de um vetor de strings
	 */
	protected String[] addNone(String[] valores)
	{
		return addString(valores, "Nenhum");
	}
	
	/**
	 * Adiciona um item "Selecione" no início de um vetor de strings
	 */
	protected String[] addSelect(String[] valores, String title)
	{
		return addString(valores, title);
	}
	
	/**
	 * Adiciona um item "Nenhum" no início de um vetor de strings
	 */
	protected String[] addNone(String[] valores, String nome)
	{
		return addString(valores, nome);
	}
	
	/**
	 * Adiciona um item em branco no início de um vetor de strings
	 */
	protected String[] addBlank(String[] valores)
	{
		return addString(valores, "");
	}

	/**
	 * Gera uma resposta de sucesso AJAX, embutindo um objeto em um marcador de sucesso
	 */
	public String ajaxSuccess(JSONObject resultado) throws ActionException
	{
		JSONObject root = new JSONObject()
			.add("Result", "OK");
		
		if (resultado != null)
			root.add("data", resultado);

		write(root.toString());
		return NONE;
	}
	
	/**
	 * Gera uma resposta AJAX, embutindo um array em um marcador de sucesso
	 */
	public String ajaxSuccess(JSONArray resultado) throws ActionException
	{
		JSONObject root = new JSONObject()
			.add("Result", "OK");
		
		if (resultado != null)
			root.add("data", resultado);

		write(root.toString());
		return NONE;
	}

	/**
	 * Gera uma resposta de sucesso AJAX sem resultado embutido
	 */
	public String ajaxSuccess() throws ActionException
	{
		JSONObject root = new JSONObject()
			.add("Result", "OK");
			
		write(root.toString());
		return NONE;
	}
	
	/**
	 * Gera uma resposta AJAX, embutindo uma mensagem em um marcador de erro
	 */
	public String ajaxError(String mensagem) throws ActionException
	{
		JSONObject root = new JSONObject().
			add("Result", "FAIL");
		
		if (mensagem.length() > 0)
		{
			root.add("message", mensagem);
			root.add("Message", mensagem);
		}

		write(root.toString());
		return NONE;
	}
	
	/**
	 * Gera uma resposta de erro AJAX sem mensagem embutida
	 */
	public String ajaxError() throws ActionException
	{
		return ajaxError("");
	}
		
	/**
	 * Gera uma resposta AJAX, sem embutir a resposta em marcadores de sucesso ou erro
	 */
	public String ajaxUnwrapped(JSONObject resultado) throws ActionException
	{
		write(resultado.toString());
		return NONE;
	}
	
	/**
	 * Gera uma resposta AJAX, sem embutir a resposta em marcadores de sucesso ou erro
	 */
	public String ajaxUnwrapped(JSONArray resultado) throws ActionException
	{
		write(resultado.toString());
		return NONE;
	}
	
}