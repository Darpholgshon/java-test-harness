package net.press.pa.ui.awt;

import java.io.Serializable;

import javax.swing.JTextField;

import net.press.pa.ui.awt.document.LowerCaseDocument;

/**
 * Description: Identifier field.
 * 
 * @author <a href="mailto:ralph.hodgson@pa.press.net">ralphho
 */
public class IdField extends JTextField implements Serializable
{
	/** serialVersionUID - Used for serialization **/
	private static final long serialVersionUID = -6231891392854717068L;

	/**
	 * Default Constructor
	 */
	public IdField()
	{
		super();
		setDocument(new LowerCaseDocument());
	}

	/**
	 * @param maxLength
	 */
	public IdField(int maxLength)
	{
		super();
		setDocument(new LowerCaseDocument(maxLength));
	}
}