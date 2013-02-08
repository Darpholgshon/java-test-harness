package org.hodgson.development.awt;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class RHTextField extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RHTextField()
	{
		super();

		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent mouse)
			{
				System.out.println("Mouse Clicked");
			}

			public void mouseEntered(MouseEvent mouse)
			{
				System.out.println("Mouse Entered");
			}

			public void mouseExited(MouseEvent mouse)
			{
				System.out.println("Mouse Exited");
			}

			public void mousePressed(MouseEvent mouse)
			{
				System.out.println("Mouse Pressed");
			}

			public void mouseReleased(MouseEvent mouse)
			{
				System.out.println("Mouse Released");
			}
		});

		addKeyListener(new KeyAdapter()
		{

			public void keyPressed(KeyEvent key)
			{
				System.out.println("Key Pressed");
			}

			public void keyReleased(KeyEvent key)
			{
				System.out.println("Key Released");
			}

			public void keyTyped(KeyEvent key)
			{
				System.out.println("Key Typed");
			}
		});

		addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent focus)
			{
				System.out.println("Focus Gained");
			}

			public void focusLost(FocusEvent focus)
			{
				System.out.println("Focus Lost");
			}
		});
	}
}