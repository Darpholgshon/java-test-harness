package org.hodgson.development;
public class RegexTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String a = "<Finish>&nbsp</Finish>";

		// a = a.replaceAll("&nbsp?:[^;]", "&nbsp;");
		a = a.replaceAll("&nbsp([^;])", "&nbsp;$1");
		System.out.println(a);

	}

}
