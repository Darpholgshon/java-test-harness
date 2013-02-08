package net.press.pa.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PerlImageHandler extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -29347293651145509L;

	public PerlImageHandler()
	{
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void go()
	{
		String link = "http://aperture.howden.press.net/php/resize_image.php?image=Spiderman3_Dunst1.jpg&width=300";

		try
		{
			URL url = new URL(link);
			ImageIcon ii = new ImageIcon(url);
			getContentPane().add( new JLabel(ii) );
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return;
		}
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		PerlImageHandler pih = new PerlImageHandler();
		pih.go();
	}
}
