package org.hodgson.development.util;

/**
 * @author <a href="mailto:Ralph.Hodgson@pa.press.net">Ralph Hodgson
 */
public class Base64Utils
{
	// Mapping table from 6-bit nibbles to Base64 characters.
	private static char[] MAP_TO_BASE64 = new char[64];
	// Mapping table from Base64 characters to 6-bit nibbles.
	private static byte[] MAP_FROM_BASE64 = new byte[128];

	static
	{
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c++)
		{
			MAP_TO_BASE64[i++] = c;
		}
		for (char c = 'a'; c <= 'z'; c++)
		{
			MAP_TO_BASE64[i++] = c;
		}
		for (char c = '0'; c <= '9'; c++)
		{
			MAP_TO_BASE64[i++] = c;
		}
		{
			MAP_TO_BASE64[i++] = '+';
			MAP_TO_BASE64[i++] = '/';
		}
	}
	static
	{
		for (int i = 0; i < MAP_FROM_BASE64.length; i++)
		{
			MAP_FROM_BASE64[i] = -1;
		}
		for (int i = 0; i < 64; i++)
		{
			MAP_FROM_BASE64[MAP_TO_BASE64[i]] = (byte) i;
		}
	}

	/**
	 * Decodes Base64 data. No blanks or line breaks are allowed within the Base64 encoded data.
	 * 
	 * @param in
	 *            a character array containing the Base64 encoded data.
	 * @return An array containing the decoded data bytes.
	 * @throws IllegalArgumentException
	 *             if the input is not valid Base64 encoded data.
	 */
	public static byte[] decode(byte[] in)
	{
		int iLen = in.length;

		if (iLen % 4 != 0)
		{
			throw new IllegalArgumentException(
					"Length of Base64 encoded input string is not a multiple of 4.");
		}
		while (iLen > 0 && in[iLen - 1] == '=')
		{
			iLen--;
		}

		int oLen = iLen * 3 / 4;
		byte[] out = new byte[oLen];
		int ip = 0;
		int op = 0;

		while (ip < iLen)
		{
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iLen ? in[ip++] : 'A';
			int i3 = ip < iLen ? in[ip++] : 'A';

			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
			{
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			}

			int b0 = MAP_FROM_BASE64[i0];
			int b1 = MAP_FROM_BASE64[i1];
			int b2 = MAP_FROM_BASE64[i2];
			int b3 = MAP_FROM_BASE64[i3];

			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
			{
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			}
			int o0 = b0 << 2 | b1 >>> 4;
			int o1 = (b1 & 0xf) << 4 | b2 >>> 2;
			int o2 = (b2 & 3) << 6 | b3;
			out[op++] = (byte) o0;

			if (op < oLen)
			{
				out[op++] = (byte) o1;
			}

			if (op < oLen)
			{
				out[op++] = (byte) o2;
			}
		}
		return out;
	}

	/**
	 * Encodes a byte array into Base64 format. No blanks or line breaks are inserted.
	 * 
	 * @param in
	 *            an array containing the data bytes to be encoded.
	 * 
	 * @return A character array with the Base64 encoded data.
	 */
	public static String encode(byte[] in)
	{
		int iLen = in.length;
		int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
		int oLen = (iLen + 2) / 3 * 4; // output length including padding
		char[] out = new char[oLen];
		int ip = 0;
		int op = 0;

		while (ip < iLen)
		{
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iLen ? in[ip++] & 0xff : 0;
			int i2 = ip < iLen ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = (i0 & 3) << 4 | i1 >>> 4;
			int o2 = (i1 & 0xf) << 2 | i2 >>> 6;
			int o3 = i2 & 0x3F;
			out[op++] = MAP_TO_BASE64[o0];
			out[op++] = MAP_TO_BASE64[o1];
			out[op] = op < oDataLen ? MAP_TO_BASE64[o2] : '=';
			op++;
			out[op] = op < oDataLen ? MAP_TO_BASE64[o3] : '=';
			op++;
		}
		return new String(out);
	}

	public static void main(String[] args)
	{
		String s = 
			"KGxwMQpTJyg1MDApIERheXMgT2YgU3VtbWVyJwpwMgphUycoNTAwKSBEYXlzIE9mIFN1bW1lcicKcDMKYVMnR0JUQScKcDQKYShscDUKUydzcycKcDYKYWEoUydpb2R5NCcKcDcKSTEwCkkwMApJMDAKdHA4CmEu";
	
		
		// Use this to encode a string to Base 64.
		//System.out.println(Base64Utils.encode(s.getBytes()));

		// Use this to decode a string from Base 64.
		System.out.println(Base64Utils.decode(s.getBytes()));
	}
}
