package org.hodgson.development.util;

import java.io.File;
import java.io.FileFilter;

public class THFileFilter implements FileFilter
{
	private String mFilter;

	public THFileFilter()
	{
		this(".*");
	}

	public THFileFilter(String filter)
	{
		if (filter == null)
		{
			System.out.println("Invalid Filter: defaulting to *.*");
			filter = ".*";
		}

		if (filter.charAt(0) == '*')
		{
			filter = filter.substring(1, filter.length());
		}
		System.out.println("filter = " + filter);
		mFilter = filter.toUpperCase();
	}

	public boolean accept(File pathname)
	{
		if (pathname != null
				&& pathname.getName().toUpperCase().endsWith(mFilter))
		{
			return true;
		}
		return false;
	}
}
