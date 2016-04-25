package br.unirio.crud.services;

import java.util.List;

import lombok.Getter;
import br.unirio.crud.config.Configuracao;
import br.unirio.simplemvc.actions.smtp.IMailSender;
import br.unirio.simplemvc.actions.smtp.MailMessage;
import br.unirio.simplemvc.actions.smtp.MailSender;
import br.unirio.simplemvc.utils.StringUtils;

/**
 * Classe responsável pelo envio de e-mails
 * 
 * @author Marcio Barros
 */
public class GerenciadorEmail
{
	/**
	 * Instância única do gerenciador de e-mails
	 */
	private static GerenciadorEmail instance; 
	
	/**
	 * Mecanismo selecionado para envio de e-mails
	 */
	private IMailSender sender;
	
	/**
	 * Última mensagem de erro ao enviar e-mails
	 */
	private @Getter String lastErrorMessage;

	/**
	 * Inicializa o serviço de envio de e-mails
	 */
	private GerenciadorEmail()
	{
		this.sender = null;
	}

	/**
	 * Retorna a instância única do gerenciador de e-mails
	 */
	public static GerenciadorEmail getInstance()
	{
		if (instance == null)
			instance = new GerenciadorEmail();
		
		return instance;
	}
	
	/**
	 * Muda o gateway de envio de e-mails - usado para testes
	 */
	public void setMailSender(IMailSender sender)
	{
		this.sender = sender;
	}

	/**
	 * Cria a instância responsável por enviar e-mails
	 */
	private IMailSender createMailSender()
	{
		if (this.sender == null)
			this.sender = new MailSender(Configuracao.getSmtpGatewayHostname(), Configuracao.getSmtpGatewayUser(), Configuracao.getSmtpGatewayPassword());
		
		return this.sender;
	}
	
	/**
	 * Abre a conexão para envio de diversos e-mails
	 */
	public boolean prepara()
	{
		return createMailSender().open();
	}
	
	/**
	 * Fecha a conexão para envio de diversos e-mails
	 */
	public boolean encerra()
	{
		return createMailSender().close();
	}

	/**
	 * Envia um e-mail referente à Plataforma BVTrade
	 */
	public boolean enviaBruto(String emails, String mensagem, String corpo)
	{
		createMailSender();
		
		MailMessage mail = new MailMessage();
		mail.setFrom(Configuracao.getEmailOrigem());
		mail.setSubject(Configuracao.getPrefixoNotificacaoEmail() + mensagem);
		
		List<String> emailsSeparados = StringUtils.stringToList(emails);

		for (String email : emailsSeparados)
			mail.addRecipient(email);
		
		mail.addBodyPart(corpo);
		return sender.send(mail);
	}

	/**
	 * Envia um e-mail referente à Plataforma BVTrade
	 */
	public boolean envia(String nomeDestinatario, String emailDestinatario, String mensagem, String corpo)
	{
		if (this.sender == null)
			this.sender = new MailSender(Configuracao.getSmtpGatewayHostname(), Configuracao.getSmtpGatewayUser(), Configuracao.getSmtpGatewayPassword());
		
		MailMessage mail = new MailMessage();
		mail.setFrom(Configuracao.getEmailOrigem());
		mail.setSubject(Configuracao.getPrefixoNotificacaoEmail() + mensagem);
		mail.addRecipient(emailDestinatario);	

		String html = "<body>";
		html += "<p>Ol&aacute; <b>" + StringUtils.escapeAccents(nomeDestinatario) + ".</b></p>";
		html += StringUtils.escapeAccents(corpo);
		html += "<p>Atenciosamente,</p>";
		html += "<p>Equipe de desenvolvimento</p>";
		html += "<p style='color:gray;font-size:9px;'>_____";
		html += "<p style='color:gray;font-size:9px;'>Este e-mail é gerado automaticamente. Por favor, não responda a este e-mail.</p>";
		html += "</body>";
		mail.addBodyPart(html);
		return sender.send(mail);
	}
}