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

import javax.annotation.Nonnull;

import org.junit.Test;
import org.solovyev.common.MutableObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: serso
 * Date: 2/1/13
 * Time: 9:40 PM
 */
public class EventListenersImplTest {

    @Test
    public void testFireEvent() throws Exception {
        JEventListeners<JEventListener<?>, JEvent> listeners = Listeners.newEventListenersBuilder().onCallerThread().withHardReferences().create();

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
    public void testCallerEventThread() throws Exception {
        JEventListeners<JEventListener<? extends JEvent>, JEvent> listeners = Listeners.newEventListenersBuilderFor(JEvent.class).onCallerThread().withHardReferences().create();

        final CountDownLatch latch = new CountDownLatch(1);
        final MutableObject<Thread> eventThread = new MutableObject<Thread>();
        listeners.addListener(new JEventListener<JEvent>() {
            @Nonnull
            @Override
            public Class<JEvent> getEventType() {
                return JEvent.class;
            }

            @Override
            public void onEvent(@Nonnull JEvent event) {
                eventThread.setObject(Thread.currentThread());
                latch.countDown();
            }
        });

        listeners.fireEvent(new TestEvent1());
        if (latch.await(1, TimeUnit.SECONDS)) {
            Assert.assertEquals(Thread.currentThread(), eventThread.getObject());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testBackgroundEventThread() throws Exception {
        JEventListeners<JEventListener<? extends JEvent>, JEvent> listeners = Listeners.newEventListenersBuilderFor(JEvent.class).onBackgroundThread().withHardReferences().create();

        final CountDownLatch latch = new CountDownLatch(1);
        final MutableObject<Thread> eventThread = new MutableObject<Thread>();
        listeners.addListener(new JEventListener<JEvent>() {
            @Nonnull
            @Override
            public Class<JEvent> getEventType() {
                return JEvent.class;
            }

            @Override
            public void onEvent(@Nonnull JEvent event) {
                eventThread.setObject(Thread.currentThread());
                latch.countDown();
            }
        });

        listeners.fireEvent(new TestEvent1());
        if (latch.await(1, TimeUnit.SECONDS)) {
            Assert.assertNotSame(Thread.currentThread(), eventThread.getObject());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testWeakReferences() throws Exception {
        JEventListeners<JEventListener<? extends JEvent>, JEvent> listeners = Listeners.newEventListenersBuilderFor(JEvent.class).onCallerThread().withWeakReferences().create();

        TestEventListener2 l2 = new TestEventListener2();
        final WeakReference<TestEventListener2> l3 = new WeakReference<TestEventListener2>(new TestEventListener2());

        listeners.addListener(l2);
        listeners.addListener(l3.get());

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.get().getCount());

        System.gc();

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(2, l2.getCount());
        Assert.assertNull(l3.get());

        final WeakReference<TestEventListener2> l2Ref = new WeakReference<TestEventListener2>(l2);
        listeners.addListener(new JEventListener<JEvent>() {
            @Nonnull
            @Override
            public Class<JEvent> getEventType() {
                return JEvent.class;
            }

            @Override
            public void onEvent(@Nonnull JEvent event) {
                Assert.assertNull(l2Ref.get());
            }
        });
        l2 = null;

        System.gc();

        listeners.fireEvent(new TestEvent2());
    }

    @Test
    public void testHardReferences() throws Exception {
        JEventListeners<JEventListener<? extends JEvent>, JEvent> listeners = Listeners.newEventListenersBuilderFor(JEvent.class).onCallerThread().withHardReferences().create();

        TestEventListener2 l2 = new TestEventListener2();
        final WeakReference<TestEventListener2> l3 = new WeakReference<TestEventListener2>(new TestEventListener2());

        listeners.addListener(l2);
        listeners.addListener(l3.get());

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.get().getCount());

        System.gc();

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(2, l2.getCount());
        Assert.assertEquals(2, l3.get().getCount());

        final WeakReference<TestEventListener2> l2Ref = new WeakReference<TestEventListener2>(l2);
        listeners.addListener(new JEventListener<JEvent>() {
            @Nonnull
            @Override
            public Class<JEvent> getEventType() {
                return JEvent.class;
            }

            @Override
            public void onEvent(@Nonnull JEvent event) {
                Assert.assertEquals(3, l3.get().getCount());
            }
        });
        l2 = null;

        System.gc();

        listeners.fireEvent(new TestEvent2());
    }

    @Test
    public void testAddListener() throws Exception {
        JEventListeners<JEventListener<? extends TestEvent2>, TestEvent2> listeners = Listeners.newEventListenersBuilderFor(TestEvent2.class).onCallerThread().withHardReferences().create();

        final TestEventListener2 l2 = new TestEventListener2();
        final TestEventListener2 l3 = new TestEventListener2();

        Assert.assertTrue(listeners.addListener(l2));
        Assert.assertFalse(listeners.addListener(l2));
        Assert.assertFalse(listeners.addListener(l2));
        Assert.assertTrue(listeners.addListener(l3));

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.getCount());
    }

    @Test
    public void testRemoveListener() throws Exception {
        JEventListeners<JEventListener<? extends TestEvent2>, TestEvent2> listeners = Listeners.newEventListenersBuilderFor(TestEvent2.class).onCallerThread().withHardReferences().create();

        final TestEventListener2 l2 = new TestEventListener2();
        final TestEventListener2 l3 = new TestEventListener2();

        listeners.addListener(l2);
        listeners.addListener(l3);

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.getCount());

        Assert.assertTrue(listeners.removeListener(l3));
        Assert.assertFalse(listeners.removeListener(l3));

        listeners.fireEvent(new TestEvent2());
        Assert.assertEquals(2, l2.getCount());
        Assert.assertEquals(1, l3.getCount());

        listeners.removeListener(l2);

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(2, l2.getCount());
        Assert.assertEquals(1, l3.getCount());
    }

    @Test
    public void testRemoveAll() throws Exception {

        JEventListeners<JEventListener<? extends TestEvent2>, TestEvent2> listeners = Listeners.newEventListenersBuilderFor(TestEvent2.class).onCallerThread().withHardReferences().create();

        final TestEventListener2 l2 = new TestEventListener2();
        final TestEventListener2 l3 = new TestEventListener2();

        listeners.addListener(l2);
        listeners.addListener(l3);

        listeners.fireEvent(new TestEvent2());

        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.getCount());

        listeners.removeListeners();

        listeners.fireEvent(new TestEvent2());
        Assert.assertEquals(1, l2.getCount());
        Assert.assertEquals(1, l3.getCount());
    }

