package test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestRuntimeExec implements FileFilter
{
	public static void main(String[] args)
	{
		TestRuntimeExec tre = new TestRuntimeExec();
		tre.setup();
		tre.searchFor(args[0]);
	}

	private String extension;

	private File root;

	private List matchedFiles = new ArrayList();

	public boolean accept(File pathname)
	{
		return pathname.isDirectory() || isApplication(pathname);
	}

	private void findFile(File file, String filename)
	{
		if (file.isDirectory() && isApplication(file) == false)
		{
			// System.out.println("Checking Directory: " + file);
			File[] files = file.listFiles(this);

			if (files != null)
			{
				for (int i = 0; i < files.length; i++)
				{
					findFile(files[i], filename);
				}
			}
		}
		else if (file.getName().toLowerCase().indexOf(filename) != -1)
		{
			matchedFiles.add(file);
		}
	}

	public boolean isApplication(File f)
	{
		return f.getName().toLowerCase().endsWith(extension);
	}

	public void searchFor(String filename)
	{
		long start = System.currentTimeMillis();

		findFile(root, filename.toLowerCase());

		long stop = System.currentTimeMillis();

		System.out.println("Time Taken: " + (stop - start) / 1000 + "sec");
		System.out.println("Matches Found: " + matchedFiles.size());

		Iterator matches = matchedFiles.iterator();

		while (matches.hasNext())
		{
			System.out.println(matches.next());
		}
	}

	public void setup()
	{
		String os = System.getProperty("os.name");

		if (os.startsWith("Windows"))
		{
			System.out.println("Matching .exe on " + os);
			extension = ".exe";
			root = new File("C:\\Program Files");
		}
		else if (os.startsWith("Mac"))
		{
			System.out.println("Matching .app on " + os);
			File[] roots = File.listRoots();
			extension = ".app";
			root = new File(roots[0], "Applications");
		}
		else
		{
			System.err.println("Unknown Platform:" + os);
			System.exit(1);
		}
	}
}
