package org.hodgson.development.awt;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class RHFocusLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean mFocusable;

	RHFocusBorder mBorder;

	public RHFocusLabel(String label, boolean focusable)
	{
		super(label);

		mFocusable = focusable;

		setBorder(mBorder = new RHFocusBorder());
		mBorder.setDrawFocus(hasFocus());

		addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				mBorder.setDrawFocus(true);
				repaint();
			}

			public void focusLost(FocusEvent e)
			{
				mBorder.setDrawFocus(false);
				repaint();
			}

		});

		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				requestFocus();
			}
		});
	}

	@SuppressWarnings({"deprecation"})
    @Override
    public boolean isFocusTraversable()
	{
		return mFocusable;
	}

	public boolean isRequestFocusEnabled()
	{
		return mFocusable;
	}
}