package org.hodgson.development.util;

public class StringUtils
{
	public static transient final String wui_id = "@(#) $Archive: /e5.1/com/cedar/e5wui/util/StringUtils.java $ $Revision: 1.2 $";

	public static final char CONTROL_1 = '\u00EE';
	public static final char CONTROL_2 = '~';
	public static final char CONTROL_3 = '&';

	public static String changeSlashes(String literal)
	{
		return literal = literal.replace('\\', '/');
	}

	public static boolean containsAccelerator(String text)
	{
		if (text == null)
		{
			return false;
		}

		if (text.indexOf(CONTROL_1) != -1 || text.indexOf(CONTROL_2) != -1
				|| text.indexOf(CONTROL_3) != -1)
		{
			return true;
		}
		return false;
	}

	public static String convertToBrowserLiteral(String literal)
	{
		literal = changeSlashes(literal);

		return XMLUtilities.removeInvalidChars(literal);
	}

	public static void getPaddedChars(String s, char[] c)
	{
		int i = Math.min(s.length(), c.length);
		s.getChars(0, i, c, 0);
		for (; i < c.length; i++)
		{
			c[i] = ' ';
		}
	}

	public static int indexOfControl(String text)
	{
		int charAt = 0;

		charAt = text.lastIndexOf(CONTROL_1);

		if (charAt == -1)
		{
			charAt = text.lastIndexOf(CONTROL_2);

			if (charAt == -1)
			{
				charAt = text.lastIndexOf(CONTROL_3);
			}
		}

		return charAt;
	}

	public static boolean isNullOrEmpty(String text)
	{
		if (text == null)
		{
			return true;
		}
		return text.length() == 0;
	}

	public static String padToMax(String pValue, int padLength)
	{
		String text = "";

		if (pValue != null)
		{
			text = pValue;
		}

		while (text.length() < padLength)
		{
			text += " ";
		}
		return text;
	}

	public static boolean parseBoolean(String text)
	{
		if (text == null)
		{
			return false;
		}
		return text.equalsIgnoreCase("TRUE") || text.equalsIgnoreCase("Y");
	}

	public static String prePadZeros(int number, int padLength)
	{
		String text = String.valueOf(number);

		while (text.length() < padLength)
		{
			text = "0" + text;
		}
		return text;
	}

	public static void printHex(byte[] b)
	{
		for (int i = 0; i < b.length; ++i)
		{
			if (i % 16 == 0)
			{
				System.out.print(Integer.toHexString(i & 0xFFFF | 0x10000)
						.substring(1, 5)
						+ " - ");
			}
			System.out.print(Integer.toHexString(b[i] & 0xFF | 0x100)
					.substring(1, 3)
					+ " ");
			if (i % 16 == 15 || i == b.length - 1)
			{
				int j;
				for (j = 16 - i % 16; j > 1; --j)
				{
					System.out.print("   ");
				}
				System.out.print(" - ");
				int start = i / 16 * 16;
				int end = b.length < i + 1 ? b.length : i + 1;

				for (j = start; j < end; ++j)
				{
					if (b[j] >= 32 && b[j] <= 126)
					{
						System.out.print((char) b[j]);
					}
					else
					{
						System.out.print(".");
					}
				}
				System.out.println();
			}
		}
		System.out.println();
	}

	public static String removeAccelerator(String text)
	{
		String modifiedText = text;

		// Search for ~
		int location = text.lastIndexOf(CONTROL_2);

		// If not found then search for hex EE
		if (location < 0)
		{
			location = text.lastIndexOf(CONTROL_1);
		}

		// If not found then search for '&'
		if (location < 0)
		{
			location = text.lastIndexOf(CONTROL_3);
		}

		if (location >= 0)
		{
			modifiedText = text.substring(0, location)
					+ text.substring(location + 1);
		}
		return modifiedText;
	}

	public static String trimNullSquares(String s)
	{
		if (s != null && s.indexOf('\u0000') >= 0)
		{
			s = s.substring(0, s.indexOf('\u0000'));
		}
		return s;
	}

	public static String trimRight(String s)
	{
		int i = s.length();
		while (i > 0 && s.charAt(i - 1) == ' ')
		{
			i--;
		}

		return s.substring(0, i);
	}
}
