package br.unirio.simplemvc.actions.smtp;

/**
 * Abstract interface for mail message senders
 * 
 * @author marcio.barros
 */
public interface IMailSender
{
	/**
	 * Sends an e-mail message to a bunch of recipients
	 */
	public abstract boolean send(MailMessage message);
}