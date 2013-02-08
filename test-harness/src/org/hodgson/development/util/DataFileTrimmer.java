package org.hodgson.development.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DataFileTrimmer
{
	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			usage();
			return;
		}

		File dataFile = new File(args[0]);
		File directory = dataFile.getParentFile();
		File trimmed = new File(directory, "data-trimmed.xml");

		try
		{
			if (dataFile.isDirectory() == false)
			{
				FileInputStream fin = new FileInputStream(dataFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fin));

				FileWriter fw = new FileWriter(trimmed);
				String line = br.readLine();

				while (line != null)
				{
					boolean open = false;
					StringTokenizer tokens = new StringTokenizer(line, "\"");

					while (tokens.hasMoreTokens())
					{
						String token = tokens.nextToken();

						if (open)
						{
							token = "\"" + token.trim() + "\"";
						}
						fw.write(token);
						open = !open;
					}
					fw.write("\n");
					line = br.readLine();
				}
				fin.close();
				fw.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void usage()
	{
		System.out
				.println("java org.hodgson.development.DataFileTrimmer <filename>");
		System.out.println();
		System.out
				.println("eg. java org.hodgson.development.DataFileTrimmer c:/temp/data.xml");

	}
}
