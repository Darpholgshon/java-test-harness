package org.hodgson.development;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AspectTest
{
	private static Log log = LogFactory.getLog(AspectTest.class);

	public static void main(String[] args)
	{
		log.debug("Hello World");
		log.info("Hello World");
		log.warn("Hello World");
		log.error("Hello World");
		log.fatal("Hello World");
	}

}
