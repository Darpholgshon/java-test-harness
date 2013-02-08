package org.hodgson.development.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

class CalendarButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public static transient final String wui_id = "@(#) $Archive: /e5.1/com/cedar/e5wui/awt/SSCalendar.java $ $Revision: 1.1 $";

	private int dayInMonth = -1;

	public CalendarButton(String l)
	{
		super(l);
		setMargin(new Insets(3, 3, 3, 3));
		setBorder(new CalendarButtonBorder());
	}

	public int getDayInMonth()
	{
		return dayInMonth;
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

	public void paintComponent(Graphics g)
	{
		setBorderPainted(true);

		// Paint with the correct font.
		g.setFont(getFont());

		int strLength = getFontMetrics(getFont()).stringWidth(getText());

		// Using the scale values doesn't work.
		int width = getSize().width;
		int height = getSize().height;

		// Fill Background color.
		g.setColor(getBackground());
		g.fillRect(0, 0, width, height);

		int xCoord = width / 2 - strLength / 2;
		int yCoord = (int) (height * 0.6);

		g.setColor(getForeground());
		g.drawString(getText(), xCoord, yCoord);
	}

	public void setDayInMonth(int day)
	{
		dayInMonth = day;
	}
}

class CalendarButtonBorder extends BevelBorder
{
	private static final long serialVersionUID = 1L;

	protected static Insets borderInsets = new Insets(0, 0, 0, 0);

	public CalendarButtonBorder()
	{
		super(BevelBorder.RAISED);
	}

	public Insets getBorderInsets(Component c)
	{
		return borderInsets;
	}

	public Insets getBorderInsets(Component c, Insets newInsets)
	{
		newInsets.top = borderInsets.top;
		newInsets.left = borderInsets.left;
		newInsets.bottom = borderInsets.bottom;
		newInsets.right = borderInsets.right;

		return newInsets;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
	{
		if (c instanceof AbstractButton == false)
		{
			super.paintBorder(c, g, x, y, w, h);
			return;
		}
		AbstractButton button = (AbstractButton) c;
		ButtonModel model = button.getModel();

		if (model.isEnabled())
		{
			boolean isPressed = model.isPressed() && model.isArmed();
			boolean isDefault = button instanceof JButton
					&& ((JButton) button).isDefaultButton();

			if (isPressed && isDefault)
			{
				g.setColor(Color.BLACK); // outer 3D border
				g.drawLine(0, 0, 0, h - 1);
				g.drawLine(0, 0, w - 1, 0);

				g.setColor(Color.BLACK); // inner 3D border
				g.drawLine(1, 1, 1, h - 3);
				g.drawLine(2, 1, w - 3, 1);

				g.setColor(Color.LIGHT_GRAY); // inner 3D border
				g.drawLine(1, h - 2, w - 2, h - 2);
				g.drawLine(w - 2, 1, w - 2, h - 3);

				g.setColor(Color.WHITE); // inner drop shadow __|
				g.drawLine(0, h - 1, w - 1, h - 1);
				g.drawLine(w - 1, h - 1, w - 1, 0);
			}
			else if (isPressed)
			{
				g.setColor(Color.BLACK); // outer 3D border
				g.drawLine(0, 0, 0, h - 1);
				g.drawLine(0, 0, w - 1, 0);

				g.setColor(Color.DARK_GRAY); // inner 3D border
				g.drawLine(1, 1, 1, h - 3);
				g.drawLine(2, 1, w - 3, 1);

				g.setColor(Color.LIGHT_GRAY); // inner 3D border
				g.drawLine(1, h - 2, w - 2, h - 2);
				g.drawLine(w - 2, 1, w - 2, h - 3);

				g.setColor(Color.WHITE); // inner drop shadow __|
				g.drawLine(0, h - 1, w - 1, h - 1);
				g.drawLine(w - 1, h - 1, w - 1, 0);
			}
			else if (isDefault)
			{
				g.setColor(Color.WHITE); // outer 3D border
				g.drawLine(0, 0, 0, h - 1);
				g.drawLine(0, 0, w - 1, 0);

				g.setColor(Color.LIGHT_GRAY); // inner 3D border
				g.drawLine(1, 1, 1, h - 3);
				g.drawLine(2, 1, w - 3, 1);

				g.setColor(Color.DARK_GRAY); // inner 3D border
				g.drawLine(1, h - 2, w - 2, h - 2);
				g.drawLine(w - 2, 1, w - 2, h - 3);

				g.setColor(Color.BLACK); // inner drop shadow __|
				g.drawLine(0, h - 1, w - 1, h - 1);
				g.drawLine(w - 1, h - 1, w - 1, 0);
			}
			else
			{
				// Default
				g.setColor(Color.WHITE); // outer 3D border
				g.drawLine(0, 0, 0, h - 1);
				g.drawLine(0, 0, w - 1, 0);

				g.setColor(Color.LIGHT_GRAY); // inner 3D border
				g.drawLine(1, 1, 1, h - 3);
				g.drawLine(2, 1, w - 3, 1);

				g.setColor(Color.BLACK); // inner 3D border
				g.drawLine(1, h - 2, w - 2, h - 2);
				g.drawLine(w - 2, 1, w - 2, h - 3);

				g.setColor(Color.BLACK); // inner drop shadow __|
				g.drawLine(0, h - 1, w - 1, h - 1);
				g.drawLine(w - 1, h - 1, w - 1, 0);
			}
		}
		else
		{
			// disabled state
			g.setColor(Color.WHITE); // outer 3D border
			g.drawLine(0, 0, 0, h - 1);
			g.drawLine(0, 0, w - 1, 0);

			g.setColor(Color.LIGHT_GRAY); // inner 3D border
			g.drawLine(1, 1, 1, h - 3);
			g.drawLine(2, 1, w - 3, 1);

			g.setColor(Color.DARK_GRAY); // inner 3D border
			g.drawLine(1, h - 2, w - 2, h - 2);
			g.drawLine(w - 2, 1, w - 2, h - 3);

			g.setColor(Color.BLACK); // inner drop shadow __|
			g.drawLine(0, h - 1, w - 1, h - 1);
			g.drawLine(w - 1, h - 1, w - 1, 0);
		}
	}
}

public class EventCalendar extends JPanel
{
	/** serialVersionUID **/
	private static final long serialVersionUID = 3144392924450564596L;

