package org.hodgson.development.pool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReusableObjectPool
{
	private static Log log = LogFactory.getLog(ReusableObjectPool.class);

	public static final int DEFAULT_POOL_SIZE = 10;
	public static final long TIMEOUT = 60000;

	private static Hashtable sObjectPools = new Hashtable();

	public static ReusableObjectPool getObjectPool(Properties props)
	{
		ReusableObjectPool pool = (ReusableObjectPool) sObjectPools.get(props);

		if (pool == null)
		{
			pool = new ReusableObjectPool(props);

			sObjectPools.put(props, pool);
		}
		return pool;
	}

	public static ReusableObjectPool getObjectPool(String name,
			Preferences alias)
	{
		ReusableObjectPool pool = (ReusableObjectPool) sObjectPools.get(name);

		if (pool == null)
		{
			pool = new ReusableObjectPool(name, alias);

			sObjectPools.put(name, pool);
		}
		return pool;
	}

	private List reusableObjects = new ArrayList();
	private ReusableObjectReaper reaper;

	private int poolSize = DEFAULT_POOL_SIZE;
	private boolean useProperties;

	// USE FOR Preferences
	private String name;

	private Preferences preferences;

	// USE FOR Properties.
	private Properties properties;

	public ReusableObjectPool(Properties p)
	{
		reaper = new ReusableObjectReaper(this);
		reaper.start();

		properties = p;
		useProperties = true;
	}

	public ReusableObjectPool(String name, Preferences node)
	{
		reaper = new ReusableObjectReaper(this);
		reaper.start();

		this.name = name;
		preferences = node;
		useProperties = false;
	}

	public ReusableObject createObject() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{
		String className = null;
		ReusableObject o = null;

		if (useProperties)
		{
			className = properties.getProperty("reusable.className");
			o = (ReusableObject) Class.forName(className).newInstance();
			o.populate(properties);
		}
		else
		{
			className = preferences.get("reusable.className", "Dummy");
			o = (ReusableObject) Class.forName(className).newInstance();
			o.populate(preferences);
		}
		return o;
	}

	public synchronized void destroyPool()
	{
		Iterator objectList = reusableObjects.iterator();
		while (objectList.hasNext())
		{
			ReusableObject object = (ReusableObject) objectList.next();
			removeObject(object);
		}
	}

	public String getName()
	{
		return name;
	}

	public Preferences getNode()
	{
		return preferences;
	}

	public synchronized ReusableObject getObject()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException
	{
		ReusableObject o = null;

		for (int i = 0; i < reusableObjects.size(); i++)
		{
			o = (ReusableObject) reusableObjects.get(i);
			if (o.lease())
			{
				return o;
			}
		}

		if (reusableObjects.size() == poolSize)
		{
			// No more objects.
			return null;
		}
		else
		{
			log.debug("ReusableObjectPool:Increasing poolSize to "
					+ (reusableObjects.size() + 1) + ".");
		}

		o = createObject();

		if (o.isValid())
		{
			reusableObjects.add(o);
		}
		return o;
	}

	public int getPoolSize()
	{
		return poolSize;
	}

	public Properties getProperties()
	{
		return properties;
	}

	public boolean initPool() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{
		for (int i = 0; i < poolSize; i++)
		{
			log.debug("Creating " + (i + 1) + " of " + poolSize + ".");
			ReusableObject o = createObject();

			if (o.isValid())
			{
				o.setObjectPool(this);
				reusableObjects.add(o);
				log.debug("Created object " + (i + 1) + " Successfully.");
			}
		}
		log.info("Created Pool of " + reusableObjects.size()
				+ " Connections to " + name);

		return reusableObjects.size() == poolSize;
	}

	public Object pollForObject()
	{
		Object o = null;
		while (o == null)
		{
			try
			{
				o = getObject();

				// Mini sleep and try again.
				if (o == null)
				{
					Thread.sleep(5);
				}
			}
			catch (InterruptedException e1)
			{
				// Ignore Interruption exception.
			}
			catch (Exception e2)
			{
				// Anything else return null.
				return null;
			}
		}
		return o;
	}

	public synchronized void reapObjects()
	{
		long stale = System.currentTimeMillis() - TIMEOUT;
		Iterator objectList = reusableObjects.iterator();

		while (objectList != null && objectList.hasNext())
		{
			ReusableObject object = (ReusableObject) objectList.next();

			if (object.inUse() && stale > object.getLastUse()
					&& !object.isValid())
			{
				removeObject(object);
			}
		}
	}

	private synchronized void removeObject(ReusableObject object)
	{
		reusableObjects.remove(object);
	}

	public synchronized void returnObject(ReusableObject object)
	{
		object.expireLease();
	}

	public void setPoolSize(int poolSize)
	{
		this.poolSize = poolSize;
	}
}