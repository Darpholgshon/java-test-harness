/*
 * Cedar Software
 *
 * All rights conferred by the law of copyright and by virtue of international
 * copyright conventions are reserved to Cedar, Inc. Use duplication or sale
 * of this product except as described in the licence agreement is strictly
 * prohibited. Violation may lead to prosecution.
 *
 * Copright 2005, Cedar, Inc. All Rights Reserved.
 */
package org.hodgson.development.obj;

import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.2
 * 
 *        Description -
 */
public class Item extends AbstractReusableObject implements Cloneable
{
	private int mCount;
	private String mName;

	public Item()
	{
	}

	public Item(String name, int count)
	{
		mName = name;
		mCount = count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * @return Returns the count.
	 */
	public int getCount()
	{
		return mCount;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return mName;
	}

	public void populate(Preferences node)
	{
		// TODO Auto-generated method stub

	}

	public void populate(Properties props)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param count
	 *            The count to set.
	 */
	public void setCount(int count)
	{
		mCount = count;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		mName = name;
	}

	public String toString()
	{
		return "Item [" + mName + ", " + mCount + "]";
	}
}
