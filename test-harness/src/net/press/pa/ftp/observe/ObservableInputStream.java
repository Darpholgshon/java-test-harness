package net.press.pa.ftp.observe;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObservableInputStream extends InputStream implements
		ObservableStream
{
	private static Log log = LogFactory.getLog(ObservableInputStream.class);

	private InputStream stream = null;
	private ObservableStreamListener observer;
	private int clientId;
	private int totalBytes = 0;

	public ObservableInputStream(InputStream is,
			ObservableStreamListener observer, int clientId)
	{
		stream = is;
		this.observer = observer;
		this.clientId = clientId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#available()
	 */
	public int available() throws IOException
	{
		return stream.available();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#close()
	 */
	public void close() throws IOException
	{
		stream.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#mark(int)
	 */
	public synchronized void mark(int readlimit)
	{
		stream.mark(readlimit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#markSupported()
	 */
	public boolean markSupported()
	{
		return stream.markSupported();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.press.pa.ftp.stream.ObservableStream#observe(int)
	 */
	public int observe(int bytes)
	{
		if (observer != null && bytes != -1)
		{
			totalBytes += bytes;
			observer.bytesRead(totalBytes, clientId);
		}
		log.debug("Total Bytes Read = [" + totalBytes + "]");

		return bytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException
	{
		return observe(stream.read());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#read(byte[])
	 */
	public int read(byte[] b) throws IOException
	{
		return observe(stream.read(b));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(byte[] b, int off, int len) throws IOException
	{
		return observe(stream.read(b, off, len));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#reset()
	 */
	public synchronized void reset() throws IOException
	{
		totalBytes = 0;
		stream.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.InputStream#skip(long)
	 */
	public long skip(long n) throws IOException
	{
		return stream.skip(n);
	}
}
