package pa.listings.sockets;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * 
 * Description: Class that listens for and monitors clients, keeping an eye on their status. The individuals in the
 * group, decide which messages are relevant to them.
 * 
 * @author Ralphho
 * @since 7 April 2008
 * @version $Revision: 1.8 $
 * 
 */
public class ServerMessageHandler extends AbstractMessageHandler
{
	class GroupOwnerStatusMonitor extends Thread
	{
		public void run()
		{
			try
			{
				while (polling)
				{
					Thread.sleep(10000);
					log.info("Request Status.....");
					sendMessage(MessageProtocols.STATUS_REQUEST);
				}
			}
			catch (Exception e1)
			{
				log.error("[" + name + "] Group Owner Listener failure.", e1);
			}
		}
	}

	public static void main(String[] args)
	{
		try
		{
			MessageHandler handler = new ServerMessageHandler();
			handler.registerUser(MessageProtocols.ADMINISTRATOR);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public ServerMessageHandler() throws UnknownHostException, IOException
	{
		super();

		monitorStatus();
	}

	public void handleMessage(String msg) throws IOException
	{
		int type = parseType(msg);
		String text = msg.substring(msg.indexOf(' ')).trim();

		switch (type)
		{
			case MessageProtocols.TERMINATE:
			{
				polling = false;
			}
				break;

			case MessageProtocols.REGISTER:
			{
				registerUser(text);
			}
				break;

			case MessageProtocols.UNREGISTER:
			{
				unregisterUser(text);
			}
				break;

			case MessageProtocols.STATUS_REQUEST:
			{
				// Maybe clear the client list.
			}
				break;

			case MessageProtocols.USER_MESSAGE:
			{
				log.info("Received:[" + MessageProtocols.EVENT_NAMES[type]
						+ "][" + text + "]");
			}
				break;

			default:
				// do Nothing.
		}
	}

	protected void monitorStatus()
	{
		Thread gosm = new GroupOwnerStatusMonitor();
		gosm.start();
	}

	public void registerUser(String name) throws IOException
	{
		// Register a client user.
	}

	public void unregister() throws IOException
	{
		sendMessage(MessageProtocols.TERMINATE);
	}

	public void unregisterUser(String name) throws IOException
	{
		// Remove a client who has shutdown.
	}
}
