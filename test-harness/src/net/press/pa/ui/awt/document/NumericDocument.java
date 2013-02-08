package net.press.pa.ui.awt.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class NumericDocument extends CharLimitDocument
{
	private static final long serialVersionUID = 8600632807856175232L;

	/**
	 * Default Constructor - Lowercase field with no limit.
	 */
	public NumericDocument()
	{
		super();
	}

	/**
	 * Description - Lowercase field with limit on maximum characters.
	 * 
	 * @param maxLength
	 */
	public NumericDocument(int maxLength)
	{
		super(maxLength);
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException
	{
		if (str != null)
		{
			for (int index = 0; index < str.length(); index++)
			{
				char c = str.charAt(index);

				if (!Character.isDigit(c))
				{
					return;
				}
			}
		}
		super.insertString(offs, str, a);
	}
}
