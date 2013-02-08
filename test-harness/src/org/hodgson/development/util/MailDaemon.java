package org.hodgson.development.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Create and send a message to one or more people.
 *
 * @author Ralph Hodgson
 */
public class MailDaemon
{
	private static final Log log = LogFactory.getLog(MailDaemon.class);
	
	public static final String MESSAGE_SENT_VERIFY = "ML-01: Your message was sent successfully.";
	public static final String FROM_NOT_SET = "ML-02: Cannot send anonymous mail.";
	public static final String TO_NOT_SET = "ML-03: The message must have at least one recipient.";
	public static final String SUBJECT_NOT_SET = "ML-04: A subject must be specified.";
	public static final String MESSAGE_NOT_SET = "ML-05: Cannot send an empty message.";
	public static final String MAIL_HOST_NOT_SET = "ML-06: A SMTP Mail Host is required to send mail.";

	/** The Sender. */
	private String sender;

	/** The Recipients. */
	private List recipients;

	/** Also copy to. */
	private List copies;

	/** Subject Matter */
	private String subject;

	/** The Message */
	private Object message;

	/** The SMTP Mail Server. */
	private String mailHost;

	/** The Mail Server Login. */
	private String login;

	/** The Mail Server Password. */
	private String password;

	/** NOtify user of whats happened. */
	private Vector messages;

	/** If the message is an HTML message */
	private boolean htmlMessage;
	
	private static MailDaemon daemon;
	
	/**
	 * The Default Constructor.
	 */
	private MailDaemon()
	{
	}
	
	public static final MailDaemon getInstance()
	{
		if (daemon == null)
		{
			daemon = new MailDaemon();
		}
		return daemon;
	}

	/**
	 * Send the e-mail
	 */
	public void sendMail()
	{
		messages = new Vector();

		// Check that parameters have been entered
		if ((sender == null) || (sender.length() == 0))
		{
			messages.addElement(FROM_NOT_SET);
		}

		if ((recipients == null) || (recipients.size() == 0))
		{
			messages.addElement(TO_NOT_SET);
		}

		if ((subject == null) || (subject.length() == 0))
		{
			messages.addElement(SUBJECT_NOT_SET);
		}

		if ((message == null) || (message.toString().length() == 0))
		{
			messages.addElement(MESSAGE_NOT_SET);
		}

		if ((mailHost == null) || (mailHost.length() == 0))
		{
			messages.addElement(MAIL_HOST_NOT_SET);
		}

		if (messages.size() > 0)
		{
			return;
		}

		try
		{
			// Get system properties
			Properties props = System.getProperties();

			// Setup mail server
			props.put("mail.smtp.host", mailHost);
			Session session = Session.getDefaultInstance(props, null);

			// Define message
			MimeMessage msg = new MimeMessage(session);

			// Set the from address
			msg.setFrom(new InternetAddress(sender));

			// Add recipients.
			for (int x = 0; x < recipients.size(); x++)
			{
				String recipient = recipients.get(x).toString();
				
				if (recipient.length() > 0)
				{
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				}
			}

			// Add a cc entry.
			for (int y = 0; (copies != null) && (y < copies.size()); y++)
			{
				String recipient = copies.get(y).toString();
				
				if (recipient.length() > 0)
				{
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
				}
			}

			// Set the subject
			msg.setSubject(subject);
			msg.setSentDate( new Date() );

			// Set the content
			if (htmlMessage)
			{
				msg.setContent(message, "text/html");
			}
			else if (message instanceof Multipart)
			{
				msg.setContent((Multipart)message);
			}
			else
			{
				msg.setText(message.toString());
			}
			
			// Send message
			msg.saveChanges(); // implicit with send()
			
			log.debug("MailHost [" + mailHost + "] UserId [" + login + "] Password [" + password + "]");
			
			Transport transport = session.getTransport("smtp");
			transport.connect(mailHost, login, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			
			messages.addElement(MESSAGE_SENT_VERIFY);
		}
		catch (Exception e)
		{
			messages.addElement( e.getMessage() );
		}
	}

	public void setCopies(List copies)
	{
		this.copies = copies;
	}

	public boolean isHtmlMessage()
	{
		return htmlMessage;
	}

	public void setHtmlMessage(boolean htmlMessage)
	{
		this.htmlMessage = htmlMessage;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setMailHost(String mailHost)
	{
		this.mailHost = mailHost;
	}

	public void setMessage(Object message)
	{
		this.message = message;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setRecipients(List recipients)
	{
		this.recipients = recipients;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	public Vector getMessages()
	{
		return messages;
	}
	
	public static void simpleSend(String host, String[] recipients, 
							String sender, String subject, String message)
		throws Exception
	{
		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(props, null);
		
		MimeMessage msg = new MimeMessage(session);
	   	msg.setFrom(new InternetAddress(sender));

		// get the list of recipients
	    InternetAddress[] address = new InternetAddress[recipients.length];
		for (int i=0 ; i<recipients.length ; i++) {
			address[i] = new InternetAddress(recipients[i]);
		}
		
		msg.setRecipients(Message.RecipientType.TO, address);
		msg.setSubject(subject);
		msg.setText(message, "ISO-8859-1");
		msg.setSentDate(new Date());
		    
		// send the message
		Transport.send(msg);
	}
	
	
	public static void main(String[] args)
	{
//		MailDaemon md = new MailDaemon();
//		System.out.println("Mail");
//		md.setMailHost("10.253.65.14");
//		System.out.println(md.mailHost);
//		md.setSubject("TEST");
//		md.setMessage("TEST");
//		System.out.println(md.message);
//		md.setSender("ralph.hodgson@pa.press.net");
//		System.out.println(md.sender);
//		md.setRecipients(Collections.singletonList("ralph.hodgson@pa.press.net") );
//		System.out.println(md.recipients.get(0));
//		md.sendMail();
//		System.out.println("Done");
//		System.out.println( md.getMessages().get(0) );
		try
		{
			MailDaemon.simpleSend("10.253.65.14", 
					new String[]{"ralph.hodgson@pa.press.net"}, 
					"ralph.hodgson@pa.press.net", "TEST", "TEST");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}