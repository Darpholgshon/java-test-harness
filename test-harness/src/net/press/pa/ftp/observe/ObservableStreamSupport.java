package net.press.pa.ftp.observe;

public interface ObservableStreamSupport extends ObservableStreamListener
{
	/**
	 * Adds an observer.
	 * 
	 * @param observer
	 */
	public void addObserver(ObservableStreamListener observer);

	/**
	 * Removes an observer
	 * 
	 * @param observer
	 */
	public void removeObserver(ObservableStreamListener observer);
}
