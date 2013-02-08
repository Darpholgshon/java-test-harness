package net.press.pa.ui.awt.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class UpperCaseDocument extends CharLimitDocument
{
	private static final long serialVersionUID = 8600632807856175232L;

	/**
	 * Default Constructor - Lowercase field with no limit.
	 */
	public UpperCaseDocument()
	{
		super();
	}

	/**
	 * Description - Lowercase field with limit on maximum characters.
	 * 
	 * @param maxLength
	 */
	public UpperCaseDocument(int maxLength)
	{
		super(maxLength);
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException
	{
		if (str != null)
		{
			str = str.toUpperCase();
		}
		super.insertString(offs, str, a);
	}
}
