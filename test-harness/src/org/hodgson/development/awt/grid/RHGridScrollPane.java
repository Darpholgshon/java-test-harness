package org.hodgson.development.awt.grid;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.ScrollPaneConstants;

import org.hodgson.development.util.Constants;

public class RHGridScrollPane extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RHGridTable mGridTable;
	private RHGridVerticalScroll mScroll;
	private JScrollPane mScroller;

	public RHGridScrollPane(Component table)
	{
		super(new GridBagLayout());

		mScroller = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScroll = new RHGridVerticalScroll();

		setTable((RHGridTable) table);

		initPanel();

		mGridTable.setScrollPane(this);
		mScroll.setParent(this);
		mScroll.setBlockIncrement(((RHGridTable) table).getRowCount());
	}

	public void fireScrollEvent(AdjustmentEvent e)
	{
		RHGridModel model = (RHGridModel) getTable().getModel();

		model.fireScrollEvent(e);

		RepaintManager repman = new RepaintManager();
		repman.markCompletelyDirty(mGridTable);
		repman.paintDirtyRegions();
	}

	public RHGridTable getTable()
	{
		return mGridTable;
	}

	private void initPanel()
	{
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(mScroller, gbc);

		gbc.gridx = 8;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(Constants.CELL_HEIGHT, 0, 2, 0);
		gbc.fill = GridBagConstraints.VERTICAL;
		add(mScroll, gbc);
	}

	public void setTable(RHGridTable table)
	{
		mGridTable = table;
	}

	public void setVerticalInformation(int position, int lowerRange,
			int upperRange)
	{
		if (mScroll != null)
		{
			mScroll.updateValues(position, mGridTable.getRowCount(),
					lowerRange, upperRange);
		}
	}
}
