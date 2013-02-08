package org.hodgson.development.images;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageFactory
{
	private static final Log log = LogFactory.getLog(ImageFactory.class);
	private static final Component imageObserver = new Container();
	
	public static final int JPG = 0;
	public static final int GIF = 1;
	public static final int PNG = 2;
	public static final int BMP = 3;
	
	public ImageFactory()
	{
	}
	
	public static Image loadImageFromURL(URL url)
	{
		log.info("Loading URL image " + url);
		Image image = Toolkit.getDefaultToolkit().createImage(url);
		
		try
		{
			MediaTracker tracker = new MediaTracker(imageObserver);
			tracker.addImage(image, 0);
			tracker.waitForAll();
		}
		catch (Exception e)
		{
			log.error("Failed to load image from [" + url + "]. ", e);
		}
		return image;
	}
	
	protected static Image loadBMP(String name)
	{
		log.info("Loading BMP image " + name);
		return loadImageFromURL( ImageFactory.class.getResource(name + ".bmp") );
	}
	
	protected static Image loadGIF(String name)
	{
		log.info("Loading GIF image " + name);
		return loadImageFromURL( ImageFactory.class.getResource(name + ".gif") );
	}
	
	protected static Image loadJPG(String name)
	{
		log.info("Loading JPG image " + name);
		return loadImageFromURL( ImageFactory.class.getResource(name + ".jpg") );
	}
	
	protected static Image loadPNG(String name)
	{
		log.info("Loading PNG image " + name);
		return loadImageFromURL( ImageFactory.class.getResource(name + ".png") );
	}
	
	public static Image loadImage(String name, int type)
	{
		switch(type)
		{
			case BMP:
				return loadBMP(name);
			case PNG:
				return loadPNG(name);
			case JPG: 
				return loadJPG(name);
			case GIF:
				return loadGIF(name);
			default:
				throw new UnsupportedOperationException("Not a supported image type. [" + type + "]");
		}
	}
}