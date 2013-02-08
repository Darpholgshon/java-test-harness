package org.hodgson.development;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChoose
{
	public static void main(String args[])
	{
		final JFrame frame = new JFrame("Color Chooser Demo");
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		// set up the canvas to draw on

		final Canvas canv = new Canvas();
		canv.setSize(new Dimension(300, 300));

		// set up the color chooser and its listener

		final JColorChooser jcc = new JColorChooser(Color.blue);
		final ColorSelectionModel csm = jcc.getSelectionModel();
		csm.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{

				// retrieve the current color and draw

				Color c = csm.getSelectedColor();
				Graphics g = canv.getGraphics();
				g.setColor(c);
				g.fillOval(150, 150, 100, 100);
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add("North", jcc);
		panel.add("South", canv);

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
