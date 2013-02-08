/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2004, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.awt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hodgson.development.images.RHImageLoader;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.1
 * 
 *        Description -
 */
public class ImageSelectorDialog extends JDialog implements ActionListener,
		ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton mSelect, mCancel, mPreview;
	private JList mImageList;
	private JLabel mNameText, mWidthText, mHeightText;
	private JPanel mDefinitionPanel;

	private ImageIcon mSelectedImageIcon;

	private int mReturnCode;

	public ImageSelectorDialog(JFrame parent, String imageDir)
	{
		super(parent, true);
		setTitle("Image Selection...");

		// ResourceBundle rb = ResourceBundle.getBundle("org.hodgson.softpaint.ui.mStrings", Locale.getDefault() );
		mDefinitionPanel = new JPanel(new GridBagLayout());
		mDefinitionPanel.setBorder(new TitledBorder("Image Definition"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(3, 3, 3, 3);
		mDefinitionPanel.add(new JLabel("Name: ", SwingConstants.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.weightx = 1.0;
		mDefinitionPanel.add(mNameText = new JLabel(""), gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		mDefinitionPanel.add(new JLabel("Width: ", SwingConstants.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mDefinitionPanel.add(mWidthText = new JLabel(""), gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mDefinitionPanel.add(new JLabel("Height: ", SwingConstants.RIGHT), gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		mDefinitionPanel.add(mHeightText = new JLabel(""), gbc);

		mImageList = new JList();
		mImageList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		mImageList.addListSelectionListener(this);

		// mSelect = new JButton( rb.getString("B01") );
		// mCancel = new JButton( rb.getString("B02") );

		mPreview = new JButton("Preview");
		mSelect = new JButton("Select");
		mCancel = new JButton("Cancel");

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(mPreview);
		p1.add(mSelect);
		p1.add(mCancel);

		mSelect.addActionListener(this);
		mCancel.addActionListener(this);
		mPreview.addActionListener(this);

		JScrollPane jsp = new JScrollPane(mImageList);

		Container cp = getContentPane();
		cp.add(mDefinitionPanel, BorderLayout.NORTH);
		cp.add(jsp, BorderLayout.CENTER);
		cp.add(p1, BorderLayout.SOUTH);

		Vector thumbs = RHImageLoader.getThumbNails();
		mImageList.setListData(thumbs);

		pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(mPreview))
		{
			ImageIcon selectedIcon = (ImageIcon) mImageList.getSelectedValue();
			String name = selectedIcon.getDescription();
			ImageIcon realImage = RHImageLoader.getImageIcon(name);

			JDialog jd = new JDialog(this);
			jd.getContentPane().add(new JLabel(realImage));
			jd.setTitle(realImage.getDescription());
			jd.pack();
			jd.setLocationRelativeTo(this);
			jd.setVisible(true);
		}
		else
		{
			if (e.getSource().equals(mPreview))
			{
				mReturnCode = JOptionPane.OK_OPTION;
			}
			else
			{
				mReturnCode = JOptionPane.CANCEL_OPTION;
			}
			setVisible(false);
		}
	}

	/**
	 * @return Returns the returnCode.
	 */
	public int getReturnCode()
	{
		return mReturnCode;
	}

	/**
	 * @return Returns the selectedImageIcon.
	 */
	public ImageIcon getSelectedImageIcon()
	{
		return mSelectedImageIcon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e)
	{
		ImageIcon thumbNail = (ImageIcon) mImageList.getSelectedValue();

		mSelectedImageIcon = RHImageLoader.getImageIcon(thumbNail
				.getDescription());

		mNameText.setText(mSelectedImageIcon.getDescription());
		mWidthText.setText("" + mSelectedImageIcon.getIconWidth());
		mHeightText.setText("" + mSelectedImageIcon.getIconHeight());
	}
}
