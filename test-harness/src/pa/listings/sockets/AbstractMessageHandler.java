package pa.listings.sockets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractMessageHandler implements MessageHandler
{
	class GroupListener extends Thread
	{
		public void run()
		{
			try
			{
				// Join the group and listen for messages.
				groupInterface.joinGroup(group);

				while (polling)
				{
					// get a responses!
					byte[] buf = new byte[1024];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					groupInterface.receive(packet);

					String msg = new String(packet.getData()).substring(0,
							packet.getLength());
					handleMessage(msg);
				}
				groupInterface.leaveGroup(group);
			}
			catch (IOException e1)
			{
				log.error("[" + name + "] Group Listener failure.", e1);
			}
		}
	}

	protected static Log log = LogFactory.getLog(MessageHandler.class);

	/** Message queue **/
	protected List messages;

	/** Listeners to tell when we have a USER/System message **/
	protected Vector listeners;

	/** Flags - groupOwner and polling, pretty straight forward **/
	protected boolean polling = true;

	/** The InetAddress of the MulticastSocket **/
	private InetAddress group;

	/** The MulticastSocket that listens for broadcast messages. **/
	private MulticastSocket groupInterface;

	protected String name;

	public AbstractMessageHandler() throws UnknownHostException, IOException
	{
		group = InetAddress.getByName(MessageProtocols.GROUP_IP);
		groupInterface = new MulticastSocket(MessageProtocols.GROUP_PORT);
		listeners = new Vector();
		messages = new ArrayList();

		Thread gl = new GroupListener();
		gl.start();
	}

	/**
	 * Add a listener that will be notified when a new message has been recieved
	 * 
	 * @param ml
	 *            the listener to notify when a message has been receieved
	 */
	public void addMessageListener(MessageListener ml)
	{
		if (!listeners.contains(ml))
		{
			listeners.addElement(ml);
		}
	}

	protected void broadcastMessage(String msg) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(msg.getBytes(),
				msg.length(), group, MessageProtocols.GROUP_PORT);
		groupInterface.send(packet);
	}

	/**
	 * Fires a message received event
	 * 
	 * @param type
	 *            the message type
	 * @param message
	 *            the message received
	 */
	protected void fireMessageReceived(String message, int type)
	{
		Enumeration en = listeners.elements();
		while (en.hasMoreElements())
		{
			MessageListener ml = (MessageListener) en.nextElement();
			ml.messageReceived(new MessageEvent(message, System
					.currentTimeMillis(), type));
		}
	}

	/**
	 * Parse the integer message code from the start of the message.
	 * 
	 * @param msg
	 * @return
	 */
	protected int parseType(String msg)
	{
		int type = -1;
		String buff = msg.substring(0, msg.indexOf(' '));
		try
		{
			// We have read the chars into the buffer now try and parse an int.
			type = Integer.parseInt(buff);
		}
		catch (NumberFormatException nfe)
		{
			log.error("[" + name
					+ "] Message received, but could not decipher number ["
					+ buff + "]", nfe);
		}
		return type;
	}

	/**
	 * Helper method to get the message type from the MLE message server.
	 * 
	 * @return
	 */
	protected String readMessage(BufferedInputStream in) throws IOException
	{
		int c;
		StringBuffer buff = new StringBuffer();

		while ((c = in.read()) != -1)
		{
			buff.append((char) c);
		}
		return buff.toString();
	}

	/**
	 * Remove a previously added listener
	 * 
	 * @param ml
	 *            the listener to remove
	 */
	public void removeMessageListener(MessageListener ml)
	{
		listeners.removeElement(ml);
	}

	protected void sendMessage(int type) throws IOException
	{
		broadcastMessage(type + " " + name);
	}

	protected void sendMessage(String msg, int type) throws IOException
	{
		broadcastMessage(type + " " + name + " " + msg);
	}

}
