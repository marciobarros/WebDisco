package br.unirio.simplemvc.actions.smtp;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class that sends e-mail over a SMTP host
 * 
 * @author marcio.barros
 */
public class MailSender implements IMailSender
{
	private String hostName;
	private int hostPort;
	private String hostUser;
	private String hostPassword;
	
	/**
	 * Initializes the mail delivery facility giving complete host information
	 * 
	 * @param hostName		The name of the host through which the e-mail will be sent
	 * @param port			The port through which the e-mail will be sent
	 * @param user			The user in the mail host
	 * @param password		The user's password
	 */
	private MailSender(String hostName, int port, String user, String password)
	{
		this.hostName = hostName;
		this.hostPort = port;
		this.hostUser = user;
		this.hostPassword = password;
	}
	
	/**
	 * Initializes the mail delivery facility giving basic host information
	 * 
	 * @param hostName		The name of the host through which the e-mail will be sent
	 * @param user			The user in the mail host
	 * @param password		The user's password
	 */
	public MailSender(String hostName, String user, String password)
	{
		this(hostName, 587, user, password);
	}
	
	/**
	 * Delivers a mail to a given host
	 * 
	 *  @param mail		Mail message that will be delivered
	 */
	public boolean send(MailMessage mail)
	{
		try
		{
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", hostName);
			props.put("mail.smtp.port", hostPort);
			props.put("mail.smtp.auth", "true");
	
			Authenticator auth = new SMTPAuthenticator();
			Session mailSession = Session.getInstance(props, auth);
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);

			Multipart multipart = new MimeMultipart();
			
			for (String part : mail.getParts())
			{
				BodyPart singlePart = new MimeBodyPart();
				singlePart.setContent(part, "text/html;charset=utf-8");
				multipart.addBodyPart(singlePart);
			}
	
			message.setContent(multipart);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setSubject(mail.getSubject());
			
			for (String recipient : mail.getRecipients())
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));	
			
			transport.connect(hostName, hostPort, hostUser, hostPassword);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}
	
	/**
	 * Class that performs SMTP authentication for mail delivering
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(hostUser, hostPassword);
		}
	}
}