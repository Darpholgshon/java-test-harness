/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2002, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.util;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.CSS;

public class AttributeChecker
{
	public static final String[] ALIGNMENT_NAMES =
		{ "left", "center", "right", "justify" };

	public static int checkAlignment(JTextPane pane)
	{
		AttributeSet attributes = pane.getParagraphAttributes();

		if (attributes.isDefined(StyleConstants.Alignment))
		{
			Integer align = (Integer) attributes
					.getAttribute(StyleConstants.Alignment);
			return align.intValue();
		}

		if (attributes.isDefined(CSS.Attribute.TEXT_ALIGN))
		{
			String s = attributes.getAttribute(CSS.Attribute.TEXT_ALIGN)
					.toString();

			for (int alignment = 0; alignment < ALIGNMENT_NAMES.length; alignment++)
			{
				if (ALIGNMENT_NAMES[alignment].equalsIgnoreCase(s))
				{
					return alignment;
				}
			}
		}
		return -1; // Not aligned.
	}

	public static void checkAttributes(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();
		Enumeration names = attributes.getAttributeNames();

		while (names.hasMoreElements())
		{
			Object key = names.nextElement();

			System.out.println("KEY[" + key + "][" + key.getClass()
					+ "] VALUE[" + attributes.getAttribute(key) + "]["
					+ attributes.getAttribute(key).getClass() + "]");
		}

		System.out.println("");
	}

	public static Color checkColour(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes.isDefined(StyleConstants.Foreground))
		{
			return (Color) attributes.getAttribute(StyleConstants.Foreground);
		}

		if (attributes.isDefined(CSS.Attribute.COLOR))
		{
			String s = attributes.getAttribute(CSS.Attribute.COLOR).toString();

			return Color.decode(s);
		}
		return Color.BLACK;
	}

	public static String checkFontFamily(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes.isDefined(StyleConstants.FontFamily))
		{
			return attributes.getAttribute(StyleConstants.FontFamily)
					.toString();
		}

		if (attributes.isDefined(CSS.Attribute.FONT_FAMILY))
		{
			return attributes.getAttribute(CSS.Attribute.FONT_FAMILY)
					.toString();
		}
		return pane.getFont().getFamily();
	}

	public static String checkFontSize(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes.isDefined(StyleConstants.FontSize))
		{
			return attributes.getAttribute(StyleConstants.FontSize).toString();
		}

		if (attributes.isDefined(CSS.Attribute.FONT_SIZE))
		{
			return attributes.getAttribute(CSS.Attribute.FONT_SIZE).toString();
		}
		return String.valueOf(pane.getFont().getSize());
	}

	public static boolean isBold(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes.containsAttribute(StyleConstants.Bold, Boolean.TRUE))
		{
			return true;
		}

		if (attributes.getAttribute(CSS.Attribute.FONT_WEIGHT) != null)
		{
			Object fontWeight = attributes
					.getAttribute(CSS.Attribute.FONT_WEIGHT);
			return fontWeight.toString().equals("bold");
		}
		return false;
	}

	public static boolean isItalic(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes.containsAttribute(StyleConstants.Italic, Boolean.TRUE))
		{
			return true;
		}

		if (attributes.getAttribute(CSS.Attribute.FONT_STYLE) != null)
		{
			Object fontWeight = attributes
					.getAttribute(CSS.Attribute.FONT_STYLE);
			return fontWeight.toString().equals("italic");
		}
		return false;
	}

	public static boolean isUnderline(JTextPane pane)
	{
		AttributeSet attributes = pane.getInputAttributes();

		if (attributes
				.containsAttribute(StyleConstants.Underline, Boolean.TRUE))
		{
			return true;
		}

		if (attributes.getAttribute(CSS.Attribute.TEXT_DECORATION) != null)
		{
			Object fontWeight = attributes
					.getAttribute(CSS.Attribute.TEXT_DECORATION);
			return fontWeight.toString().equals("underline");
		}
		return false;
	}

}
