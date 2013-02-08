package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Event
{
	public void processEvent(MessagingNode server);

	public void readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException;

	public void writeEvent(ObjectOutputStream out) throws IOException;

	public void writeEventId(ObjectOutputStream out) throws IOException;
}
