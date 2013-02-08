package org.hodgson.development.obj;

import java.util.Properties;
import java.util.prefs.Preferences;

import org.hodgson.development.pool.ReusableObject;
import org.hodgson.development.pool.ReusableObjectPool;

public abstract class AbstractReusableObject implements ReusableObject
{
	ReusableObjectPool pool;

	boolean busy = false;
	long lastUse = System.currentTimeMillis();

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public void expireLease()
	{
		busy = false;
	}

	public long getLastUse()
	{
		return lastUse;
	}

	public String getName()
	{
		return null;
	}

	public Preferences getNode()
	{
		return null;
	}

	public boolean inUse()
	{
		return busy;
	}

	public boolean isValid()
	{
		return true;
	}

	/*
	 * If idle set to busy and log lastUse and return true, else return false; (non-Javadoc)
	 * 
	 * @see org.hodgson.development.pool.ReusableObject#lease()
	 */
	public boolean lease()
	{
		boolean idle = !busy;

		if (idle)
		{
			busy = true;
			lastUse = System.currentTimeMillis();
		}
		return idle;
	}

	public abstract void populate(Preferences node);

	public abstract void populate(Properties props);

	public void setObjectPool(ReusableObjectPool pool)
	{
		this.pool = pool;
	}
}