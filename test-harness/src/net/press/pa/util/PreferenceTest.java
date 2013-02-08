package net.press.pa.util;

import java.util.prefs.Preferences;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class PreferenceTest
{
    public static void main(String[] args)
    {
    	//Preferences.userRoot().node("ralph_test").put("URL", "http://");
    	
    	System.out.println( Preferences.userRoot().node("ralph_test").get("URL", "") );
    	
    	
    }
}
