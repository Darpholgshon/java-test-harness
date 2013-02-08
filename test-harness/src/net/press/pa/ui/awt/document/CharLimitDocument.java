package net.press.pa.ui.awt.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CharLimitDocument extends PlainDocument
{
	private static final long serialVersionUID = 3921471948676677362L;

	private int maxLength;

	public CharLimitDocument()
	{
	}

	public CharLimitDocument(int maxLength)
	{
		this.maxLength = maxLength;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException
	{
		if (str != null && maxLength != 0)
		{
			int currentLength = getLength();

			if (maxLength <= str.length() + currentLength)
			{
				str = str.substring(0, maxLength - currentLength);
			}
		}
		super.insertString(offs, str, a);
	}
}