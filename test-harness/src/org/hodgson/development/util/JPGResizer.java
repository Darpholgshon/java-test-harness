package org.hodgson.development.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * Description: Resizes all ".jpg" images in a directory to a maximum size of 512x512. The scaled image retains the
 * width/height ratio of the original.
 * 
 * @author <a href="mailto:ralph.hodgson@gmail.com">Ralph Hodgson
 */
public class JPGResizer
{
	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			usage();
		}

		JPGResizer resizer = new JPGResizer();
		resizer.setInputDirectory(args[0]);
		resizer.setOutputDirectory(args[1]);
		resizer.resizeAll();

	}

	private static void usage()
	{
		System.out
				.println("java org.hodgson.development.util.JPGResizer <input dir> <output_dir>");
		System.exit(-1);
	}

	private String inputDirectory, outputDirectory;

	private static final int WIDTH = 512;

	private static final int HEIGHT = 512;

	public JPGResizer()
	{
	}

	private Dimension calculateSize(Image image)
	{
		int thumbHeight = HEIGHT;
		int thumbWidth = WIDTH;
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		double imageRatio = (double) imageWidth / (double) imageHeight;

		if (thumbRatio < imageRatio)
		{
			thumbHeight = (int) (thumbWidth / imageRatio);
		}
		else
		{
			thumbWidth = (int) (thumbHeight * imageRatio);
		}
		return new Dimension(thumbWidth, thumbHeight);
	}

	private void resize(File input) throws IOException, InterruptedException
	{
		File f = new File(outputDirectory, input.getName());

		// If the file exists already no need to do anything.
		if (f.exists() == false)
		{
			f.createNewFile();

			// Load base Image.
			Image image = Toolkit.getDefaultToolkit().getImage(
					input.getAbsolutePath());
			MediaTracker mediaTracker = new MediaTracker(new Container());
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);

			// Calculate preferred thumb size.
			Dimension thumbSize = calculateSize(image);

			// Draw original image to thumb-nail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage(thumbSize.width,
					thumbSize.height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image, 0, 0, thumbSize.width,
					thumbSize.height, null);

			// Save thumb-nail image to file
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(f));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(thumbImage);
			out.close();
		}
	}

	public void resizeAll()
	{
		File[] files = new File(inputDirectory).listFiles();

		for (int i = 0; i < files.length; i++)
		{
			try
			{
				if (files[i].getName().endsWith(".jpg"))
				{
					resize(files[i]);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param inputDirectory
	 *            the inputDirectory to set
	 */
	public void setInputDirectory(String inputDirectory)
	{
		this.inputDirectory = inputDirectory;
	}

	/**
	 * @param outputDirectory
	 *            the outputDirectory to set
	 */
	public void setOutputDirectory(String outputDirectory)
	{
		this.outputDirectory = outputDirectory;

		File out = new File(this.outputDirectory);

		if (out.exists() == false)
		{
			out.mkdirs();
		}
	}
}
