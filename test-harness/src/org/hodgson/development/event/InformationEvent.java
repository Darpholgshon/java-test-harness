package org.hodgson.development.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Description:
 * 
 * @author Ralphho
 * @since 19 Sep 2007
 * @version $Revision: 1.2 $
 * 
 */
public class InformationEvent implements Event
{
	/**
	 * Constructor
	 * 
	 */
	public InformationEvent()
	{
		// TODO Auto-generated constructor stub
	}

	public void processEvent(MessagingNode server)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#readEvent(java.io.ObjectInputStream)
	 */
	public void readEvent(ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#writeEvent(java.io.ObjectOutputStream)
	 */
	public void writeEvent(ObjectOutputStream out) throws IOException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hodgson.development.event.Event#writeEventId(java.io.ObjectOutputStream)
	 */
	public void writeEventId(ObjectOutputStream out) throws IOException
	{
		out.writeShort(EventProtocols.InformationEventId);
	}
}
