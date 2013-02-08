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

import java.math.BigDecimal;

/**
 * @author <a href="mailto:Ralph.Hodgson@cedar.com">Ralph Hodgson
 * @since v5.2
 * 
 *        Description -
 */
public class Thing implements Cloneable
{
	private String mCode;
	private String mDescription;
	private BigDecimal mValue;

	/**
	 * 
	 */
	public Thing()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode()
	{
		return mCode;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription()
	{
		return mDescription;
	}

	/**
	 * @return Returns the value.
	 */
	public BigDecimal getValue()
	{
		return mValue;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code)
	{
		mCode = code;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description)
	{
		mDescription = description;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(BigDecimal value)
	{
		mValue = value;
	}

	public String toString()
	{
		return "Thing[" + mCode + ", " + mDescription + ", " + mValue + "]";
	}
}
