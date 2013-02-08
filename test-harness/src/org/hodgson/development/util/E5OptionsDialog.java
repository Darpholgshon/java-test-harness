/*
 * CedAr E5 Portlets
 *
 * Version Number 5.0
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to CedAr Software Ltd. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2002, CedAr, Inc. All Rights Reserved.
 *
 * Created on 24-Sep-2003
 *
 */
package org.hodgson.development.util;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * @author <a href="mailto:Ralph.Hodgson@arelon.com">Ralph Hodgson
 * 
 */
/**
 * The E5OptionsDialog is a generic dialog that has a JTabbedPane for adding implementations of E5OptionPanel to. The
 * Dialog also has an Apply, OK, and Cancel Button Panel.
 * 
 * @author <a href="mailto:Ralph.Hodgson@arelon.com">Ralph Hodgson
 */
public class E5OptionsDialog extends JDialog implements ActionListener,
		E5OptionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane mTabbedPane;
	private JButton mApply, mOK, mCancel;

	private ResourceBundle mRB;
	private Preferences mPreferences;
	private Vector mPanels;
	private boolean mIsSystem;

	/**
	 * Default Constructor.
	 */
	public E5OptionsDialog(JFrame owner, Class nodeClass, boolean isSystem)
	{
		super(owner, true);

		mRB = ResourceBundle.getBundle("org.hodgson.e5wui.options.mStrings",
				Locale.getDefault());
		mIsSystem = isSystem;

		// Determine whether the preferences are user or system.
		if (isSystem)
		{
			mPreferences = Preferences.systemNodeForPackage(nodeClass);
		}
		else
		{
			mPreferences = Preferences.userNodeForPackage(nodeClass);
		}
		mTabbedPane = new JTabbedPane(SwingConstants.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		mApply = new JButton(mRB.getString("BUTTON_01"));
		mApply.addActionListener(this);
		mOK = new JButton(mRB.getString("BUTTON_02"));
		mOK.addActionListener(this);
		mCancel = new JButton(mRB.getString("BUTTON_03"));
		mCancel.addActionListener(this);

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(mApply);
		p1.add(mOK);
		p1.add(mCancel);

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(mTabbedPane, BorderLayout.CENTER);
		cp.add(p1, BorderLayout.SOUTH);

		mPanels = new Vector();

		pack();
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (mApply.equals(ae.getSource()) || mOK.equals(ae.getSource()))
		{
			for (int i = 0; i < mPanels.size(); i++)
			{
				E5OptionPanel panel = (E5OptionPanel) mPanels.elementAt(i);

				panel.savePreferences(mPreferences);
			}
		}

		if (mCancel.equals(ae.getSource()) || mOK.equals(ae.getSource()))
		{
			setVisible(false);
		}
	}

	/**
	 * Description - Add an E5OptionPanel to the tabbed panel.
	 * 
	 * @param panel
	 */
	public void addOptionPanel(E5OptionPanel panel)
	{
		mTabbedPane.addTab(panel.getName(), panel);
		panel.loadPreferences(mPreferences);
		panel.addOptionListener(this);
	}

	/**
	 * Description - Accessor method to get the preferences.
	 * 
	 * @return mPreferences
	 */
	public Preferences getPreferences()
	{
		return mPreferences;
	}

	/**
	 * Description - Accessor method.
	 * 
	 * @return
	 */
	public boolean isSystem()
	{
		return mIsSystem;
	}

	public void preferencesChanged(boolean state)
	{
		mApply.setEnabled(state);
	}

	/**
	 * Description - If we have changed preferences, re-initialise all the panels.
	 * 
	 * @param p
	 */
	public void setPreferences(Preferences p)
	{
		mPreferences = p;

		for (int i = 0; i < mPanels.size(); i++)
		{
			E5OptionPanel panel = (E5OptionPanel) mPanels.elementAt(i);

			panel.loadPreferences(mPreferences);
		}
	}

	/**
	 * Description - Accessor method.
	 * 
	 * @param pB
	 */
	public void setSystem(boolean pB)
	{
		mIsSystem = pB;
	}
}