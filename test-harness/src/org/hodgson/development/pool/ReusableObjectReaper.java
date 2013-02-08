package org.hodgson.development.pool;

public class ReusableObjectReaper extends Thread
{
	private ReusableObjectPool objectPool;
	private final long delay = 300000;

	ReusableObjectReaper(ReusableObjectPool pool)
	{
		objectPool = pool;
	}

	public void run()
	{
		while (true)
		{
			try
			{
				sleep(delay);
			}
			catch (InterruptedException e)
			{
			}
			objectPool.reapObjects();
		}
	}
}
