package br.unirio.simplemvc.actions.smtp;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a message to be sent through e-mail
 * 
 * @author marcio.barros
 */
public class MailMessage
{
	private String from;
	private String subject;
	private List<String> recipients;
	private List<String> parts;
	
	/**
	 * Initializes the mail message
	 */
	public MailMessage()
	{
		this.from = "";
		this.subject = "";
		this.recipients = new ArrayList<String>();
		this.parts = new ArrayList<String>();
	}
	
	/**
	 * Returns the source of the message
	 */
	public String getFrom()
	{
		return from;
	}
	
	/**
	 * Sets the source of the message
	 */
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	/**
	 * Returns the subject of the message
	 */
	public String getSubject()
	{
		return subject;
	}
	
	/**
	 * Sets the subject of the message
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * Adds a recipient to the message
	 */
	public void addRecipient(String to)
	{
		this.recipients.add(to);
	}

	/**
	 * Returns an enumeration of the recipients
	 */
	public Iterable<String> getRecipients()
	{
		return recipients;
	}

	/**
	 * Adds a body part to the message
	 */
	public void addBodyPart(String part)
	{
		this.parts.add(part);
	}

	/**
	 * Returns an enumeration of the message parts
	 */
	public Iterable<String> getParts()
	{
		return parts;
	}

	/**
	 * Checks whether some part of the e-mail contain a given string
	 */
	public boolean partsContain(String s)
	{
		for (String part : parts)
			if (part.contains(s))
				return true;
		
		return false;
	}
}