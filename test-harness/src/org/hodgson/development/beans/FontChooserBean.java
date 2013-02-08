/**
 * FontChooserPanel.java Ralph Hodgson
 *
 */
package org.hodgson.development.beans;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FontChooserBean extends Container implements
		PropertyChangeListener, ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] sFontSizes =
		{ "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26",
				"28", "36", "48", "72" };

	private static String[] sFontNames = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	protected Font mFont = new Font("Serif", Font.PLAIN, 10);

	protected Choice mNameSelector;
	protected Choice mSizeSelector;
	protected Checkbox mBoldSelector;
	protected Checkbox mItalicSelector;

	protected boolean mSuppressEvents;
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public FontChooserBean()
	{
		setLayout(new BorderLayout());

		add(mNameSelector = new Choice(), BorderLayout.CENTER);

		for (int i = 0; i < sFontNames.length; ++i)
		{
			mNameSelector.addItem(sFontNames[i]);
		}
		mNameSelector.select(mFont.getName());
		mNameSelector.addItemListener(this);

		add(mSizeSelector = new Choice(), BorderLayout.EAST);

		for (int i = 0; i < sFontSizes.length; ++i)
		{
			mSizeSelector.addItem(sFontSizes[i]);
		}
		mSizeSelector.select(String.valueOf(mFont.getSize()));
		mSizeSelector.addItemListener(this);

		Panel p = new Panel(new FlowLayout());
		p.add(mBoldSelector = new Checkbox("bold"));

		mBoldSelector.setState(mFont.isBold());
		mBoldSelector.setFont(new Font("TimesRoman", Font.BOLD, 12));
		mBoldSelector.addItemListener(this);

		p.add(mItalicSelector = new Checkbox("italic"));
		mItalicSelector.setState(mFont.isBold());
		mItalicSelector.setFont(new Font("TimesRoman", Font.ITALIC, 12));
		mItalicSelector.addItemListener(this);

		add(p, BorderLayout.SOUTH);
	}

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		listeners.addPropertyChangeListener(l);
	}

	protected void fireStateChange()
	{
		if (mSuppressEvents == false)
		{
			listeners.firePropertyChange("style", mFont, mFont = getStyle());
		}
	}

	public Font getStyle()
	{
		return new Font(mNameSelector.getSelectedItem(), 0
				| (mBoldSelector.getState() ? Font.BOLD : 0)
				| (mItalicSelector.getState() ? Font.ITALIC : 0), Integer
				.parseInt(mSizeSelector.getSelectedItem()));
	}

	public void itemStateChanged(ItemEvent e)
	{
		fireStateChange();
	}

	public void propertyChange(PropertyChangeEvent e)
	{
		fireStateChange();
	}

	public void removePropertyChangeListener(PropertyChangeListener l)
	{
		listeners.removePropertyChangeListener(l);
	}

	public void setStyle(Font f)
	{
		mSuppressEvents = true;
		mNameSelector.select(f.getName());
		mBoldSelector.setState(f.isBold());
		mItalicSelector.setState(f.isItalic());
		mSuppressEvents = false;
		fireStateChange();
	}
}
