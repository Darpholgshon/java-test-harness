package org.hodgson.development.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

import org.hodgson.development.images.ImageFactory;

public class RHImageCanvas extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Image mImage;

	protected Image mOffScreenImage;

	private Dimension mSize;

	private String mErrorText;

	private boolean mScalable;

	private transient boolean mDisposed = false;

	public RHImageCanvas(Image image)
	{
		this(image, false);
	}

	public RHImageCanvas(Image image, boolean scalable)
	{
		super();

		mImage = image;
		mScalable = scalable;
		int w = image.getWidth(this);
		int h = image.getHeight(this);

		mSize = null;

		if (h > 0 && w > 0)
		{
			mSize = new Dimension(w, h);
		}
		else
		{
			mScalable = true;
		}
	}

	public RHImageCanvas(String name, Dimension size, boolean scalable)
	{
		super();

		mScalable = scalable;
		mSize = size;
		if (name == null || name.length() == 0)
		{
			mErrorText = "A null name was passed to ImageCanvas";
		}
		else
		{
			URL loc = ImageFactory.class.getResource(name + ".gif");
			if (loc == null)
			{
				mImage = null;
				mErrorText = name + " not found";
				System.err.println("Warning: image " + name + " not found");
			}
			else
			{
				try
				{
					mImage = Toolkit.getDefaultToolkit().getImage(loc);
					prepareImage(mImage, this);
				}
				catch (Exception e)
				{
					System.out.println("Warning: unable to load image " + name);
					e.printStackTrace();
				}
			}
		}
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(300, 300);
	}

	public boolean imageUpdate(Image image, int infoFlgs, int x, int y,
			int width, int height)
	{
		if (mDisposed)
		{
			return false;
		}
		mOffScreenImage = null;
		if ((infoFlgs & ERROR) != 0)
		{
			mErrorText = "Error loading image";
		}
		else if ((infoFlgs & ABORT) != 0)
		{
			mErrorText = "Image was aborted before download completed";
		}
		else if ((infoFlgs & (FRAMEBITS | ALLBITS)) != 0)
		{
			if (mSize == null)
			{
				mSize = new Dimension(image.getWidth(this), image
						.getHeight(this));
			}
		}
		else
		{
			return true;
		}

		update(getGraphics());

		// Indicate we're no longer interested if we're finished or it's a bust
		return (infoFlgs & (ABORT | ALLBITS)) == 0;

	}

	public void paint(Graphics onscreenGraphics)
	{
		if (onscreenGraphics == null)
		{
			return;
		}

		int width, height;

		if (mScalable)
		{
			width = getSize().width;
			height = getSize().height;
		}
		else
		{
			width = mSize.width;
			height = mSize.height;
		}

		// What can we do if we don't have a size?
		if (width <= 0 || height <= 0)
		{
			return;
		}

		if (mOffScreenImage == null)
		{
			mOffScreenImage = createImage(width, height);
		}

		Graphics g = mOffScreenImage.getGraphics();

		if (g == null)
		{
			return;
		}

		if (mErrorText != null)
		{
			g.drawString(mErrorText, 2, getSize().height / 2);
		}
		else
		{
			if (mImage != null)
			{
				Dimension viewSize;
				if (mScalable)
				{
					viewSize = getSize();
				}
				else
				{
					viewSize = mSize;
				}
				int imageWidth = mImage.getWidth(null);
				int imageHeight = mImage.getHeight(null);

				// draw and scale the image.
				g.drawImage(mImage, 0, 0, viewSize.width, viewSize.height, 0,
						0, imageWidth, imageHeight, this);
			}
		}

		if (onscreenGraphics != null && mOffScreenImage != null)
		{
			onscreenGraphics.drawImage(mOffScreenImage, 0, 0, this);
		}
		paintChildren(onscreenGraphics);
	}

	public void paintAll(Graphics g)
	{
		paint(g);
	}

	public void removeNotify()
	{
		super.removeNotify();
		mDisposed = true;
	}

	public void setImage(Image mmm)
	{
		mImage = mmm;
		mOffScreenImage = null;
	}

	public void update(Graphics g)
	{
		paint(g);
	}

}