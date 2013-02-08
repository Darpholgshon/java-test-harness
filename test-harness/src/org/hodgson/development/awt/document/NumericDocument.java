package org.hodgson.development.awt.document;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumericDocument extends PlainDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxLength = 0;

	public NumericDocument()
	{
		super();
	}

	public NumericDocument(int maxLength)
	{
		super();
		this.maxLength = maxLength;
	}

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException
	{
		if (maxLength > 0 && getLength() + str.length() > maxLength)
		{
			Toolkit.getDefaultToolkit().beep();
		}
		else
		{
			try
			{
				Integer.parseInt(str);
				super.insertString(offset, str, attr);
			}
			catch (NumberFormatException exp)
			{
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
