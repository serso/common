package org.solovyev.common;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * User: serso
 * Date: 9/30/11
 * Time: 3:33 PM
 */
public class TimeSingletonTest {

	@Test
	public void testTimeSingleton() throws Exception {
		final Date startDate = new Date();

		Thread.sleep(5000);

		Assert.assertTrue(TimeSingleton.instance.getDate().getTime() - new Date().getTime() < 50);
		Assert.assertTrue(TimeSingleton.instance.getDate().getTime() - startDate.getTime() >= 5000);

	}
}
