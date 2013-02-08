package org.hodgson.development.awt.grid;

import java.awt.event.AdjustmentEvent;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class RHGridModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String[] mColumnHeadings =
		{ "Type", "Model", "Colour" };

	private int mRows = 10;
	private int mCols = mColumnHeadings.length;

	private Object mData[][] = new Object[mRows][mCols];
	private Object mServerData[][] = new Object[mRows * 4][mCols];

	private Hashtable mColumns;

	private RHGridScrollPane mGridScroll;

	private static String[] mColours =
		{ "Red", "White", "Blue", "Black", "Brown", "Grey", "Gold", "Silver",
				"Yellow", "Green" };

	public RHGridModel()
	{
		super();

		mColumns = new Hashtable();

		setServerData();
	}

	public void addColumn(int index, TableColumn tc)
	{
		mColumns.put(new Integer(index), tc);
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Fake Scrolling Stuff. //
	// /////////////////////////////////////////////////////////////////////////////
	public void fireScrollEvent(AdjustmentEvent e)
	{
		scrollData(e.getValue());
		mGridScroll.setVerticalInformation(e.getValue(), 0, mServerData.length);
	}

	public TableColumn getColumn(int index)
	{
		return (TableColumn) mColumns.get(new Integer(index));
	}

	public int getColumnCount()
	{
		return mColumns.size();
	}

	public String getColumnHeading(int index)
	{
		return mColumnHeadings[index];
	}

	public String[] getColumnHeadings()
	{
		return mColumnHeadings;
	}

	public int getRowCount()
	{
		return mRows;
	}

	public Object getValueAt(int row, int col)
	{
		return mData[row][col];
	}

	public void initData()
	{
		for (int i = 0; i < mRows; i++)
		{
			mData[i][0] = mServerData[i][0];
			mData[i][1] = mServerData[i][1];
			mData[i][2] = mServerData[i][2];
		}
		mGridScroll.setVerticalInformation(0, 0, mServerData.length);

	}

	public boolean isCellEditable(int row, int col)
	{
		return false;
	}

	public void scrollData(int value)
	{
		for (int i = 0; i < mRows; i++)
		{
			if (i >= mServerData.length)
			{
				mData[i][0] = "";
				mData[i][1] = "";
				mData[i][2] = "";
			}
			else
			{
				mData[i][0] = mServerData[value + i][0];
				mData[i][1] = mServerData[value + i][1];
				mData[i][2] = mServerData[value + i][2];
			}
		}
	}

	public void setScrollPane(RHGridScrollPane pane)
	{
		mGridScroll = pane;
	}

	public void setServerData()
	{
		int i = 0;

		for (i = 0; i < mRows; i++)
		{
			mServerData[i][0] = "Ford";
			mServerData[i][1] = "Fiesta";
			mServerData[i][2] = mColours[i];
		}

		for (i = 0; i < mRows; i++)
		{
			mServerData[i + mRows][0] = "BMW";
			mServerData[i + mRows][1] = "520i";
			mServerData[i + mRows][2] = mColours[i];
		}

		for (i = 0; i < mRows; i++)
		{
			mServerData[i + mRows + mRows][0] = "Toyota";
			mServerData[i + mRows + mRows][1] = "Yaris";
			mServerData[i + mRows + mRows][2] = mColours[i];
		}

		for (i = 0; i < mRows; i++)
		{
			mServerData[i + mRows + mRows + mRows][0] = "Nissan";
			mServerData[i + mRows + mRows + mRows][1] = "Bluebird";
			mServerData[i + mRows + mRows + mRows][2] = mColours[i];
		}
	}

	public void setValueAt(Object data, int row, int col)
	{
		mData[row][col] = data;
	}

}