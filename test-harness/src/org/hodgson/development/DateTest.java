package org.hodgson.development;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTest
{
	public static void main(String[] args)
	{
		Calendar c = Calendar.getInstance();
		c.set(2008, Calendar.DECEMBER, 20);
		c.setMinimalDaysInFirstWeek(5);
		c.setFirstDayOfWeek(Calendar.SATURDAY);
		
		System.out.print("Number of JAN days required in week 1 = " + c.getMinimalDaysInFirstWeek() );
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM");
		String month = ""; 
		
		for(int i = 0; i < 14; i++)
		{
			c.add(Calendar.DATE, 1);
		
			
			
			int indent = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
			
			if (indent < 0)
			{
				indent += 7;
			}
			String mTemp = sdf.format( c.getTime() ); 
			
			if ( !month.equals(mTemp) )
			{
				System.out.println();
				System.out.println();
				System.out.println(" NO ****** " + mTemp + " ******* ");
				System.out.println("      S  S  M  T  W  T  F");
				System.out.print(" " + week(c) + "  " );
				
				for (int idt = 0; idt < indent; idt++)
				{
					System.out.print( "   " );
				}
				month = mTemp;
			}
			else if (indent == 0)
			{
				System.out.println();
				System.out.print(" " + week(c) + "  ");
			}
			System.out.print( day(c) + " ");
		}
	}
		
	private static String week(Calendar c)
	{
		if ( c.get(Calendar.WEEK_OF_YEAR) < 10)
		{
			return " " + c.get(Calendar.WEEK_OF_YEAR);
		}
		return "" + c.get(Calendar.WEEK_OF_YEAR);
	}
	
	private static String day(Calendar c)
	{
		if ( c.get(Calendar.DAY_OF_MONTH) < 10)
		{
			return " " + c.get(Calendar.DAY_OF_MONTH);
		}
		return "" + c.get(Calendar.DAY_OF_MONTH);
	}
}
