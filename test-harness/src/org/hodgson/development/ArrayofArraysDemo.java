package org.hodgson.development;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArrayofArraysDemo
{
	private static final Log log = LogFactory.getLog(ArrayofArraysDemo.class);
	
	public static void main(String[] args)
	{
		String[][] cartoons =
			{
						{ "Flintstones", "Fred", "Wilma", "Pebbles", "Dino" },
						{ "Rubbles", "Barney", "Betty", "Bam Bam" },
						{ "Jetsons", "George", "Jane", "Elroy", "Judy",
								"Rosie", "Astro" },
						{ "Scooby Doo Gang", "Scooby Doo", "Shaggy", "Velma",
								"Fred", "Daphne" } };

		for (int i = 0; i < cartoons.length; i++)
		{
			System.out.print(cartoons[i][0] + ": ");
			for (int j = 1; j < cartoons[i].length; j++)
			{
				System.out.print(cartoons[i][j] + " ");
			}
			System.out.println();
		}
		log.debug("All Done");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		System.out.println("toString");
		return super.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		System.out.println("hashCode");
		return super.hashCode();
	}
}