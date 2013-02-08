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

import java.util.Vector;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.2
 * 
 *        Description -
 */
public class Person implements Cloneable
{
	private String mName;
	private Item mCounter;

	private Vector<Thing> mThings;

	public Person()
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException
	{
		Person p = (Person) super.clone();

		// Deep clone the counter
		if (mCounter != null)
		{
			p.setCounter((Item) mCounter.clone());
		}

		// deep clone the vector.
		if (mThings != null)
		{
			// Deep clone the vector and contents

			// Vector v = new Vector();
			//			
			// for (int i = 0; i < mThings.size(); i++)
			// {
			// Thing t = (Thing)mThings.elementAt(i);
			// v.addElement( t.clone() );
			// }
			// p.setThings(v);

			// Deep clone vector only.
			p.setThings((Vector<Thing>) mThings.clone());
		}
		return p;
	}

	/**
	 * @return Returns the counter.
	 */
	public Item getCounter()
	{
		return mCounter;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * @return Returns the things.
	 */
	public Vector<Thing> getThings()
	{
		return mThings;
	}

	/**
	 * @param counter
	 *            The counter to set.
	 */
	public void setCounter(Item counter)
	{
		mCounter = counter;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		mName = name;
	}

	/**
	 * @param things
	 *            The things to set.
	 */
	public void setThings(Vector<Thing> things)
	{
		mThings = things;
	}
}