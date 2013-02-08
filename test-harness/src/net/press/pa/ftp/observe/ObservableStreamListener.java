package net.press.pa.ftp.observe;

public interface ObservableStreamListener
{
	/**
	 * Notify listener of the number of bytes read by a stream identified by a unique clientId.
	 * 
	 * @param bytes
	 *            Number of bytes read.
	 * @param clientId
	 *            The unique clientId.
	 */
	public void bytesRead(int bytes, int clientId);

	/**
	 * Notify listener of the number of bytes written by a stream identified by a unique clientId.
	 * 
	 * @param bytes
	 *            Number of bytes written.
	 * @param clientId
	 *            The unique clientId.
	 */
	public void bytesWrite(int bytes, int clientId);
}
