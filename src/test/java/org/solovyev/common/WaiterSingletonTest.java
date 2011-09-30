package org.solovyev.common;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 9/30/11
 * Time: 4:37 PM
 */
public class WaiterSingletonTest {

	@Test
	public void testTimeSingleton() throws Exception {
		new Thread(new Runnable() {

			@Override
			public void run() {
				WaiterSingleton.getInstance();
			}

		}).start();

		Thread.sleep(200);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Assert.assertNull(WaiterSingleton.getInstance().getDate());
			}

		}).start();

		Assert.assertNotNull(WaiterSingleton.getInstance().getDate());

	}
}
