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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.listeners;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * note: some methods of {@link ReferenceListeners} are already tested in {@link EventListenersImplTest}
 */
public class ReferenceListenersTest {

	@Test
	public void testGetListeners() throws Exception {
		final JListeners<TestListener> listeners = ReferenceListeners.newHardReferenceInstance();

		final TestListener l1 = new TestListener() {
			@Override
			public void doSomething() {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		};

		Assert.assertTrue(listeners.addListener(l1));
		Assert.assertFalse(listeners.addListener(l1));

		listeners.addListener(new TestListener() {
			@Override
			public void doSomething() {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});

		final Collection<TestListener> testListeners = listeners.getListeners();
		Assert.assertEquals(2, testListeners.size());
		testListeners.remove(l1);
		Assert.assertEquals(1, testListeners.size());
		Assert.assertEquals(2, listeners.getListeners().size());

	}

	@Test
	public void testGetListenersOfType() throws Exception {

		final JListeners<TestListener> listeners = ReferenceListeners.newHardReferenceInstance();

		final TestListener l1 = new TestListener1();
		final TestListener l2 = new TestListener2();
		final TestListener l3 = new TestListener3();

		Assert.assertTrue(listeners.addListener(l1));
		Assert.assertTrue(listeners.addListener(l2));
		Assert.assertTrue(listeners.addListener(l3));

		final Collection<TestListener> testListeners = listeners.getListenersOfType(TestListener.class);
		Assert.assertEquals(3, testListeners.size());

		final Collection<TestListener1> testListeners1 = listeners.getListenersOfType(TestListener1.class);
		Assert.assertEquals(1, testListeners1.size());

		final Collection<TestListener2> testListeners2 = listeners.getListenersOfType(TestListener2.class);
		Assert.assertEquals(2, testListeners2.size());

		final Collection<TestListener3> testListeners3 = listeners.getListenersOfType(TestListener3.class);
		Assert.assertEquals(1, testListeners3.size());

	}

	private static interface TestListener {

		void doSomething();

	}

	private static class TestListener1 implements TestListener {

		@Override
		public void doSomething() {
			//To change body of implemented methods use File | Settings | File Templates.
		}
	}

	private static class TestListener2 implements TestListener {

		@Override
		public void doSomething() {
			//To change body of implemented methods use File | Settings | File Templates.
		}
	}

	private static class TestListener3 extends TestListener2 {
	}
}
