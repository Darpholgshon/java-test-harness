package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:
 * 
 * @author Ralphho
 * @since 19 Sep 2007
 * @version $Revision: 1.2 $
 * 
 */
public class SynchronizationEvent implements Event
{
	private Map register;

	/**
	 * Constructor
	 * 
	 */
	public SynchronizationEvent()
	{
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param register
	 */
	public SynchronizationEvent(Map register)
	{
		this.register = register;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#processEvent(org.hodgson.development.event.MessagingNode)
	 */
	public void processEvent(MessagingNode server)
	{
		Map register = server.getRegister();
		register.clear();

		Iterator keyIter = this.register.keySet().iterator();

		while (keyIter.hasNext())
		{
			String key = keyIter.next().toString();
			register.put(key, this.register.get(key));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#readEvent(java.io.ObjectInputStream)
	 */
	@SuppressWarnings("unchecked")
	public void readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		register = new HashMap();
		int count = in.readInt();

		for (int i = 0; i < count; i++)
		{
			String name = in.readUTF();
			Object port = in.readObject();
			register.put(name, port);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#writeEvent(java.io.ObjectOutputStream)
	 */
	public void writeEvent(ObjectOutputStream out) throws IOException
	{
		int count = register.size();
		out.writeInt(count);

		Iterator keyIter = register.keySet().iterator();

		while (keyIter.hasNext())
		{
			String key = keyIter.next().toString();
			out.writeUTF(key);
			out.writeObject(register.get(key));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#writeEventId(java.io.ObjectOutputStream)
	 */
	public void writeEventId(ObjectOutputStream out) throws IOException
	{
		out.writeShort(EventProtocols.SynchronizationEventId);
	}
}