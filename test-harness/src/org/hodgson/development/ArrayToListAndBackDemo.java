package org.hodgson.development;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class ArrayToListAndBackDemo
{
    public static void main(String[] args)
    {
    	String[] toons = new String[] {"Flintstones", "Scooby Doo", "Dora Explorer" };
    	ArrayList<String> toonList = new ArrayList<String>();
    	
    	for (int i = 0; i < toons.length; i++)
		{
			toonList.add(toons[i]);
		}
    	Collections.sort(toonList);
    	
    	toonList.add(0, "Pick me");
    	toons = (String[])toonList.toArray( new String[0] );
    	
    	for (Iterator iterator = toonList.iterator(); iterator.hasNext();)
		{
			System.out.println( iterator.next() );
		}
    }
}