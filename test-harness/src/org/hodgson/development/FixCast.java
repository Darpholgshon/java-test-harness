package org.hodgson.development;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class FixCast
{
	private static Connection getConnection() throws Exception
	{
		// Load JDBC driver class.
		Class.forName("com.mysql.jdbc.Driver");

		// Get connection using URL and Identification.
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://aedb04:3306/medialisting", "tvod", "tv0d");

		return con;
	}

	public static void main(String[] args)
	{
		File f = new File(System.getProperty("user.home") + File.separator
				+ "Desktop", "cast.csv");

		BufferedReader br = null;
		Connection c = null;

		try
		{
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
		}
		catch (Exception e)
		{
			System.out.println("No file loaded. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}

		try
		{
			c = getConnection();
		}
		catch (Exception e)
		{
			System.out.println("No connection. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}

		try
		{
			String line = br.readLine();

			PreparedStatement ps = c
					.prepareStatement("insert into `cast` values (?,?,?,?)");

			int success = 0;
			int failed = 0;

			while (line != null)
			{
				try
				{
					StringTokenizer st = new StringTokenizer(line, ",");

					String programmeid = st.nextToken();
					String personid = st.nextToken();
					String characterid = st.nextToken();
					String sequenceno = st.nextToken();

					ps.setString(1, programmeid);
					ps.setString(2, (personid.equals("-1") ? null : personid));
					ps.setString(3, (characterid.equals("-1") ? null
							: characterid));
					ps.setString(4, sequenceno);
					ps.executeUpdate();
					success++;

					if (characterid.equals("-1"))
					{
						System.out.print("!");
					}
					else
					{
						System.out.print(".");
					}

					if (success % 80 == 0)
					{
						System.out.println();
					}
				}
				catch (Exception e)
				{
					failed++;
					// System.out.println("Can't insert line. [" + line + "]");
					// e.printStackTrace();
					// System.exit(-1);
				}
				line = br.readLine();
			}
			System.out
					.println("Done [" + success + "] Failed [" + failed + "]");
		}
		catch (IOException ioe)
		{
			System.out.println("Failed to read from file");
			ioe.printStackTrace();
			System.exit(-1);
		}
		catch (SQLException e)
		{
			System.out.println("Failed to prepare statement from file.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
