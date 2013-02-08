package pa.listings.sockets;

/**
 * Message Event.
 * 
 * @author ralphho
 * @since 7 April 2008
 * @version $Revision: 1.0 $
 * 
 * @see MessageListener
 */
public class MessageEvent
{
	private Object message;
	private long recieved;
	private int type;

	/**
	 * Indicates a system message
	 */
	public final static int SYSTEM_MESSAGE = 1;

	/**
	 * Indicates a user message
	 */
	public final static int USER_MESSAGE = 2;

	/**
	 * @param message
	 * @param recieved
	 * @param type
	 */
	public MessageEvent(Object message, long recieved, int type)
	{
		this.message = message;
		this.recieved = recieved;
		this.type = type;
	}

	/**
	 * Retrieves the message stored in this MessageEvent
	 * 
	 * @return the message stored in this MessageEvent
	 */
	public Object getMessage()
	{
		return message;
	}

	/**
	 * Returns the time at which the message recieved
	 * 
	 * @return the time this message was recieved
	 */
	public long getTimeRecieved()
	{
		return recieved;
	}

	/**
	 * Returns the type of message recieved
	 * 
	 * @return the type of message recieved
	 */
	public int getType()
	{
		return type;
	}
}
