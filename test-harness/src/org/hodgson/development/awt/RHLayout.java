package org.hodgson.development.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Title: Test Harness Description: Test Harness Project for testing awt widgets for the WUI. *
 * 
 * @author Ralph Hodgson
 * @version 1.0
 */

public class RHLayout extends Object implements LayoutManager2, Externalizable
{
	public static final long serialVersionUID = -1458228009530058099L;

	private boolean mDirty = false;
	private Hashtable mComponents = new Hashtable(50, 50);
	private Dimension mSize = new Dimension(0, 0);

	public RHLayout()
	{
	}

	// Waste of space really.
	private Dimension addInsets(Container container, Dimension size)
	{
		return size;
	}

	// Add a component to the layout. The positioning object should be a point.
	public void addLayoutComponent(Component comp, Object position)
	{
		if (position instanceof Point)
		{
			mComponents.put(comp, position);
			mDirty = true;
		}
		else
		{
			Object o1 = comp == null ? null : comp.getClass();
			Object o2 = position == null ? null : position.getClass();

			String s = "Internal error: cannot add " + o1
					+ " to SSLayout: constraints of type " + o2
					+ " are not understood";

			throw new IllegalArgumentException(s);
		}
	}

	// This is required as part of the interface.
	public void addLayoutComponent(String name, Component comp)
	{
		this.addLayoutComponent(comp, name);
	}

	// helper functions
	private void calculateSizes()
	{
		if (!mDirty)
		{
			return;
		}
		mDirty = false;
		mSize.width = 0;
		mSize.height = 0;
		Enumeration e = mComponents.keys();

		while (e.hasMoreElements())
		{
			Component c = (Component) e.nextElement();
			Point p = (Point) mComponents.get(c);
			mSize.width = Math.max(mSize.width, c.getPreferredSize().width
					+ p.x);
			mSize.height = Math.max(mSize.height, c.getPreferredSize().height
					+ p.y);
		}
	}

	// Get the constraint for an object.
	public Object getConstraint(Component comp)
	{
		return mComponents.get(comp);
	}

	// Gets the alignment of the objects. 0.5 is centered.
	public float getLayoutAlignmentX(Container parent)
	{
		return 0.5f;
	}

	// Gets the alignment of the objects. 0.5 is centered.
	public float getLayoutAlignmentY(Container parent)
	{
		return 0.5f;
	}

	// Does exactly what it says it does. Invalidate the layout.
	public void invalidateLayout(Container container)
	{
		mDirty = true;
	}

	// Layout the container.
	public void layoutContainer(Container container)
	{
		while (mDirty)
		{
			mDirty = false;

			Enumeration e = mComponents.keys();
			container.getInsets();

			while (e.hasMoreElements())
			{
				Component c = (Component) e.nextElement();
				Point p = (Point) mComponents.get(c);

				c.setBounds(p.x, p.y, c.getPreferredSize().width, c
						.getPreferredSize().height);
			}
		}
	}

	// Calculate the maximum size for the target container.
	public Dimension maximumLayoutSize(Container target)
	{
		return preferredLayoutSize(target);
	}

	// Calculate the minimum size for the target container.
	public Dimension minimumLayoutSize(Container target)
	{
		return preferredLayoutSize(target);
	}

	// Calculate the preferred size for the target container.
	public Dimension preferredLayoutSize(Container target)
	{
		calculateSizes();
		return addInsets(target, new Dimension(mSize.width, mSize.height));
	}

	// Externalizable implementation.
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException
	{
		mDirty = in.readBoolean();
		mComponents = (Hashtable) in.readObject();
		mSize = (Dimension) in.readObject();
	}

	// Remove a component from the layout.
	public void removeLayoutComponent(Component comp)
	{
		mComponents.remove(comp);
		mDirty = true;
	}

	// Externalizable implementation.
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeBoolean(mDirty);
		out.writeObject(mComponents);
		out.writeObject(mSize);
	}
}