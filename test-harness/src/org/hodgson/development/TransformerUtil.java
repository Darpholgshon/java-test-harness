package org.hodgson.development;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TransformerUtil
{
	public static void main(String[] args)
	{
		StringWriter sw = new StringWriter();
		try
		{
			File f1 = new File("xml/content.xml");
			File f2 = new File("xsl/template.xsl");
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Source xmlSource = new StreamSource(f1);
			Source xslSource = new StreamSource(f2);

			Transformer t = tFactory.newTransformer(xslSource);
			t.transform(xmlSource, new StreamResult(sw));

			System.out.println(sw);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
