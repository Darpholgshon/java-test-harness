/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2006, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.awt.border;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputListener;

import org.hodgson.development.awt.FormPanel;
import org.hodgson.development.images.RHImageLoader;
import org.hodgson.development.util.E5Actions;
import org.hodgson.development.util.GrayFilter;

public class FormPanelBorder extends TitledBorder implements MouseInputListener
{
	private static final long serialVersionUID = 1L;

	private static GrayFilter sFilter = new GrayFilter();

	private static Image[] sEnabledImages;
	private static Image[] sDisabledImages;

	private static Dimension sSize;

	static
	{
		ImageIcon icon1 = RHImageLoader.getImageIcon("mini-backward.gif");
		ImageIcon icon2 = RHImageLoader.getImageIcon("mini-forward.gif");
		ImageIcon icon3 = RHImageLoader.getImageIcon("mini-add.gif");
		ImageIcon icon4 = RHImageLoader.getImageIcon("mini-delete.gif");

		sSize = new Dimension(icon1.getIconWidth(), icon1.getIconHeight());

		sEnabledImages = new Image[4];
		sEnabledImages[0] = icon1.getImage();
		sEnabledImages[1] = icon2.getImage();
		sEnabledImages[2] = icon3.getImage();
		sEnabledImages[3] = icon4.getImage();

		ImageProducer producer1 = new FilteredImageSource(sEnabledImages[0]
				.getSource(), sFilter);
		ImageProducer producer2 = new FilteredImageSource(sEnabledImages[1]
				.getSource(), sFilter);
		ImageProducer producer3 = new FilteredImageSource(sEnabledImages[2]
				.getSource(), sFilter);
		ImageProducer producer4 = new FilteredImageSource(sEnabledImages[3]
				.getSource(), sFilter);

		sDisabledImages = new Image[4];
		sDisabledImages[0] = Toolkit.getDefaultToolkit().createImage(producer1);
		sDisabledImages[1] = Toolkit.getDefaultToolkit().createImage(producer2);
		sDisabledImages[2] = Toolkit.getDefaultToolkit().createImage(producer3);
		sDisabledImages[3] = Toolkit.getDefaultToolkit().createImage(producer4);
	}
	private FormPanel mParent;

	private Rectangle mUp, mDown, mAdd, mDelete;

	public FormPanelBorder(FormPanel parent, String title)
	{
		super(title);

		mParent = parent;
		parent.addMouseListener(this);
		parent.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e)
	{
		Component c = e.getComponent();

		if (c.getCursor().getType() == Cursor.WAIT_CURSOR)
		{
			return;
		}

		if (mUp != null && mUp.contains(e.getX(), e.getY()))
		{
			mParent.actionPerformed(new ActionEvent(e.getSource(),
					E5Actions.BACKWARD, String.valueOf(E5Actions.BACKWARD)));
		}
		else if (mDown != null && mDown.contains(e.getX(), e.getY()))
		{
			mParent.actionPerformed(new ActionEvent(e.getSource(),
					E5Actions.FORWARD, String.valueOf(E5Actions.FORWARD)));
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e)
	{
		Component c = e.getComponent();

		if (c.getCursor().getType() == Cursor.WAIT_CURSOR)
		{
			return;
		}

		if (mUp != null && mUp.contains(e.getX(), e.getY()) || mDown != null
				&& mDown.contains(e.getX(), e.getY()) || mDelete != null
				&& mDelete.contains(e.getX(), e.getY())
				|| mAdd.contains(e.getX(), e.getY()))
		{
			e.getComponent().setCursor(
					Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else
		{
			e.getComponent().setCursor(Cursor.getDefaultCursor());
		}
	}

	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height)
	{
		super.paintBorder(c, g, x, y, width, height);

		int pos1 = width - sSize.width * 4 - 10;
		int pos2 = width - sSize.width * 3 - 10;
		int pos3 = width - sSize.width * 2 - 10;
		int pos4 = width - sSize.width * 1 - 10;

		int iconsWidth = sSize.width * 4 + 4;

		String pageInfo = "Page " + mParent.getCurItem() + " of "
				+ mParent.getMaxItem();

		Font f = getTitleFont();
		int pageInfoLength = c.getFontMetrics(f).stringWidth(pageInfo);

		g.setColor(c.getBackground());
		g.fillRect(pos1 - pageInfoLength - 6, 0, iconsWidth + pageInfoLength
				+ 4, sSize.height);

		boolean fwd = mParent.getCurItem() < mParent.getMaxItem();
		boolean bwd = mParent.getCurItem() > 1;
		boolean del = mParent.getMaxItem() > 1;

		if (fwd)
		{
			mDown = new Rectangle(pos2, 0, sSize.width, sSize.height);
		}
		else
		{
			mDown = null;
		}

		if (bwd)
		{
			mUp = new Rectangle(pos1, 0, sSize.width, sSize.height);
		}
		else
		{
			mUp = null;
		}

		if (del)
		{
			mDelete = new Rectangle(pos3, 0, sSize.width, sSize.height);
		}
		else
		{
			mDelete = null;
		}

		mAdd = new Rectangle(pos4, 0, sSize.width, sSize.height);

		g.drawImage(bwd ? sEnabledImages[0] : sDisabledImages[0], pos1, 0, c);
		g.drawImage(fwd ? sEnabledImages[1] : sDisabledImages[1], pos2, 0, c);
		g.drawImage(del ? sEnabledImages[2] : sDisabledImages[1], pos3, 0, c);
		g.drawImage(sEnabledImages[3], pos4, 0, c);

		g.setColor(getTitleColor());
		g.setFont(getTitleFont());
		g.drawString(pageInfo, pos1 - pageInfoLength - 4, c.getFontMetrics(f)
				.getAscent());
	}
}
