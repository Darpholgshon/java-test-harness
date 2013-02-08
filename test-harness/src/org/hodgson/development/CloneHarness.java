package org.hodgson.development;

import java.math.BigDecimal;
import java.util.Vector;

import org.hodgson.development.obj.Item;
import org.hodgson.development.obj.Person;
import org.hodgson.development.obj.Thing;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.2
 * 
 *        Description -
 */
public class CloneHarness
{
	/**
	 * Demonstrates that cloning a vector only clones the Vector object. The objects inside the vectors are the same
	 * objects.
	 * 
	 * However cloning a simple type creates a copy of that simple type.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Item ourCounter = new Item("Team Counter", 150);

		try
		{
			Vector<Thing> things = new Vector<Thing>();

			for (int i = 1; i < 5; i++)
			{
				Thing t = new Thing();
				t.setCode(String.valueOf(i));
				t.setDescription("Description of " + i);
				t.setValue(new BigDecimal(i * i));

				things.addElement(t);
			}
			Person p1 = new Person();
			p1.setName("Ken");
			p1.setThings(things);
			p1.setCounter(ourCounter);

			Person p2 = (Person) p1.clone();

			p2.setName("Ralph");
			Vector<Thing> things2 = p2.getThings();

			for (int i = 1; i < 5; i++)
			{
				Thing t = (Thing) things2.elementAt(i - 1);

				t.setCode(String.valueOf(i * 10));
				t.setDescription("Description of " + i * 10);
				t.setValue(new BigDecimal(i * i * 10));
			}
			System.out.println("Person 1:" + p1.getName());
			System.out.println(p1.getCounter());

			for (int i = 0; i < p1.getThings().size(); i++)
			{
				System.out.println(p1.getThings().elementAt(i).toString());
			}

			System.out.println("Person 2:" + p2.getName());
			System.out.println(p2.getCounter());

			for (int i = 0; i < p2.getThings().size(); i++)
			{
				System.out.println(p2.getThings().elementAt(i).toString());
			}

			ourCounter.setCount(100);

			System.out.println("Person 1:" + p1.getName());
			System.out.println(p1.getCounter());

			for (int i = 0; i < p1.getThings().size(); i++)
			{
				System.out.println(p1.getThings().elementAt(i).toString());
			}

			System.out.println("Person 2:" + p2.getName());
			System.out.println(p2.getCounter());

			for (int i = 0; i < p2.getThings().size(); i++)
			{
				System.out.println(p2.getThings().elementAt(i).toString());
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	/**
	 * 
	 */
	public CloneHarness()
	{
		super();
	}
}
