package org.hodgson.development.util;

import java.awt.image.RGBImageFilter;

// class to gray out images
public class GrayFilter extends RGBImageFilter
{
	public static transient final String wui_id = "@(#) $Archive: /cedar/wui5.1/com/cedar/e5wui/util/GrayFilter.java $ $Revision: 1.2 $";

	public GrayFilter()
	{
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb)
	{
		int a = rgb & 0xFF000000;
		int r = (rgb & 0xFF0000) >> 16;
		int g = (rgb & 0x00FF00) >> 8;
		int b = rgb & 0x0000FF;

		int c = (r + g + b) / 3; // Made a gray version
		c += (0xC0 - c) / 2; // Make it more light grayish
		int res = (c << 16) + (c << 8) + c;

		return a | res;
	}
}
