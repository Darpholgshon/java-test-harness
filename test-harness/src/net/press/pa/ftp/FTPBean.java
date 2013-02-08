package net.press.pa.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.press.pa.ftp.observe.ObservableInputStream;
import net.press.pa.ftp.observe.ObservableOutputStream;
import net.press.pa.ftp.observe.ObservableStreamListener;
import net.press.pa.ftp.observe.ObservableStreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * Description: Simple bean that extends <code>FTPClient</code> in order to implement a put and get method as per the
 * standard ftp functions.
 * 
 * The bean also implements ObservableStreamSupport so that clients can check on the progress of their upload/download.
 * 
 * @author <a href="mailto:ralph.hodgson@pa.press.net">Ralphho
 */
public class FTPBean extends FTPClient implements ObservableStreamSupport
{
	private static Log log = LogFactory.getLog(FTPBean.class);

	private int clientId;

	private File localDirectory;

	private List observers;

	public FTPBean()
	{
	}

	@SuppressWarnings("unchecked")
	public void addObserver(ObservableStreamListener observer)
	{
		if (observers == null)
		{
			observers = new ArrayList();
		}
		observers.add(observer);
	}

	public void bytesRead(int bytes, int clientId)
	{
		if (this.clientId == clientId)
		{
			for (Iterator i = observers.iterator(); i.hasNext();)
			{
				ObservableStreamListener listener = (ObservableStreamListener) i
						.next();
				listener.bytesRead(bytes, clientId);
			}
		}
	}

	public void bytesWrite(int bytes, int clientId)
	{
		if (this.clientId == clientId)
		{
			for (Iterator i = observers.iterator(); i.hasNext();)
			{
				ObservableStreamListener listener = (ObservableStreamListener) i
						.next();
				listener.bytesWrite(bytes, clientId);
			}
		}
	}

	public boolean changelocalDirectory(String dir) throws IOException
	{
		log.debug("Changing local directory to [" + dir + "]");

		localDirectory = new File(dir);

		if (!localDirectory.exists())
		{
			localDirectory.mkdirs();
		}
		return localDirectory.exists();
	}

	public boolean changeWorkingDirectory(String dir) throws IOException
	{
		boolean changed = super.changeWorkingDirectory(dir);

		if (changed)
		{
			log.debug("Remote dir now:" + printWorkingDirectory());
		}
		return changed;
	}

	public boolean connect(String hostname, String username, String password)
			throws IOException
	{
		log.debug("Connecting to [" + hostname + "]");
		connect(hostname);

		if (FTPReply.isPositiveCompletion(getReplyCode()))
		{
			log.debug("Connected: Logging on as [" + username + "]");
			return login(username, password);
		}
		else
		{
			log.debug("Connection failed.");
			return false;
		}
	}

	public boolean connect(String hostname, String username, String password,
			String remotePath) throws IOException
	{
		boolean connected = connect(hostname, username, password);

		if (connected)
		{
			connected = changeWorkingDirectory(remotePath);
		}
		return connected;
	}

	public File get(String filename) throws IOException
	{
		File outputFile = File.createTempFile("ftp", ".tmp", localDirectory);
		OutputStream out = new ObservableOutputStream(new FileOutputStream(
				outputFile), this, clientId);

		boolean success = retrieveFile(filename, out);

		if (success)
		{
			File f = new File(localDirectory, filename);
			outputFile.renameTo(f);
		}
		return outputFile;
	}

	public boolean get(String filename, StringBuffer sb) throws IOException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		boolean success = retrieveFile(filename, bout);

		if (success)
		{
			sb.setLength(0); // Make sure buffer is empty.
			sb.append(bout.toString());
		}
		return success;
	}

	/**
	 * @return the clientId
	 */
	public int getClientId()
	{
		return clientId;
	}

	public boolean lastActionSuccessfull()
	{
		return FTPReply.isPositiveCompletion(getReplyCode());
	}

	public boolean put(File file) throws IOException
	{
		InputStream fin = new ObservableInputStream(new FileInputStream(file),
				this, clientId);
		return appendFile(file.getName(), fin);
	}

	public void removeObserver(ObservableStreamListener observer)
	{
		if (observers != null && observers.contains(observer))
		{
			observers.remove(observer);
		}
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}
}