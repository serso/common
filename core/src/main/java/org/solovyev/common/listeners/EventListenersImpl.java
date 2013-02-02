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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: serso
 * Date: 2/1/13
 * Time: 9:21 PM
 */
class EventListenersImpl<L extends JEventListener<? extends E>, E extends JEvent> implements JEventListeners<L, E> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final JListeners<L> listeners;

    @NotNull
    private final Class<E> baseEventType;

    @Nullable
    private final ExecutorService eventExecutor;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private EventListenersImpl(@NotNull JListeners<L> listeners, @NotNull Class<E> baseEventType, int eventThreadsCount) {
        if ( eventThreadsCount < 0 ) {
            throw new IllegalArgumentException("eventThreadsCount should be not negative!");
        }
        this.listeners = listeners;
        this.baseEventType = baseEventType;

        if ( eventThreadsCount == 0) {
            this.eventExecutor = null;
        } else if ( eventThreadsCount == 1 ) {
            this.eventExecutor = Executors.newSingleThreadExecutor(createDefaultThreadFactory());
        } else {
            this.eventExecutor = Executors.newFixedThreadPool(eventThreadsCount, createDefaultThreadFactory());
        }
    }

    @NotNull
    public static <L extends JEventListener<?>> EventListenersImpl<L, JEvent> newSingleThread(@NotNull JListeners<L> listeners) {
        return newInstance(listeners, JEvent.class, 1);
    }

    @NotNull
    public static <L extends JEventListener<? extends E>, E extends JEvent> EventListenersImpl<L, E> newSingleThread(@NotNull JListeners<L> listeners, @NotNull Class<E> baseEventType) {
        return newInstance(listeners, baseEventType, 1);
    }

    @NotNull
    public static <L extends JEventListener<? extends E>, E extends JEvent> EventListenersImpl<L, E> newInstance(@NotNull JListeners<L> listeners, @NotNull Class<E> baseEventType, int eventThreadsCount) {
        return new EventListenersImpl<L, E>(listeners, baseEventType, eventThreadsCount);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public void fireEvent(@NotNull final E event) {
        final Collection<L> listeners = this.listeners.getListeners();

        if ( eventExecutor == null ) {
            // run on current thread
            fireEvent(event, listeners);
        } else {
            eventExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    fireEvent(event, listeners);
                }
            });
        }
    }

    private void fireEvent(@NotNull E event, @NotNull Collection<L> listeners) {
        for (L listener : listeners) {
            if  (listener.getEventType().isAssignableFrom(event.getClass())) {
                ((JEventListener<E>) listener).onEvent(event);
            }
        }
    }

    @Override
    public boolean addListener(@NotNull L listener) {
        if ( !baseEventType.isAssignableFrom(listener.getEventType()) ) {
            throw new IllegalArgumentException("Current listener cannot be added, because will never be fired!");
        }
        return listeners.addListener(listener);
    }

    @Override
    public boolean removeListener(@NotNull L listener) {
        return listeners.removeListener(listener);
    }

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    @NotNull
    private static ThreadFactory createDefaultThreadFactory() {
        return new ThreadFactory() {

            @NotNull
            private AtomicInteger threadCounter = new AtomicInteger(0);

            @NotNull
            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new Thread(r, "Event executor thread #" + threadCounter.incrementAndGet());
            }
        };
    }
}
