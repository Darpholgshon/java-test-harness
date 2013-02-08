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

import java.util.prefs.Preferences;

import javax.swing.JPanel;

/**
 * @author <a href="mailto:Ralph.Hodgson@arelon.com">Ralph Hodgson
 */
public abstract class E5OptionPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9114476104975868066L;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 *            The name that appears on the tab panel in the E5OptionsDialog.
	 */
	public E5OptionPanel(String name)
	{
		super();

		setName(name);
	}

	public abstract void addOptionListener(E5OptionListener listener);

	public abstract boolean loadPreferences(Preferences p);

	public abstract boolean savePreferences(Preferences p);
}
