package br.unirio.inscricaoppgi.gae.mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.unirio.simplemvc.actions.smtp.IMailSender;
import br.unirio.simplemvc.actions.smtp.MailMessage;

/**
 * Classe que envia e-mails pela API do Google App Engine
 * 
 * @author Marcio
 */
public class GAEMailSender implements IMailSender
{
	@Override
	public boolean send(MailMessage message)
	{
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
 
        try 
        {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(message.getFrom()));
            msg.setSubject(message.getSubject());
            
            for (String recipient : message.getRecipients())
            	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            
            Multipart mp = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            
            for (String part : message.getParts())
            {
	            htmlPart.setContent(part, "text/html");
	            mp.addBodyPart(htmlPart);
            }
            
            msg.setContent(mp);
            Transport.send(msg);
            return true;
 
        } catch (Exception e) 
        {
        	Logger log = Logger.getLogger("MailSender");
        	log.log(Level.SEVERE, e.getMessage());
        	return false;
        }
	}
}