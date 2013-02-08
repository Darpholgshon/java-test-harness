package org.hodgson.development.awt;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

public class RHFocusBorder extends EmptyBorder
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean mDrawFocus;

	public RHFocusBorder()
	{
		super(2, 2, 2, 2);

		mDrawFocus = false;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		if (mDrawFocus)
		{
			x = 1;
			y = height - 2;

			while (x < width)
			{
				g.drawLine(x, 1, x, 1);
				g.drawLine(x + 2, y, x + 2, y);
				x += 2;
			}

			x = width - 2;
			y = 1;

			while (y < height)
			{
				g.drawLine(1, y, 1, y);
				g.drawLine(x, y + 2, x, y + 2);
				y += 2;
			}
		}
	}

	public void setDrawFocus(boolean b)
	{
		mDrawFocus = b;
	}

}