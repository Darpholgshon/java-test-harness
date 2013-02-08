package org.hodgson.development.test;

public class ThreadTest
{
	private static boolean success = true;

	public static void main(String[] args)
	{
		Thread[] threads = new Thread[5];

		for (int i = 0; i < threads.length; i++)
		{
			threads[i] = new Thread()
			{
				public void run()
				{
				}
			};
			threads[i].start();
		}

		for (int j = 0; j < threads.length; j++)
		{
			try
			{
				threads[j].join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("All Finished - " + success);

	}
}
