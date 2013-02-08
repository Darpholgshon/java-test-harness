package pa.listings.sockets;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMessageHandler extends AbstractMessageHandler
{
	public static void main(String[] args)
	{
		MessageHandler handler = null;

		try
		{
			handler = new ClientMessageHandler();
			handler.registerUser("RALPH" + (int) (Math.random() * 100));
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public ClientMessageHandler() throws UnknownHostException, IOException
	{
		super();
	}

	public void handleMessage(String msg) throws IOException
	{
		int type = parseType(msg);
		msg.substring(msg.indexOf(' ')).trim();

		switch (type)
		{
			case MessageProtocols.STATUS_REQUEST:
			{
				sendMessage(MessageProtocols.USER_MESSAGE);
			}
				break;

			default:
				// do Nothing.
		}
	}

	public void registerUser(String username) throws IOException
	{
		log.info("Registering user [" + username + "]");
		name = username;
		broadcastMessage(MessageProtocols.REGISTER + " " + name);
	}

	public void unregister() throws IOException
	{
		log.info("Unregistering user [" + name + "]");
		broadcastMessage(MessageProtocols.UNREGISTER + " " + name);
	}
}