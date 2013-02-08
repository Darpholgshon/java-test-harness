package test;

import java.text.DateFormat;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class TestClass
{
public static void main(String[] args)
{
	System.out.println( DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(null) ); 
}
}
