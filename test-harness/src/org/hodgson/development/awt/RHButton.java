package org.hodgson.development.awt;

import java.awt.Dimension;

import javax.swing.JButton;

public class RHButton extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RHButton(String text)
	{
		super(text);
		// setBorderPainted(false);
	}

	public Dimension getMinimumSize()
	{
		Dimension size = super.getMinimumSize();
		if (size.width < 80)
		{
			size.width = 80;
		}

		if (size.height < 25)
		{
			size.height = 25;
		}

		return size;
	}
}