    private static class TestEvent1 implements JEvent {

    }

    private static abstract class AbstractTestEventListener<E extends JEvent> extends AbstractJEventListener<E> {

        @Nonnull
        private final AtomicInteger counter = new AtomicInteger();

        protected AbstractTestEventListener(@Nonnull Class<E> eventType) {
            super(eventType);
        }

        protected void count() {
            counter.getAndIncrement();
        }

        public int getCount() {
            return counter.get();
        }

    }

    private static class TestEventListener1 extends AbstractTestEventListener<TestEvent1> {

        public TestEventListener1() {
            super(TestEvent1.class);
        }

        @Override
        public void onEvent(@Nonnull TestEvent1 event) {
            count();
        }
    }

    private static class TestEvent2 implements JEvent {

    }

    private static class TestEventListener2 extends AbstractTestEventListener<TestEvent2> {

        private TestEventListener2() {
            super(TestEvent2.class);
        }

        @Override
        public void onEvent(@Nonnull TestEvent2 event) {
            count();
        }
    }

    private static class TestEvent3 extends TestEvent2 {

    }

    private static class TestEventListener3 extends AbstractTestEventListener<TestEvent3> {

        protected TestEventListener3() {
            super(TestEvent3.class);
        }

        @Override
        public void onEvent(@Nonnull TestEvent3 event) {
            count();
        }
    }
}
