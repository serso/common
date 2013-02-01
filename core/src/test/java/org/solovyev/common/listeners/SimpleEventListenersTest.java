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
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.listeners;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: serso
 * Date: 2/1/13
 * Time: 9:40 PM
 */
public class SimpleEventListenersTest {

    @Test
    public void testFireEvent() throws Exception {
         JEventListeners<JEventListener<?>, JEvent> listeners = Listeners.newHardRefEventListeners();

        final TestEventListener1 l1 = new TestEventListener1();
        final TestEventListener2 l2 = new TestEventListener2();
        final TestEventListener3 l3 = new TestEventListener3();

        listeners.addListener(l1);
        listeners.addListener(l2);
        listeners.addListener(l3);

        listeners.fireEvent(new TestEvent1());

        Assert.assertEquals(1, l1.getCount());
        Assert.assertEquals(0, l2.getCount());
        Assert.assertEquals(0, l3.getCount());

        listeners.fireEvent(new TestEvent1());

        Assert.assertEquals(2, l1.getCount());
        Assert.assertEquals(0, l2.getCount());
        Assert.assertEquals(0, l3.getCount());

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(2, l1.getCount());
        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(0, l3.getCount());

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(2, l1.getCount());
        Assert.assertEquals(2, l2.getCount());
        Assert.assertEquals(0, l3.getCount());

        listeners.fireEvent(new TestEvent3());

        Assert.assertEquals(2, l1.getCount());
        Assert.assertEquals(3, l2.getCount());
        Assert.assertEquals(1, l3.getCount());

        listeners.fireEvent(new TestEvent3());

        Assert.assertEquals(2, l1.getCount());
        Assert.assertEquals(4, l2.getCount());
        Assert.assertEquals(2, l3.getCount());
    }

    @Test
    public void testAddListener() throws Exception {

    }

    @Test
    public void testRemoveListener() throws Exception {

    }

    @Test
    public void testGetListeners() throws Exception {

    }

    @Test
    public void testGetListenersOfType() throws Exception {

    }

    private static class TestEvent1 implements JEvent {

    }

    private static abstract class AbstractTestEventListener {

        @NotNull
        private final AtomicInteger counter = new AtomicInteger();

        protected void count() {
            counter.getAndIncrement();
        }

        public int getCount() {
            return counter.get();
        }

    }

    private static class TestEventListener1 extends AbstractTestEventListener implements JEventListener<TestEvent1> {

        @NotNull
        @Override
        public Class<TestEvent1> getEventType() {
            return TestEvent1.class;
        }

        @Override
        public void onEvent(@NotNull TestEvent1 event) {
            count();
        }
    }

    private static class TestEvent2 implements JEvent {

    }

    private static class TestEventListener2 extends AbstractTestEventListener  implements JEventListener<TestEvent2> {

        @NotNull
        @Override
        public Class<TestEvent2> getEventType() {
            return TestEvent2.class;
        }

        @Override
        public void onEvent(@NotNull TestEvent2 event) {
           count();
        }
    }

    private static class TestEvent3 extends TestEvent2 {

    }

    private static class TestEventListener3 extends AbstractTestEventListener  implements JEventListener<TestEvent3> {

        @NotNull
        @Override
        public Class<TestEvent3> getEventType() {
            return TestEvent3.class;
        }

        @Override
        public void onEvent(@NotNull TestEvent3 event) {
            count();
        }
    }
}
