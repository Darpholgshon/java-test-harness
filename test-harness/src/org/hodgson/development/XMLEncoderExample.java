package org.hodgson.development;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import javax.swing.JFrame;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.2
 * 
 *        Description -
 */
public class XMLEncoderExample
{
	public static void main(String[] args) throws Exception
	{
		// Create the encoder.
		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream("RX1.xml")));

		// Write the XML document.
		e.writeObject(new JFrame());
		e.close();

		System.exit(0);
	}
}
