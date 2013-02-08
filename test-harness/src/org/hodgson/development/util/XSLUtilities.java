package org.hodgson.development.util;

import java.io.PrintWriter;

/**
 * @author <a href="mailto:Ralph.Hodgson@gmail.com">Ralph Hodgson
 * 
 *         Description -
 */
public class XSLUtilities
{
	public static void output(PrintWriter pw, String str, int indent)
	{
		pw.println(tabs(indent) + str);
	}

	public static void outputAttribute(PrintWriter pw, String name,
			boolean value, int indent)
	{
		pw.println(tabs(indent) + "<xsl:attribute name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:attribute>");
	}

	public static void outputAttribute(PrintWriter pw, String name, int value,
			int indent)
	{
		pw.println(tabs(indent) + "<xsl:attribute name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:attribute>");
	}

	public static void outputAttribute(PrintWriter pw, String name,
			String value, int indent)
	{
		value = XMLUtilities.removeInvalidChars(value);

		pw.println(tabs(indent) + "<xsl:attribute name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:attribute>");
	}

	public static void outputParameter(PrintWriter pw, String name,
			boolean value, int indent)
	{
		pw.println(tabs(indent) + "<xsl:with-param name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:with-param>");
	}

	public static void outputParameter(PrintWriter pw, String name, int value,
			int indent)
	{
		pw.println(tabs(indent) + "<xsl:with-param name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:with-param>");
	}

	public static void outputParameter(PrintWriter pw, String name,
			String value, int indent)
	{
		value = XMLUtilities.removeInvalidChars(value);

		pw.println(tabs(indent) + "<xsl:with-param name='" + name
				+ "'><xsl:text>" + value + "</xsl:text></xsl:with-param>");
	}

	public static String tabs(int indent)
	{
		String tabs = "";

		for (int i = 0; i < indent; i++)
		{
			tabs += "\t";
		}
		return tabs;
	}

	public XSLUtilities()
	{
	}

}
