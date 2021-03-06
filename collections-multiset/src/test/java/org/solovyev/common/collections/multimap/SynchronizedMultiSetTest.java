/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.collections.multimap;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.collections.multiset.ArrayListMultiSet;
import org.solovyev.common.collections.multiset.MultiSet;
import org.solovyev.common.collections.multiset.SynchronizedMultiSet;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SynchronizedMultiSetTest {

	private static final int THREAD_NUMBER = 100;

	// random number generator
	private static final Random rg = new Random(new Date().getTime());

	@Test
	public void testOperations() throws Exception {
		final Object mutex = new Object();
		final MultiSet<String> m = SynchronizedMultiSet.wrap(ArrayListMultiSet.<String>newInstance(), mutex);

		final CountDownLatch startPoint = new CountDownLatch(1);
		final CountDownLatch finalPoint = new CountDownLatch(THREAD_NUMBER);

		for (int i = 0; i < THREAD_NUMBER; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						startPoint.await();

						doOperations(m, mutex);

					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					} finally {
						finalPoint.countDown();
					}
				}
			}).start();
		}

		// start test
		startPoint.countDown();

		// wait until all threads finish their jobs
		finalPoint.await();

	}

	private void doOperations(@Nonnull MultiSet<String> m, @Nonnull Object mutex) {
		for (int j = 0; j < 1000; j++) {

			final int operationCode;
			final int operationCount;
			synchronized (rg) {
				operationCode = rg.nextInt(10);
				operationCount = rg.nextInt(100);
			}

			switch (operationCode) {
				case 0:
					m.add("1", operationCount);
					break;
				case 1:
					m.add("2", operationCount);
					break;
				case 2:
					m.add("3", operationCount);
					break;
				case 3:
					m.remove("1", operationCount);
					break;
				case 4:
					m.remove("2", operationCount);
					break;
				case 5:
					m.remove("3", operationCount);
					break;
				case 6:
					m.count("1");
					break;
				case 7:
					m.count("2");
					break;
				case 8:
					m.count("3");
					break;
				case 9:
					synchronized (mutex) {
						for (Iterator<String> it = m.iterator(); it.hasNext(); ) {
							final String el = it.next();
							if (el.equals("1")) {
								it.remove();
							}
						}
						Assert.assertEquals(0, m.count("1"));
					}
					break;
				case 10:
					synchronized (mutex) {
						for (Iterator<String> it = m.iterator(); it.hasNext(); ) {
							final String el = it.next();
							if (el.equals("2")) {
								it.remove();
							}
						}
						Assert.assertEquals(0, m.count("2"));
					}
					break;
			}
		}
	}
}
