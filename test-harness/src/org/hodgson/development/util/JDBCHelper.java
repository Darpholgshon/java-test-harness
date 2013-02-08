package org.hodgson.development.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JDBCHelper
{
	private static void close(PreparedStatement ps, String errorMessage)
	{
		try
		{
			ps.close();
		}
		catch (SQLException sqle)
		{
			System.err.println(errorMessage);
			sqle.printStackTrace();
		}
	}

	public static Set executeQuery(Connection con, String sql, List predicates,
			int numReturnColumns, String errorMessage)
	{
		PreparedStatement ps = null;
		Set rows = new HashSet();

		try
		{
			ps = con.prepareStatement(sql);

			for (int i = 0; i < predicates.size(); i++)
			{
				int index = i + 1;
				ps.setObject(index, predicates.get(i));
			}
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				Set row = new HashSet();

				for (int index = 1; index <= numReturnColumns; index++)
				{
					row.add(rs.getObject(index));
				}
				rows.add(row);
			}
		}
		catch (SQLException sqle)
		{
			System.err.println(errorMessage);
			sqle.printStackTrace();
		}
		finally
		{
			close(ps, errorMessage);
		}
		return rows;
	}

	public Set executeUpdate(Connection con, String sql, List predicates,
			String errorMessage)
	{
		PreparedStatement ps = null;
		Set rows = new HashSet();

		try
		{
			ps = con.prepareStatement(sql);

			for (int i = 0; i < predicates.size(); i++)
			{
				int index = i + 1;
				ps.setObject(index, predicates.get(i));
			}
			ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			System.err.println(errorMessage);
			sqle.printStackTrace();
		}
		finally
		{
			close(ps, errorMessage);
		}
		return rows;
	}
}
