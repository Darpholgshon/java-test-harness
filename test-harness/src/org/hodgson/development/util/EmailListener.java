package org.hodgson.development.util;

import java.security.Security;
import java.util.Arrays;
import java.util.Properties;
import java.util.Stack;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.swing.JOptionPane;

public class EmailListener implements Runnable
{
	private Store store = null;
	
	private Folder folder;
	
	protected Stack<Message> queue = new Stack<Message>();
		
	static
	{
		Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );

		System.getProperties().put("mail.host", "pahexch.ad.pvt");
		System.getProperties().put("mail.user", "AD\\tvs");
		System.getProperties().put("mail.pswd", "Change1t");
		System.getProperties().put("mail.folder", "Satellite/Time and Title channels");
	}
	
	public void init()
	{
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props, null);
		
		store = null;
		
		try
		{
			// Get the store
			store = session.getStore("imap");
			store.connect( (String)props.get("mail.host"), (String)props.get("mail.user"), (String)props.get("mail.pswd") );
						
			String location = (String)System.getProperties().get("mail.folder"); 
			
			// Get folder
			folder = store.getFolder(location);
			
			folder.addMessageCountListener( new MessageCountListener()
			{
				@Override
				public void messagesRemoved(MessageCountEvent arg0)
				{
					synchronized (queue)
					{
						for (Message msg :  arg0.getMessages() )
						{
							if (queue.contains(msg) )
							{
								System.out.println("Remove deleted message: " + msg);
								queue.remove(msg);
							}
						}
					}
				}
				
				@Override
				public void messagesAdded(MessageCountEvent arg0)
				{
					synchronized (queue)
					{
						System.out.println("Posting to queue: [" + arg0.getMessages().length + "] new messages.");
						queue.addAll( Arrays.asList( arg0.getMessages() ) );
					}
				}
			});
			System.out.println( folder.getFullName() + " : " + folder.getMessageCount() );
			folder.open(Folder.READ_WRITE);

//			
//			synchronized (queue)
//			{
//				Calendar cal = DateUtils.createInstance();
//				cal.add(Calendar.WEEK_OF_YEAR, -2);
//
//				SearchTerm st = new ReceivedDateTerm(ReceivedDateTerm.GE, cal.getTime() );
//				
//				for (Message msg: folder.search(st) )
//				{
//					System.out.println("Old Message: " + msg.getSubject() + " : " + msg.getReceivedDate() );
//					queue.add(msg);
//				}
//			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				checkForMessages();
			}
			catch (Exception e) 
			{
				System.err.println("Error processing message queue!");
			}
		}
	}
	
	private void checkForMessages() throws Exception
	{
		folder.hasNewMessages();
		
		synchronized (queue)
		{
			System.out.println("Processing Queue. Please wait...");
			
			while (queue.empty() == false)
			{
				Message m = queue.pop();
				
				System.out.println(m.getSubject() + " : " + m.getReceivedDate() );
				Thread.sleep(1000);
			}
		}
		Thread.sleep(10000);
	}

	public static void main(String[] args)
	{
		EmailListener el = new EmailListener();
		el.init();
		
		Thread t = new Thread(el);
		t.start();
				
		JOptionPane.showMessageDialog(null, "Press to terminate listener");
		t.interrupt();
		
		System.out.println("Exit");
	}
}
