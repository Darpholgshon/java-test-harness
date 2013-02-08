package org.hodgson.development.images;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class ImageFactoryTest extends TestCase
{
	/**
	 * Test method for {@link org.hodgson.development.images.ImageFactory#loadImageFromURL(java.net.URL)}.
	 */
	public void testLoadImageFromURL()
	{
		URL url = null;
		
		try
		{
			url = new URL("http://localhost/images/hodgson.gif");	
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			fail("Failed to create URL: " + e.getMessage() );
		}
		Image image = ImageFactory.loadImageFromURL(url);
		assertNotNull(image);
		assertFalse(image.getWidth(null) == -1);
	}
	
	/**
	 * Test method for {@link org.hodgson.development.images.ImageFactory#loadGIF(java.lang.String)}.
	 */
	public void testLoadGIF()
	{
		Image image = ImageFactory.loadGIF("hodgson");
		assertNotNull(image);
		assertFalse(image.getWidth(null) == -1);
	}
	
	/**
	 * Test method for {@link org.hodgson.development.images.ImageFactory#loadJPG(java.lang.String)}.
	 */
	public void testLoadJPEG()
	{
		Image image = ImageFactory.loadJPG("hodgson");
		assertNotNull(image);
		assertFalse(image.getWidth(null) == -1);
	}
	
	/**
	 * Test method for {@link org.hodgson.development.images.ImageFactory#loadPNG(java.lang.String)}.
	 */
	public void testLoadPNG()
	{
		Image image = ImageFactory.loadPNG("hodgson");
		assertNotNull(image);
		assertFalse(image.getWidth(null) == -1);
	}
	
	/**
	 * Test method for {@link org.hodgson.development.images.ImageFactory#loadImage(java.lang.String, int)}.
	 */
	public void testLoadImage()
	{
		Image image1 = ImageFactory.loadImage("hodgson", ImageFactory.PNG);
		assertNotNull(image1);
		assertFalse(image1.getWidth(null) == -1);
		
		Image image2 = ImageFactory.loadImage("hodgson", ImageFactory.JPG);
		assertNotNull(image2);
		assertFalse(image2.getWidth(null) == -1);
		
		Image image3 = ImageFactory.loadImage("hodgson", ImageFactory.GIF);
		assertNotNull(image3);
		assertFalse(image3.getWidth(null) == -1);
	}
}