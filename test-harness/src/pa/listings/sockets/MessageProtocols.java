package pa.listings.sockets;

public class MessageProtocols
{
	public static final int GROUP_PORT = 2366;
	public static final String GROUP_IP = "224.0.0.1";
	public static final int MSG_LIMIT = 10;

	public static final String ADMINISTRATOR = "SYSTEM";

	/**
	 * Indicates a Status Request message
	 */
	public final static int STATUS_REQUEST = 1;

	/**
	 * Indicates a user message
	 */
	public final static int USER_MESSAGE = 2;

	/**
	 * Indicates a user registering with the group.
	 */
	public static final int REGISTER = 3;

	/**
	 * Indicates a user leaving the group.
	 */
	public static final int UNREGISTER = 4;

	/**
	 * Indicates master user leaving group. Group users should try to become master.
	 */
	public static final int TERMINATE = 5;

	public static final String[] EVENT_NAMES =
		{ "NULL", "STAT", "USER", "HELO", "KICK", "TERM" };

}
