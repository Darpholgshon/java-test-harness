package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EventProtocols
{
	/**
	 * Used as a placeholder for NULL Event.
	 */
	public static final short NullEventId = 0;

	/**
	 * Used to register a client node into the network.
	 */
	public static final short RegistrationEventId = 1;

	/**
	 * Used to acknowledge a request from the server.
	 */
	public static final short AcknowledgementEventId = 2;

	/**
	 * Used to notify termination of this socket communication.
	 */
	public static final short TerminateEventId = 3;

	/**
	 * Used to pass information about to a node.
	 */
	public static final short InformationEventId = 4;

	/**
	 * Used by the server to keep the network alive and synchronised.
	 */
	public static final short RollCallEventId = 5;

	/**
	 * Used by the server to keep the network alive and synchronised.
	 */
	public static final short SynchronizationEventId = 6;

	private static final short eventCount = 7;
	private static final Class[] eventClasses = new Class[eventCount];

	static
	{
		eventClasses[NullEventId] = NullEvent.class;
		eventClasses[RegistrationEventId] = RegistrationEvent.class;
		eventClasses[AcknowledgementEventId] = AcknowledgementEvent.class;
		eventClasses[TerminateEventId] = TerminateEvent.class;
		eventClasses[InformationEventId] = InformationEvent.class;
		eventClasses[RollCallEventId] = RollCallEvent.class;
		eventClasses[SynchronizationEventId] = SynchronizationEvent.class;
	}

	public static Event readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		short eType = in.readShort();

		try
		{
			Event e = (Event) eventClasses[eType].newInstance();
			e.readEvent(in);

			return e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void writeEvent(ObjectOutputStream out, Event e)
			throws IOException
	{
		e.writeEventId(out);
		e.writeEvent(out);
	}
}
