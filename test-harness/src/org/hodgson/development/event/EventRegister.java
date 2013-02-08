package org.hodgson.development.event;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EventRegister extends HashMap implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(EventRegister.class);
	private MessagingNode server;
	private Thread pollingThread;

	public EventRegister(MessagingNode server)
	{
		this.server = server;

		pollingThread = new Thread(this);
		pollingThread.start();
	}

	public void run()
	{
		while (pollingThread.isAlive())
		{
			synchronized (server)
			{
				Iterator keys = keySet().iterator();

				while (keys.hasNext())
				{
					String key = keys.next().toString();
					Integer port = (Integer) get(key);
					log.info("Checking User[" + key + "]-[" + port + "]");

					server.synchronizeNode(key, port.intValue());
				}
			}
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void stopPolling()
	{
		pollingThread.interrupt();
		pollingThread = null;
	}
}
