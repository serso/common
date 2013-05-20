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

package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Set;

/**
 * User: serso
 * Date: 4/9/13
 * Time: 1:12 AM
 */
final class ListenersAwareFutureTask<T> {

	@GuardedBy("listeners")
	@Nonnull
	private final Set<FutureCallback<T>> listeners = new HashSet<FutureCallback<T>>();

	@Nonnull
	private final ListenableFutureTask<T> futureTask;

	@GuardedBy("listeners")
	private volatile boolean executed = false;

	private ListenersAwareFutureTask(@Nonnull ListenableFutureTask<T> futureTask) {
		this.futureTask = futureTask;
		Futures.addCallback(futureTask, new FutureCallback<T>() {
			@Override
			public void onSuccess(T result) {
				synchronized (listeners) {
					executed = true;
				}

				for (FutureCallback<T> listener : getListenersCopy()) {
					listener.onSuccess(result);
				}

				synchronized (listeners) {
					listeners.clear();
				}
			}

			@Override
			public void onFailure(Throwable t) {
				synchronized (listeners) {
					executed = true;
				}

				for (FutureCallback<T> listener : getListenersCopy()) {
					listener.onFailure(t);
				}

				synchronized (listeners) {
					listeners.clear();
				}
			}
		});
	}

	@Nonnull
	static <T> ListenersAwareFutureTask<T> create(@Nonnull ListenableFutureTask<T> futureTask) {
		return new ListenersAwareFutureTask<T>(futureTask);
	}

	@Nonnull
	private Set<FutureCallback<T>> getListenersCopy() {
		synchronized (listeners) {
			return new HashSet<FutureCallback<T>>(listeners);
		}
	}

	public boolean addListener(@Nonnull FutureCallback<T> listener) {
		synchronized (listeners) {
			if (!executed) {
				return listeners.add(listener);
			} else {
				// too late
				return false;
			}
		}
	}

	public boolean removeListener(@Nonnull FutureCallback<T> listener) {
		synchronized (listeners) {
			return listeners.remove(listener);
		}
	}

	public void removeAllListeners() {
		synchronized (listeners) {
			listeners.clear();
		}
	}

	@Nonnull
	public ListenableFutureTask<T> getFutureTask() {
		return futureTask;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ListenersAwareFutureTask that = (ListenersAwareFutureTask) o;

		if (!futureTask.equals(that.futureTask)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return futureTask.hashCode();
	}
}
