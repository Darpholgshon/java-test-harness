package org.hodgson.development.images;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.ImageIcon;

public class RHImageLoader
{
	private static Map sImages = new HashMap();

	private static final RHImageLoader sRHImageLoader = new RHImageLoader();

	public synchronized static ImageIcon getImageIcon(String name)
	{
		if (name == null)
		{
			return null;
		}

		try
		{
			String key = name;
			ImageIcon imageIcon = (ImageIcon) sImages.get(key);

			if (imageIcon == null)
			{
				// Try and load the image and place in the set

				imageIcon = new ImageIcon(sRHImageLoader.getClass()
						.getResource(key));
				Image i = imageIcon.getImage();
				imageIcon = new ImageIcon(i);
				imageIcon.setDescription(key);
				sImages.put(key, imageIcon);
			}
			return imageIcon;
		}
		catch (Throwable t)
		{
			throw new RuntimeException("Unable to load image " + name);
		}
	}

	public static Collection getLoadedImages()
	{
		return sImages.values();
	}

	public static Vector getThumbNails()
	{
		Vector v = new Vector();

		preloadAll();

		Set keyset = sImages.keySet();

		Object[] keys = keyset.toArray();

		// Order by name.
		Arrays.sort(keys);

		for (int i = 0; i < keys.length; i++)
		{
			ImageIcon unscaledImage = (ImageIcon) sImages.get(keys[i]);
			Image img = unscaledImage.getImage().getScaledInstance(24, 24,
					Image.SCALE_DEFAULT);
			ImageIcon scaledImage = new ImageIcon(img);
			scaledImage.setDescription(unscaledImage.getDescription());

			v.addElement(scaledImage);
		}
		return v;
	}

	public static void preloadAll()
	{
		try
		{
			CodeSource cs = RHImageLoader.class.getProtectionDomain()
					.getCodeSource();
			File codeSource = new File(URLDecoder.decode(cs.getLocation()
					.getFile(), "UTF-8"));

			String desiredPackage = RHImageLoader.class.getPackage().getName()
					.replace('.', '/');
			Enumeration contents = new JarFile(codeSource).entries();

			while (contents.hasMoreElements())
			{
				JarEntry je = (JarEntry) contents.nextElement();

				String pkg = je.getName().substring(0,
						je.getName().lastIndexOf("/"));
				String fname = je.getName().substring(
						je.getName().lastIndexOf("/") + 1);

				if (je.getName().endsWith(".gif") && pkg.equals(desiredPackage))
				{
					RHImageLoader.getImageIcon(fname);
				}
			}
		}
		catch (Exception e)
		{
			try
			{
				// System.out.println("Mustn't have a jar.");
				URL dirURL = RHImageLoader.class.getResource(".");
				File[] files = new File(URLDecoder.decode(dirURL.getFile(),
						"UTF-8")).listFiles();

				for (int i = 0; i < files.length; i++)
				{
					String fname = files[i].getName();

					if (fname.endsWith(".gif"))
					{
						RHImageLoader.getImageIcon(fname);
					}
				}
			}
			catch (Exception e2)
			{
				// TODO: handle exception
			}
		}
	}
}