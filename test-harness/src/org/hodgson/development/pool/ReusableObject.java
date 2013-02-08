package org.hodgson.development.pool;

import java.util.Properties;
import java.util.prefs.Preferences;

public interface ReusableObject
{
	public void destroy();

	public void expireLease();

	public long getLastUse();

	public String getName();

	public Preferences getNode();

	public boolean inUse();

	public boolean isValid();

	public boolean lease();

	public void populate(Preferences node);

	public void populate(Properties props);

	public void setObjectPool(ReusableObjectPool pool);
}