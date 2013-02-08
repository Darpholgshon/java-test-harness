package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class RegistrationEvent implements Event
{
	private String name;
	private int port;

	public RegistrationEvent()
	{
		// TODO Auto-generated constructor stub
	}

	public RegistrationEvent(String name, int port)
	{
		this.name = name;
		this.port = port;
	}

	public void processEvent(MessagingNode server)
	{
		Map register = server.getRegister();
		register.put(name, new Integer(port));

		server.synchronizeNode(name, port);
	}

	public void readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		name = in.readUTF();
		port = in.readInt();
	}

	public String toString()
	{
		return RegistrationEvent.class + "[" + name + ", " + port + "]";
	}

	public void writeEvent(ObjectOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeInt(port);
	}

	public void writeEventId(ObjectOutputStream out) throws IOException
	{
		out.writeShort(EventProtocols.RegistrationEventId);
	}
}
