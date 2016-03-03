package br.unirio.simplemvc.actions.smtp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that records mails sent for testing purposes
 * 
 * @author marcio.barros
 */
public class TestMailSender implements IMailSender
{
	private List<MailMessage> sentMail;
	
	/**
	 * Initializes the mail sender for testing purposes
	 */
	public TestMailSender()
	{
		this.sentMail = new ArrayList<MailMessage>();
	}

	/**
	 * Opens a connection to send many e-mails
	 */
	@Override
	public boolean open()
	{
		return false;
	}

	/**
	 * Closes a connection to send many e-mails
	 */
	@Override
	public boolean close()
	{
		return false;
	}

	/**
	 * Sends a message through e-mail 
	 */
	@Override
	public boolean send(MailMessage message)
	{
		sentMail.add(message);
		return true;
	}
	
	/**
	 * Returns the number of e-mails sent
	 */
	public int countMessages()
	{
		return sentMail.size();
	}
	
	/**
	 * Returns a message sent through e-mail, given its index
	 */
	public MailMessage getMessageIndex(int index)
	{
		return sentMail.get(index);
	}
	
	/**
	 * Returns an enumeration of the e-mail
	 */
	public Iterator<MailMessage> getMessages()
	{
		return sentMail.iterator();
	}

	/**
	 * Removes all former outgoing mail messages
	 */
	public void clear()
	{
		sentMail.clear();
	}
}