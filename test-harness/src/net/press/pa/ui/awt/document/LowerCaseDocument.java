package net.press.pa.ui.awt.document;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class LowerCaseDocument extends CharLimitDocument
{
	private static final long serialVersionUID = 8600632807856175232L;

	/**
	 * Default Constructor - Lowercase field with no limit.
	 */
	public LowerCaseDocument()
	{
		super();
	}

	/**
	 * Description - Lowercase field with limit on maximum characters.
	 * 
	 * @param maxLength
	 */
	public LowerCaseDocument(int maxLength)
	{
		super(maxLength);
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException
	{
		if (str != null)
		{
			str = str.toLowerCase();
		}
		super.insertString(offs, str, a);
	}
}
