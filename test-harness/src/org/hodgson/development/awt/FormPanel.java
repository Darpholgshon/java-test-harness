/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2006, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.awt;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.hodgson.development.awt.border.FormPanelBorder;
import org.hodgson.development.util.E5Actions;

public class FormPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 3014890432947867437L;

	private CardLayout layout;

	private int currentItem = 0;

	public FormPanel(String title)
	{
		super();
		setBorder(new FormPanelBorder(this, title));
		setLayout(layout = new CardLayout());
	}

	public void actionPerformed(ActionEvent e)
	{
		if (Integer.parseInt(e.getActionCommand()) == E5Actions.FORWARD)
		{
			currentItem++;
		}
		else if (Integer.parseInt(e.getActionCommand()) == E5Actions.BACKWARD)
		{
			currentItem--;
		}
		changePanel();
	}

	public void addPanel(Component c)
	{
		currentItem = getComponentCount() + 1;
		add(c, String.valueOf(currentItem));
		changePanel();
	}

	private void changePanel()
	{
		if (currentItem > getComponentCount())
		{
			currentItem = getComponentCount();
		}
		layout.show(this, String.valueOf(currentItem));
		invalidate();
		validate();
		repaint();
	}

	/**
	 * @return Returns the curItem.
	 */
	public int getCurItem()
	{
		return currentItem;
	}

	/**
	 * @return Returns the maxItem.
	 */
	public int getMaxItem()
	{
		return getComponentCount();
	}

	public void removePanel(Component c)
	{
		remove(c);
		changePanel();
	}

	public void selectPage(int index)
	{
		currentItem = index;
		changePanel();
	}
}