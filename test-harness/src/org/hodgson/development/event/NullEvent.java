package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NullEvent implements Serializable, Event
{
	public static final long serialVersionUID = -8563434527611427548L;

	private static NullEvent sNE = new NullEvent();

	public static NullEvent getNullEventInstance()
	{
		return sNE;
	}

	public NullEvent()
	{
	}

	public void processEvent(MessagingNode server)
	{
		// TODO Auto-generated method stub

	}

	public void readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
	}

	public String toString()
	{
		return "NullEvent";
	}

	public void writeEvent(ObjectOutputStream out) throws IOException
	{
	}

	public void writeEventId(ObjectOutputStream out) throws IOException
	{
		out.writeShort(EventProtocols.NullEventId);
	}

}