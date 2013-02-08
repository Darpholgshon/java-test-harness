package org.hodgson.development.test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hodgson.development.util.XMLUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UTF16Test
{
	private static Document createTestDocument()
			throws ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();

		Element element = doc.createElement("Programme");
		element.setAttribute("programmeid", "432435");
		doc.appendChild(element);

		Element title = doc.createElement("Title");
		title.appendChild(doc.createTextNode("Star Wars"));

		element.appendChild(title);

		Element director = doc.createElement("Director");
		director.appendChild(doc.createTextNode("George Lucas"));

		element.appendChild(director);

		Element year = doc.createElement("Year");
		year.appendChild(doc.createTextNode("1977"));

		element.appendChild(year);

		return doc;
	}

	public static void main(String[] args)
	{
		try
		{
			String file = System.getProperty("user.home") + File.separator
					+ "tvod" + File.separator + "test.xml";

			XMLUtilities.writeUTF16(file, createTestDocument() );

			XMLUtilities.readDodgyXML(file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
