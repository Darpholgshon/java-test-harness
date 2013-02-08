package net.press.pa.ftp.observe;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObservableOutputStream extends OutputStream implements
		ObservableStream
{
	private static Log log = LogFactory.getLog(ObservableOutputStream.class);

	private OutputStream stream;
	private ObservableStreamListener observer;
	private int clientId;
	private int totalBytes = 0;

	public ObservableOutputStream(OutputStream out,
			ObservableStreamListener observer, int clientId)
	{
		stream = out;
		this.observer = observer;
		this.clientId = clientId;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException
	{
		stream.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException
	{
		stream.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.press.pa.ftp.stream.ObservableStream#observe(int)
	 */
	public int observe(int bytes)
	{
		if (observer != null)
		{
			totalBytes += bytes;
			observer.bytesWrite(totalBytes, clientId);
		}
		log.debug("Total Bytes Written = [" + totalBytes + "]");
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte[] b) throws IOException
	{
		stream.write(b);
		observe(b.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte[] b, int off, int len) throws IOException
	{
		stream.write(b, off, len);
		observe(off);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException
	{
		stream.write(b);
		observe(1);
	}
}
