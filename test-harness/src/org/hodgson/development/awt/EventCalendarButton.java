package org.hodgson.development.awt;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;

public class EventCalendarButton extends JButton
{
	/** serialVersionUID **/
	private static final long serialVersionUID = -4172094321967260744L;

	public static final int NONE = 0;
	public static final int SELECTED = 1;
	public static final int EXCLUDED = 2;

	private Date date;
	private int selection;

	public EventCalendarButton()
	{
		super();
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	public Dimension getMaximumSize()
	{
		return getPreferredSize();
	}

	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(35, 20);
	}

	/**
	 * @return the selection
	 */
	public int getSelection()
	{
		return selection;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
		setText("");

		if (date != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
	}

	/**
	 * @param selection
	 *            the selection to set
	 */
	public void setSelection(int selection)
	{
		this.selection = selection;
	}
}
