package pa.listings.sockets;

/**
 * Listens for message received events within the MessageServer
 * 
 * @author ralphho
 * @since 7 April 2008
 * @version $Revision: 1.0 $
 * 
 * @see MessageHandler
 */
public interface MessageListener
{

	/**
	 * This is called when a MessageListener recieves an event indicating that a mesages has been recieved
	 * 
	 * @param messageEvent
	 *            the event associated with the message received
	 */
	public void messageReceived(MessageEvent messageEvent);
}
