package pa.listings.sockets;

import java.io.IOException;

public interface MessageHandler
{
	/**
	 * Handle a message received by the group listener.
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void handleMessage(String message) throws IOException;

	/**
	 * Register an user with the Messsage Group.
	 * 
	 * @param username
	 * @throws IOException
	 */
	public void registerUser(String username) throws IOException;

	/**
	 * Unregister the user from the Message Group.
	 * 
	 * @param username
	 * @throws IOException
	 */
	public void unregister() throws IOException;
}
