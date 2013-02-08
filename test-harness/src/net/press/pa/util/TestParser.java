package net.press.pa.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;

public class TestParser //extends AbstractFeedParser 
{
	private DefaultHandler handler = new DefaultHandler()
	{
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
		}
	};
	
	public void parse(InputStream inputStream) 
		throws Exception
	{
		InputSource source = new InputSource();
		source.setByteStream(inputStream);
					
		SAXParser parser = new SAXParser();
		parser.setContentHandler(handler);
		parser.setErrorHandler(handler);
		
		try
		{
			parser.parse(source);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
		
	public static void main(String[] args) throws Exception
	{
		URL url = new URL("http://pahaeapp01:8080/people-api/person/1");
		BufferedReader br = new BufferedReader( new InputStreamReader ( url.openStream() ) );
		
		String s = br.readLine();
		
		while (s != null)
		{
			System.out.println(s);
			s = br.readLine();
		}
		br.close();
	}
}


