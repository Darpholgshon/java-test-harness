package test;

import java.sql.Time;
import java.util.Calendar;

public class TimeToLong
{
	public static void main(String[] args)
	{
		String time = String.valueOf( System.currentTimeMillis() );
		System.out.println(time + "[" + time.length() + "]");
		
		String maxi = String.valueOf( Integer.MAX_VALUE );
		System.out.println(maxi + "[" + maxi.length() + "]");
		
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);

		System.out.println(c.getTime().toString());
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 42);

		System.out.println(c.getTimeInMillis());
		System.out.println(new Time(c.getTimeInMillis()));
	}
}
