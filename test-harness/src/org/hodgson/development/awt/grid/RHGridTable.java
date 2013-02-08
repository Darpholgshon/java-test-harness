package org.hodgson.development.awt.grid;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.hodgson.development.util.Constants;

public class RHGridTable extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RHGridModel mModel;
	RHGridScrollPane mScrollPane;

	public RHGridTable()
	{
		super();

		setModel(mModel = new RHGridModel());

		TableColumn tc;

		for (int i = 0; i < mModel.getColumnHeadings().length; i++)
		{
			tc = new TableColumn();
			tc.setHeaderValue(mModel.getColumnHeading(i));
			tc.setMinWidth(80);
			addColumn(i, tc);
		}

		setCellSelectionEnabled(true);
		setRowHeight(Constants.CELL_HEIGHT);
		setGridColor(Color.black);

		getTableHeader().setBackground(Constants.LIST_HEADING_COLOR);
		setPreferredScrollableViewportSize(new Dimension(300, 190));
		configureEnclosingScrollPane();
	}

	public void addColumn(int index, TableColumn column)
	{
		addColumn(column);
		mModel.addColumn(index, column);
	}

	public int getRowCount()
	{
		if (mModel == null)
		{
			return super.getRowCount();
		}
		else
		{
			return mModel.getRowCount();
		}
	}

	public Object getValueAt(int row, int col)
	{
		return mModel.getValueAt(row, col);
	}

	public void initData()
	{
		mModel.initData();
	}

	public void setScrollPane(RHGridScrollPane scrollpane)
	{
		mScrollPane = scrollpane;
		mModel.setScrollPane(scrollpane);
	}

	public void setValueAt(Object data, int row, int col)
	{
		mModel.setValueAt(data, row, col);
	}

}