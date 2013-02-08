package org.hodgson.development;

public class StaticSynchronized
{
	private static synchronized void doMethod1()
	{
		System.out.println("Entering Method 1");

		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e1)
		{
		}
		System.out.println("Exiting Method 1");
	}

	private static void doMethod2()
	{
		System.out.println("Entering Method 2");

		synchronized (StaticSynchronized.class)
		{
			System.out.println("Entering sync block in doMethod2()");

			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException e1)
			{
			}
			System.out.println("Exiting sync block in doMethod2()");
		}
		System.out.println("Exiting Method 2");
	}

	private static synchronized void doMethod3()
	{
		System.out.println("Entering Method 3");

		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e1)
		{
		}
		System.out.println("Exiting Method 3");
	}

	/**
	 * Description - Demonstration of synchronization.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread t1 = new Thread()
		{
			public void run()
			{
				StaticSynchronized.doMethod1();
			}
		};

		Thread t2 = new Thread()
		{
			public void run()
			{
				StaticSynchronized.doMethod2();
			}
		};

		Thread t3 = new Thread()
		{
			public void run()
			{
				StaticSynchronized.doMethod3();
			}
		};
		t1.start();
		t2.start();
		t3.start();
	}

	public StaticSynchronized()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
