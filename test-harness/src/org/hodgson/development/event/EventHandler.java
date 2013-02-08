package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Description: Comms handler is reponsible for handling an incoming message.
 * 
 * SEND ACTIONS - HELO - Register child with server. TERM - Unregister child with server. LIVE - Send an acknowledgement
 * to server roll call.
 * 
 * RECV ACTIONS - CALL - Server checks we are still here. KIDS - Server notifies of other child nodes and their listener
 * ports. TERM - Server informs us of death.
 * 
 * @author Ralphho
 * @since 19 Sep 2007
 * @version $Revision: 1.2 $
 * 
 */
public class EventHandler extends EventProtocols implements Runnable
{
	private static final Log log = LogFactory.getLog(EventHandler.class);

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private Socket clientSocket;
	private MessagingNode server;

	private Thread workerThread;

	/**
	 * 
	 * Constructor
	 * 
	 * @param address
	 * @param port
	 * @throws IOException
	 */
	public EventHandler(MessagingNode server, Socket clientSocket)
			throws IOException
	{
		super();

		this.clientSocket = clientSocket;
		this.server = server;

		ois = new ObjectInputStream(clientSocket.getInputStream());
		oos = new ObjectOutputStream(clientSocket.getOutputStream());

		workerThread = new Thread(this);
		workerThread.start();
	}

	/**
	 * Listen for messages from master socket.
	 */
	public void run()
	{
		try
		{
			synchronized (server)
			{
				Event e = EventProtocols.readEvent(ois);
				server.recv(e);
				clientSocket.close();
			}
		}
		catch (Exception e)
		{
			log.debug("Error communicating with the Server", e);
		}
	}

	/** *********************************************************** */
	public void send(String Message)
	{
		try
		{
			oos.writeBytes(Message);
		}
		catch (IOException _IoExc)
		{
		}
	}
}