	private JPanel p1, p2, p3;
	private JComboBox lMonths, lYears;
	private EventCalendarButton mButtons[];
	private GregorianCalendar cd = new GregorianCalendar();

	private static final int mStartYear = 1900;
	private static final int mNumberOfYears = 200;
	private static final int mEndYear = mStartYear + mNumberOfYears - 1;

	protected Color sButtonDefaultColor = (Color) UIManager
			.get("Button.background");

	public EventCalendar(String title)
	{
		super();

		// Construct the panels which make up the dialog
		p1 = new JPanel();
		p2 = new JPanel(new GridLayout(7, 7));

		JButton confirmButton = new JButton("OK");

		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(confirmButton, BorderLayout.SOUTH);

		// Access the date format symbols for the default locale
		DateFormatSymbols dfs = new DateFormatSymbols();

		// Build the lMonths combo box
		String[] months = dfs.getMonths();
		lMonths = new JComboBox(months);

		// Set the selected month
		lMonths.setSelectedIndex(cd.get(Calendar.MONTH));

		// Build the lYears combo box
		String[] years;
		years = new String[mNumberOfYears];

		for (int y = mStartYear; y < mEndYear; y++)
		{
			years[y - mStartYear] = new Integer(y).toString();
		}

		lYears = new JComboBox(years);

		// Set the selected year
		lYears.setSelectedIndex(cd.get(Calendar.YEAR) - mStartYear);

		// Add a listener for when the month changes
		lMonths.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (lMonths.getSelectedIndex() < 12) // Because DateFormatStrings.getMonths returns 13 entries!
				{
					cd.set(Calendar.MONTH, lMonths.getSelectedIndex());
					populateButtons();
				}
				else
				{
					lMonths.setSelectedIndex(cd.get(Calendar.MONTH));
				}
			}
		});

		// Add a listener for when the year changes
		lYears.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cd.set(Calendar.YEAR, lYears.getSelectedIndex() + mStartYear);
				populateButtons();
			}
		});

		// Add the months and years combo boxes to the dialog
		p2.add(lMonths);
		p2.add(lYears);

		// Add the seven day names
		String[] weekdays = dfs.getShortWeekdays();

		p3.add(new JLabel(weekdays[1], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[2], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[3], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[4], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[5], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[6], SwingConstants.CENTER));
		p3.add(new JLabel(weekdays[7], SwingConstants.CENTER));

		// Add buttons for each of the possible dates

		mButtons = new EventCalendarButton[42];

		for (int i = 0; i < 42; i++)
		{
			mButtons[i] = new EventCalendarButton();
			p3.add(mButtons[i]);
			mButtons[i].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				}
			});
		}
		// populate the button labels with day numbers
		populateButtons();
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
		return new Dimension(300, 250);
	}

	protected void populateButtons()
	{
		int month = lMonths.getSelectedIndex();
		int year = lYears.getSelectedIndex() + mStartYear;

		GregorianCalendar gc = new GregorianCalendar(year, month, 1);

		// Get which day of week is the first day of the month
		int firstDay = gc.get(Calendar.DAY_OF_WEEK) - 1;

		// System.out.println("isLeapYear = " + gc.isLeapYear(year) );
		int pos = 0;

		// Disable all the buttons up to the first day
		while (pos < firstDay)
		{
			mButtons[pos++].setDate(null);
		}

		int numberOfDaysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

		while (gc.get(Calendar.DAY_OF_MONTH) <= numberOfDaysInMonth)
		{
			mButtons[pos++].setDate(gc.getTime());
			gc.add(Calendar.DAY_OF_MONTH, 1);
		}

		while (pos < mButtons.length)
		{
			mButtons[pos].setDate(null);
		}
	}
}
