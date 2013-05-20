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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
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

	@Nonnull
	private final JListeners<L> listeners;

	@Nonnull
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

	private EventListenersImpl(@Nonnull JListeners<L> listeners, @Nonnull Class<E> baseEventType, int eventThreadsCount) {
		this(listeners, baseEventType, newExecutor(eventThreadsCount));
	}

	@Nullable
	private static ExecutorService newExecutor(int eventThreadsCount) {
		final ExecutorService result;

		if (eventThreadsCount < 0) {
			throw new IllegalArgumentException("eventThreadsCount should be not negative!");
		}
		if (eventThreadsCount == 0) {
			result = null;
		} else if (eventThreadsCount == 1) {
			result = Executors.newSingleThreadExecutor(createDefaultThreadFactory());
		} else {
			result = Executors.newFixedThreadPool(eventThreadsCount, createDefaultThreadFactory());
		}

		return result;
	}

	private EventListenersImpl(@Nonnull JListeners<L> listeners, @Nonnull Class<E> baseEventType, @Nullable ExecutorService eventExecutor) {
		this.listeners = listeners;
		this.baseEventType = baseEventType;
		this.eventExecutor = eventExecutor;
	}

	@Nonnull
	public static <L extends JEventListener<?>> EventListenersImpl<L, JEvent> newSingleThread(@Nonnull JListeners<L> listeners) {
		return newInstance(listeners, JEvent.class, 1);
	}

	@Nonnull
	public static <L extends JEventListener<? extends E>, E extends JEvent> EventListenersImpl<L, E> newSingleThread(@Nonnull JListeners<L> listeners, @Nonnull Class<E> baseEventType) {
		return newInstance(listeners, baseEventType, 1);
	}

	@Nonnull
	public static <L extends JEventListener<? extends E>, E extends JEvent> EventListenersImpl<L, E> newInstance(@Nonnull JListeners<L> listeners, @Nonnull Class<E> baseEventType, int eventThreadsCount) {
		return new EventListenersImpl<L, E>(listeners, baseEventType, eventThreadsCount);
	}

	@Nonnull
	public static <L extends JEventListener<? extends E>, E extends JEvent> EventListenersImpl<L, E> newInstance(@Nonnull JListeners<L> listeners, @Nonnull Class<E> baseEventType, @Nonnull ExecutorService executor) {
		return new EventListenersImpl<L, E>(listeners, baseEventType, executor);
	}

	/*
	**********************************************************************
	*
	*                           METHODS
	*
	**********************************************************************
	*/

	@Override
	public void fireEvent(@Nonnull final E event) {
		fireEvents(Arrays.asList(event));
	}

	@Override
	public void fireEvents(@Nonnull final Collection<E> events) {
		final Collection<L> listeners = this.listeners.getListeners();

		if (eventExecutor == null) {
			// run on current thread
			fireEvents(events, listeners);
		} else {
			eventExecutor.execute(new Runnable() {
				@Override
				public void run() {
					fireEvents(events, listeners);
				}
			});
		}
	}

	private void fireEvents(@Nonnull Collection<E> events, @Nonnull Collection<L> listeners) {
		// first loop for events as some events might be earlier than others and we want to notify all listeners
		// in chronological order
		for (E event : events) {
			for (L listener : listeners) {
				if (listener.getEventType().isAssignableFrom(event.getClass())) {
					((JEventListener<E>) listener).onEvent(event);
				}
			}
		}
	}

	@Override
	public boolean addListener(@Nonnull L listener) {
		if (!baseEventType.isAssignableFrom(listener.getEventType())) {
			throw new IllegalArgumentException("Current listener cannot be added, because will never be fired!");
		}
		return listeners.addListener(listener);
	}

	@Override
	public boolean removeListener(@Nonnull L listener) {
		return listeners.removeListener(listener);
	}

	@Override
	public void removeListeners() {
		this.listeners.removeListeners();
	}

	/*
	**********************************************************************
	*
	*                           STATIC
	*
	**********************************************************************
	*/

	@Nonnull
	private static ThreadFactory createDefaultThreadFactory() {
		return new ThreadFactory() {

			@Nonnull
			private AtomicInteger threadCounter = new AtomicInteger(0);

			@Nonnull
			@Override
			public Thread newThread(@Nonnull Runnable r) {
				return new Thread(r, "Event executor thread #" + threadCounter.incrementAndGet());
			}
		};
	}
}
