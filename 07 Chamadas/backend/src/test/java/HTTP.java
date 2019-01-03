import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que facilita a execução de requisições HTTP para efeito de teste
 * 
 * @author Marcio
 */
public class HTTP
{
	/**
	 * Requisição GET com token de autenticação
	 */
	public static WebResponse get(String url, String authenticationHeader) throws Exception
	{
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		if (authenticationHeader != null)
			con.setRequestProperty("X-AUTH-TOKEN", authenticationHeader);

		return captureResponse(con);
	}

	/**
	 * Requisição GET sem token de autenticação
	 */
	public static WebResponse get(String url) throws Exception
	{
		return get(url, null);
	}

	/**
	 * Requisição POST com parâmetros e token de autenticação
	 */
	public static WebResponse post(String url, String requestBody, String authenticationHeader) throws Exception
	{
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json");

		if (authenticationHeader != null)
			con.setRequestProperty("X-AUTH-TOKEN", authenticationHeader);

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestBody);
		wr.flush();
		wr.close();

		return captureResponse(con);
	}

	/**
	 * Requisição POST com parâmetros e sem token de autenticação
	 */
	public static WebResponse post(String url, String requestBody) throws Exception
	{
		return post(url, requestBody, null);
	}

	/**
	 * Captura a resposta de uma requisição Web
	 */
	private static WebResponse captureResponse(HttpURLConnection con) throws IOException
	{
		int code = con.getResponseCode();
		WebResponse response = new WebResponse(code);

		Map<String, List<String>> map = con.getHeaderFields();

		for (Map.Entry<String, List<String>> entry : map.entrySet())
			response.set(entry.getKey(), entry.getValue().toString());

		if (code == 200)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.setBody(response.getBody() + inputLine);

			in.close();
		}
		
		return response;
	}
}

/**
 * Classe que representa a resposta a uma requisição Web
 * 
 * @author Marcio
 */
class WebResponse
{
	private @Getter int code;
	private @Getter @Setter String body;
	private HashMap<String, String> headers;

	/**
	 * Inicializa uma resposta HTTP
	 */
	public WebResponse(int code)
	{
		this.code = code;
		this.body = "";
		this.headers = new HashMap<String, String>();
	}

	/**
	 * Indica o valor de um header da resposta HTTP
	 */
	public void set(String key, String value)
	{
		this.headers.put(key, value);
	}

	/**
	 * Retorna um header da resposta HTTP
	 */
	public String get(String key)
	{
		return this.headers.get(key);
	}
}