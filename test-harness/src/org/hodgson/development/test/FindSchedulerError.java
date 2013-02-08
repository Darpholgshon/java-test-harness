package org.hodgson.development.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class FindSchedulerError
{
	
	
	public static void main(String[] args) 
		throws IOException
	{
		InputStream is = FindSchedulerError.class.getResourceAsStream("crontabjob.log");	
		BufferedReader br = new BufferedReader(new InputStreamReader(is) );
		
		String line = null, duplicated = null;
		int count = 1;
		
		while ( (line = br.readLine() ) != null)
		{
			if ( line.indexOf("Checking for crontab jobs:") != -1)
			{
				String datePart = line.substring( line.indexOf(':') + 1, line.lastIndexOf(" =====") ).trim();
				
				if (datePart.equals(duplicated) )
				{
					count++;
				}
				else if (count > 1)
				{
					System.out.println("Executed " + count + " times at: " + duplicated);
					count = 1;
				}
				duplicated = datePart;
			}
		}
	}
}
