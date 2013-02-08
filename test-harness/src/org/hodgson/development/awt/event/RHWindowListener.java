package org.hodgson.development.awt.event;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RHWindowListener extends WindowAdapter
{
	Component mParent;

	public RHWindowListener(Component parent)
	{
		mParent = parent;
	}

	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
}
