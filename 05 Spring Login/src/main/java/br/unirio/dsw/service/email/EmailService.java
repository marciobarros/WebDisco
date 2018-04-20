package br.unirio.dsw.service.email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import br.unirio.dsw.configuration.Configuration;

/**
 * Classe responsável pelo envio de e-mails
 * 
 * @author marciobarros
 */
@Service("emailService")
public class EmailService
{
	/**
	 * Modelo padronizado dos e-mails transacionais
	 */
	private static final String EMAIL_PADRAO =
		"<table style='width:100%;background-color:#F7F7F7;text-align:center;' cellspacing='0' cellpadding='0' border='0' align='center'>" +
		"<tbody>" +
		"<tr>" +
		"<td style='padding-top:8px;padding-bottom:8px;'>" +
		"    <table style='width:100%;max-width:600px;background-color:#FFFFFF;text-align:left;' cellspacing='0' cellpadding='0' border='0' align='center'>" +
		"    <tbody>" +
		"    <tr>" +
		"    <td>" +
		"        <table style='width:100%;' cellspacing='0' cellpadding='0' border='0'>" +
		"        <tbody>" +
		"        <tr>" +
		"        <td style='padding:0px 0px 2px 0px;background-color:#86B13B;'>" +
		"        </td>" +
		"        </tr>" +
		"        <tr>" +
		"        <td>" +
		"            <br>" + 
		"            <p>Olá <b>@1.</b></p>" +
		"            @2" +
		"            <p>Atenciosamente,</p>" +
		"            <p>Equipe UNIRIO</p>" + 
		"            <br>" + 
		"        </td>" +
		"        </tr>" +
		"        <tr>" +
		"        <td style='padding:0px 0px 2px 0px;background-color:#86B13B;'>" +
		"        </td>" +
		"        </tr>" +
		"        </tbody>" +
		"        </table>" +
		"    </td>" +
		"    </tr>" +
		"    </tbody>" +
		"    </table>" +
		"</td>" +
		"</tr>" +
		"</tbody>" +
		"</table>";
	
	/**
	 * Conta dos desenvolvedores
	 */
	@Value("${EMAIL_DESENVOLVEDOR}")
	private String developerAccount;

	/**
	 * Prefixo para envio de e-mails, vindo do arquivo de configuração da aplicação
	 */
	@Value("${SENDGRID_KEY}")
	private String sendGridKey;

	/**
	 * Prefixo para envio de e-mails, vindo do arquivo de configuração da aplicação
	 */
	@Value("${MAIL_NOTICE}")
	private String mailNotice;
	
	/**
	 * E-mail de origem das mensagens enviadas pela plataforma
	 */
	@Value("${EMAIL_SOURCE}")
	private String emailSource;
	
	/**
	 * Impede a instanciação direta do serviço de envio de e-mails
	 */
	private EmailService()
	{
	}

	/**
	 * Envia um e-mail referente à plataforma
	 */
	public boolean sendToUser(String destinationName, String destinationEmail, String title, String contents)
	{
		String html = EMAIL_PADRAO;
		html = html.replace("@1", destinationName);
		html = html.replace("@2", contents);
		return send(destinationEmail, title, html);
	}

	/**
	 * Envia um e-mail para os desenvolvedores do sistema
	 */
	public boolean sendToDevelopers(String title, String contents)
	{
		return send(developerAccount, title, contents);
	}

	/**
	 * Envia e-mail utilizando modelo assincrono pela API do sendgrid
	 */
	private boolean send(String email, String title, String contents)
	{
		JsonObject jsonEmail = new JsonObject();
		
		// Prepara o corpo do email
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("type", "text/html");
		jsonBody.addProperty("value", contents);
		JsonArray jsonContents = new JsonArray();
		jsonContents.add(jsonBody);
		
		// Poca-yoke para evitar o envio de e-mails de desenvolvimento para usuários finais
		if (Configuration.isStaggingEnvironment())
			email = developerAccount;
		
		// Registra assunto do email
		JsonObject personalizacao = new JsonObject();
		personalizacao.addProperty("subject", mailNotice + " " + title);
		
		// Registra os destinatarios
		JsonArray jsonTo = new JsonArray();
		JsonObject jsonTarget = new JsonObject();
		jsonTarget.addProperty("email", email);
		jsonTo.add(jsonTarget);
		personalizacao.add("to", jsonTo);

		// Insere personalizacao (recipientes e assunto) ao email
		JsonArray personalizacoes = new JsonArray();
		personalizacoes.add(personalizacao);
		
		// Registra a origem
		JsonObject jsonFrom = new JsonObject();
		jsonFrom.addProperty("email", emailSource);
		
		// Monta objeto json do email para ser enviado na requisicao
		jsonEmail.add("personalizations", personalizacoes);
		jsonEmail.add("from", jsonFrom);
		jsonEmail.add("content", jsonContents);
		
		// Envia email
		String retorno = post("https://api.sendgrid.com/v3/mail/send", jsonEmail.toString());
		
		// Registra no console mensagem de retorno. (Provavelmente erro)
		if (retorno != null && retorno.compareTo("") != 0)
			System.out.println("GerenciadorEmail::enviaAsync => " + retorno);

		else if (retorno == null)
			return false;
		
		return (retorno.compareTo("") == 0);
	}

	/**
	 * Envia uma requisição POST com headers
	 */
	private String post(String endereco, String body)
	{
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		
		RequestBody reqBody = RequestBody.create(mediaType, body);
		Builder builder = new Request.Builder()
				.url(endereco)
				.post(reqBody);
		
		builder.addHeader("Cache-Control", "no-cache");
		builder.addHeader("Authorization", "Bearer " + sendGridKey);
		builder.addHeader("Content-Type", "application/json");
		
		Request request = builder.build();
		String responseData = "";
		
		try 
		{
			Response response = client.newCall(request).execute();
			responseData = response.body().string();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			responseData = e.getMessage();
		}
		
		return responseData;
	}
}