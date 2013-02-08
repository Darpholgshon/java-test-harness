package org.hodgson.development.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * XMLUtilities helper functions
 * 
 * @author Ralph Hodgson
 */
public class XMLUtilities
{
	public static void endofTag(StringWriter xml, String tag)
	{
		xml.write("</" + tag + ">\n");
	}

	public static String readDodgyXML(String filename) throws IOException
	{
		InputStream in = new FileInputStream(new File(filename));
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line = br.readLine();

		// Ignore first line and replace it with the utf-8 one.
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

		while ((line = br.readLine()) != null)
		{
			byte[] b = line.getBytes();

			for (int i = 0; i < b.length; i++)
			{
				if (b[i] != 0)
				{
					sb.append((char) b[i]);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String readUTF16(String filename)
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException
	{
		System.out.println("readUTF16()");
		File f = new File(filename);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		// InputSource is = new InputSource( f.toURI().toString() );
		// is.setEncoding("utf-16");

		Document doc = db.parse(f.toURI().toString());

		StringWriter writer = new StringWriter();

		// serialise to console using jaxp
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource ds = new DOMSource(doc);

		StreamResult res = new StreamResult(writer);
		t.transform(ds, res);

		return writer.toString();
	}// end method

	/**
	 * Removes or reformats invalid characters in a string which will be the node value of and XML node, or attribute.
	 * After doing this, remove any invalid unicode characters
	 * 
	 * @param sInput
	 *            some string input
	 * 
	 * @return the input without any invalid chars.
	 */
	public static String removeInvalidChars(String sInput)
	{
		String sReturn = "";

		if (sInput != null)
		{
			StringBuffer sbInput = new StringBuffer(sInput);

			for (int i = 0; i < sbInput.length(); i++)
			{
				char c = sbInput.charAt(i);

				if (!Character.isISOControl(c))
				{
					if (c == '&')
					{
						sReturn += "&amp;";
					}
					else if (c == '<')
					{
						sReturn += "&lt;";
					}
					else if (c == '>')
					{
						sReturn += "&gt;";
					}
					else if (c == '\"')
					{
						sReturn += "&quot;";
					}
					else if (c == '\'')
					{
						sReturn += "&apos;";
					}
					else
					{
						sReturn += c;
					}
				}
				else
				{
					if (c == '\r' || c == '\n')
					{
						sReturn += c;
					}
				}
			}
		}

		return sReturn;
	}

	/**
	 * Start a tag and append its attributes.
	 * 
	 */
	public static void startTag(StringWriter xml, String tag,
			Hashtable attributes)
	{
		xml.write("<" + tag);

		if (attributes != null)
		{
			Enumeration keys = attributes.keys();

			while (keys.hasMoreElements())
			{
				String key = keys.nextElement().toString();
				String value = (String) attributes.get(key);

				xml.write(" " + key + "='" + removeInvalidChars(value) + "'");
			}
		}
		xml.write(">\n");
	}

	/**
	 * Description - Used to write a string to be used in xml. Removes invalid char.
	 * 
	 * @deprecated - Either use removeInvalidChars or the write method above.
	 * 
	 * @param sInput
	 *            input string
	 * 
	 * @return string without invalid chars.
	 */
	public static String write(String sInput)
	{
		return removeInvalidChars(sInput);
	}

	/**
	 * Description - Generate a single line of xml.
	 * 
	 * 
	 * @param xml
	 *            the stringwriter to write to
	 * @param s
	 *            the data
	 * @param tag
	 *            the xml tag.
	 */
	public static final void writeTag(StringWriter xml, String s, String tag)
	{
		String temp1 = removeInvalidChars(s);
		String temp2 = removeInvalidChars(tag);
		xml.write("<" + temp2 + ">" + temp1 + "</" + temp2 + ">\n");
	}

	/**
	 * Description - Generate a single line of xml.
	 * 
	 * @param xml
	 *            the stringwriter to write to
	 * @param s
	 *            the data
	 * @param tag
	 *            the xml tag.
	 */
	public static final void writeTag(StringWriter xml, String s, String tag,
			Hashtable attributes)
	{
		String temp = removeInvalidChars(s);

		startTag(xml, tag, attributes);
		xml.write(temp);
		endofTag(xml, tag);
	}

	public static void writeUTF16(String filename, Document xmlData)
			throws ParserConfigurationException, IOException,
			TransformerException
	{
		System.out.println("main()");
		File f = new File(filename);
		Writer fw = new FileWriter(f);
		Writer bw = new BufferedWriter(fw);
		Writer pw = new PrintWriter(bw);

		// new DOMSource instance
		DOMSource ds = new DOMSource(xmlData);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();

		// does something when output to the console anyway...
		t.setOutputProperty("encoding", "utf-16le");
		t.setOutputProperty(OutputKeys.INDENT, "yes");

		Properties p = t.getOutputProperties();
		p.list(System.out);

		StreamResult res = new StreamResult(pw);

		t.transform(ds, res);
	}
}