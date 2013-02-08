/*
 * CedAr E5 Portlets
 *
 * Version Number 5.0
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to CedAr Software Ltd. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2002, CedAr, Inc. All Rights Reserved.
 *
 * Created on 22-Apr-03
 *
 */
package org.hodgson.development.util;

import java.util.Arrays;

/**
 * @author <a href="mailto:Ralph.Hodgson@arelon.com">Ralph Hodgson
 */
public class RHDate
{
	private static final String[] MONTHS =
		{ "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
				"NOV", "DEC" };

	private static final int[] DAYS_IN_MONTH =
		{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private static final int DAYS_IN_YEAR = 365;

	private static final int MIN_YEAR = 1900;
	private static final int MAX_YEAR = 4000;

	public static void calculateDifference(RHDate date1, RHDate date2)
	{
		int dayCount1 = daysSinceStart(date1);
		int dayCount2 = daysSinceStart(date2);

		System.out.println("Days since 01-01-1900 for " + date1 + "="
				+ dayCount1);
		System.out.println("Days since 01-01-1900 for " + date2 + "="
				+ dayCount2);

		System.out.println("Days Difference =" + (dayCount2 - dayCount1));
	}

	private static int daysSinceStart(RHDate date)
	{
		int yearCount = date.mYear - RHDate.MIN_YEAR;
		int leapYearCount = yearCount / 4 + 1;

		int daysToStartOfCurrentYear = yearCount * RHDate.DAYS_IN_YEAR
				+ leapYearCount;
		int daysSinceStartOfCurrentYear = 0;

		for (int month = 1; month < date.mMonth; month++)
		{
			// The extra day in feb has already been accounted for in leapYearCount.
			daysSinceStartOfCurrentYear += DAYS_IN_MONTH[month - 1];
		}

		// Don't count today.
		daysSinceStartOfCurrentYear += date.mDate - 1;

		int daysSinceStart = daysToStartOfCurrentYear
				+ daysSinceStartOfCurrentYear;

		return daysSinceStart;
	}

	private static boolean isLeapYear(int year)
	{
		return year % 4 == 0;
	}

	public static final void main(String[] args)
	{
		int days1, month1, year1;

		days1 = Integer.parseInt(args[0]);

		month1 = Integer.parseInt(args[1]);

		year1 = Integer.parseInt(args[2]);

		int days2, month2, year2;

		days2 = Integer.parseInt(args[3]);

		month2 = Integer.parseInt(args[4]);

		year2 = Integer.parseInt(args[5]);

		RHDate d1 = new RHDate(days1, month1, year1);
		RHDate d2 = new RHDate(days2, month2, year2);

		calculateDifference(d1, d2);
	}

	public int mDate, mMonth, mYear;

	public RHDate(int date, int month, int year)
	{
		validateDate(date, month, year);
	}

	public RHDate(int date, String monthStr, int year)
	{
		String temp = null;

		if (monthStr.length() > 3)
		{
			temp = monthStr.substring(0, 3).toUpperCase();
		}
		else
		{
			temp = monthStr.toUpperCase();
		}

		// Returns position in array. Add one to get month
		int month = Arrays.binarySearch(MONTHS, temp) + 1;

		validateDate(date, month, year);
	}

	public String toString()
	{
		return "Date[" + (mDate > 9 ? "" : "0") + mDate + " "
				+ MONTHS[mMonth - 1] + " " + mYear + "]";
	}

	private void validateDate(int date, int month, int year)
	{
		// If year is divisble by four its a leap year.
		boolean isLeapYear = isLeapYear(year);

		if (year < MIN_YEAR || year > MAX_YEAR)
		{
			throw new NumberFormatException("Invalid year entered");
		}

		if (month <= 0 || month > MONTHS.length)
		{
			throw new NumberFormatException("Invalid month entered");
		}

		int daysInMonth = isLeapYear && month == 2 ? DAYS_IN_MONTH[month - 1] + 1
				: DAYS_IN_MONTH[month - 1];

		if (date <= 0 || date > daysInMonth)
		{
			throw new NumberFormatException("Invalid date entered");
		}

		mDate = date;
		mMonth = month;
		mYear = year;

		System.out.println("Date entered - " + (date > 9 ? "" : "0") + date
				+ " " + MONTHS[month - 1] + " " + year);
	}
	
//	public static void main(String[] args)
//	{
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
//		//sdf.setLenient(true);
//		
//		try
//		{
//			Date d = sdf.parse("08/08/2029");
//			
//			System.out.println( d );
//		}
//		catch (ParseException e)
//		{
//			e.printStackTrace();
//		}
//	}
}
