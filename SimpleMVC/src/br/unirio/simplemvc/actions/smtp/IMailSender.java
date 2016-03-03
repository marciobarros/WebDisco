package br.unirio.simplemvc.actions.smtp;

/**
 * Abstract interface for mail message senders
 * 
 * @author marcio.barros
 */
public interface IMailSender
{
	/**
	 * Opens the current connection for the transport layer
	 */
	boolean open();
	
	/**
	 * Closes the current transport layer
	 */
	boolean close();

	/**
	 * Sends an e-mail message to a bunch of recipients
	 */
	boolean send(MailMessage message);
}