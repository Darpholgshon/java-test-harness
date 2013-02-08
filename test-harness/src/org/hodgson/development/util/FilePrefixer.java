package org.hodgson.development.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class FilePrefixer
{
	private class DirectoryFileFilter implements FileFilter
	{
		private List exclusionList;

		protected DirectoryFileFilter(String baseDir, String excludeDirs)
		{
			exclusionList = new ArrayList();

			StringTokenizer st = new StringTokenizer(excludeDirs, ",");

			while (st.hasMoreTokens())
			{
				String token = st.nextToken().trim();

				if (token.equals("."))
				{
					exclusionList.add(baseDir);
				}
				else
				{
					exclusionList.add(baseDir + token);
				}
			}
		}

		public boolean accept(File pathname)
		{
			if (pathname != null && pathname.isDirectory())
			{
				boolean excluded = exclusionList.contains(pathname
						.getAbsolutePath());

				// System.out.println("Checking Exclusion of [" + pathname.getAbsolutePath() + "] - " + (excluded ?
				// "EXCLUDED" : "ACCEPTED") );
				return !excluded;
			}
			return false;
		}
	}

	private class SuffixFileFilter implements FileFilter
	{
		private String filter;

		protected SuffixFileFilter(String filter)
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
			// System.out.println("filter = " + filter);
			this.filter = filter.toUpperCase();
		}

		public boolean accept(File pathname)
		{
			if (pathname != null
					&& pathname.getName().toUpperCase().endsWith(filter))
			{
				return true;
			}
			return false;
		}
	}

	public static void main(String[] args)
	{
		FilePrefixer.renameFiles();
	}

	private static void renameFiles()
	{
		FilePrefixer fp = new FilePrefixer();
		fp.execute();
	}

	private String baseDir;
	private String prefix;

	private String suffix;

	private String excludeDirs;

	FileFilter dirFilter = null;

	FileFilter fileFilter = null;

	private int numericLength;

	private FilePrefixer()
	{
		ResourceBundle rb = ResourceBundle.getBundle(
				"org.hodgson.development.fileprefixer", Locale.getDefault());

		baseDir = rb.getString("base.directory").replace('/',
				File.separatorChar);

		if (baseDir.endsWith(File.separator) == false)
		{
			baseDir += baseDir + File.separator;
		}
		excludeDirs = rb.getString("exclude.dirs");
		prefix = rb.getString("prefix");
		suffix = rb.getString("suffix");

		try
		{
			numericLength = Integer.parseInt(rb.getString("numeric.length"));
		}
		catch (Exception e)
		{
			numericLength = 5;
		}
		dirFilter = new DirectoryFileFilter(baseDir, excludeDirs);
		fileFilter = new SuffixFileFilter(suffix);
	}

	private void execute()
	{
		File directory = new File(baseDir);

		if (directory.exists() == false)
		{
			System.out
					.println("Directory doresn't exist = [" + directory + "]");
			return;
		}
		searchAndReplaceFor(directory);
	}

	private void searchAndReplaceFor(File directory)
	{
		File[] directories = directory.listFiles(dirFilter);

		for (int i = 0; i < directories.length; i++)
		{
			searchAndReplaceFor(directories[i]);
		}
		File[] files = directory.listFiles(fileFilter);

		System.out.println("*");
		System.out.println("* MODIFYING FILES IN [" + directory + "]");
		System.out.println("*");
		for (int i = 0; i < files.length; i++)
		{
			File f1 = files[i];

			if (f1.isDirectory() == false)

			{
				String newName = prefix
						+ StringUtils.prePadZeros((i + 1), numericLength) + "."
						+ suffix;

				if (f1.getName().equals(newName) == false)
				{
					File f2 = new File(directory.getAbsolutePath()
							+ File.separator + newName);
					boolean success = f1.renameTo(f2);

					if (!success)
					{
						System.out
								.println("*******************************************************");
						System.out.println("* FAILED");
						System.out.println("* OLD NAME[" + f1.getPath() + "]");
						System.out.println("* NEW NAME[" + f2.getPath() + "] ");
						System.out.println("*");
					}
				}
				else
				{
					System.out
							.println("*******************************************************");
					System.out.println("* IGNORED [" + f1.getPath() + "]");
				}
			}
		}
	}

}