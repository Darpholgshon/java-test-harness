package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessagingNode extends EventProtocols implements Runnable
{
	private static final Log log = LogFactory.getLog(MessagingNode.class);

	private static final int DEFAULT_PORT = 2352;
	private static boolean alreadyRunning = false;

	public static void main(String[] args)
	{
		try
		{
			String name = "MASTER";

			if (args.length >= 1)
			{
				name = args[0];
			}
			new MessagingNode(name);
		}
		catch (Exception e)
		{
			log.fatal("Toal failure to open a socket connection.", e);
		}
	}

	private ServerSocket incoming;

	private Thread serverThread;
	private EventRegister register;
	private String name;
	private int port;

	private boolean child;

	private MessagingNode(String name) throws IOException, InterruptedException
	{
		this.name = name;
		register = new EventRegister(this);

		initialiseConnection();

		serverThread = new Thread(this);
		serverThread.start();
	}

	private ServerSocket bindSocket(int port) throws IOException
	{
		ServerSocket s = new ServerSocket(port);
		child = port != DEFAULT_PORT;
		this.port = s.getLocalPort();

		// For debug only remove later.
		name = (child ? "CHILD" : "MASTER") + ":" + this.port;

		return s;
	}

	private ObjectOutputStream createOutputStream(int port)
			throws UnknownHostException, IOException
	{
		log.debug("Creating communications connection to port " + port);
		Socket clientSocket = new Socket("localhost", port);
		return new ObjectOutputStream(clientSocket.getOutputStream());
	}

	public Map getRegister()
	{
		return register;
	}

	private void initialiseConnection() throws IOException,
			InterruptedException
	{
		try
		{
			incoming = bindSocket(DEFAULT_PORT);
		}
		catch (BindException e1)
		{
			log.debug("Master already runninng!");
			incoming = bindSocket(0);
		}
		alreadyRunning = true;
		log.debug("Listener started as [" + name + "]");

		if (child)
		{
			registerChild();
		}
		else
		{
			register.put(name, new Integer(port));
		}
	}

	/** ******************************************************************************************** */
	public synchronized void recv(Event e)
	{
		log.info("Recieved Event: " + e);
		e.processEvent(this);
	}

	public synchronized void registerChild()
	{
		try
		{
			ObjectOutputStream out = createOutputStream(DEFAULT_PORT);
			EventProtocols.writeEvent(out, new RegistrationEvent(name, port));
			out.close();
		}
		catch (Exception e1)
		{
			log.fatal("Failed to register messaging node.", e1);
		}
	}

	public void run()
	{
		try
		{
			while (alreadyRunning)
			{
				Socket socket = incoming.accept();

				log.debug(name + ": Accepting connection...");
				new EventHandler(this, socket);

				Thread.sleep(1500);
			}
		}
		catch (Exception e)
		{
			log.error("Error in communication protocol! ", e);
		}
	}

	public synchronized void synchronizeNode(String name, int port)
	{
		if (port != this.port)
		{
			try
			{
				log.info("Synchronize Child node - " + port);
				ObjectOutputStream out = createOutputStream(port);
				EventProtocols.writeEvent(out, new SynchronizationEvent(
						register));
				out.flush();
				out.close();
			}
			catch (Exception e1)
			{
				synchronized (register)
				{
					log
							.fatal("Failed to synchnonize node - assume terminated.");
					register.remove(name);
				}
			}
		}
	}
}