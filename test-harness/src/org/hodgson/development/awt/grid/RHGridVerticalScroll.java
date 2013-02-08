package org.hodgson.development.awt.grid;

import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;

import javax.swing.JScrollBar;

class RHGridVerticalScroll extends JScrollBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int mValue;
	private RHGridScrollPane mParent;

	public RHGridVerticalScroll()
	{
		super(Scrollbar.VERTICAL);
	}

	public void fireAdjustmentValueChanged(int id, int type, int value)
	{
		// System.out.println("fireAdjustmentValueChanged: id = " + id + ", type = " + type + ", value = " + value);

		if (!getValueIsAdjusting() && value != mValue)
		{
			mValue = value;
			mParent.fireScrollEvent(new AdjustmentEvent(this,
					AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED,
					AdjustmentEvent.TRACK, value));
		}
	}

	public void setParent(RHGridScrollPane parent)
	{
		mParent = parent;
	}

	public void setValues(int value, int visible, int minimum, int maximum)
	{

	}

	public void updateValues(int value, int visible, int minimum, int maximum)
	{
		if (value == -1)
		{
			value = 0;
		}

		// System.out.println("Update Values: Value = " + value + "\n" +
		// " Visible= " + visible + "\n" +
		// " Minimum= " + minimum + "\n" +
		// " Maximum= " + maximum + "\n" );

		super.setValues(value, visible, minimum, maximum);
	}
}