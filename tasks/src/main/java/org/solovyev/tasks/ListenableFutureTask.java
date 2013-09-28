package org.solovyev.tasks;

/*
 * Copyright (C) 2008 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.*;

import static com.google.common.util.concurrent.Uninterruptibles.getUninterruptibly;

/**
 * Copied from Guava, listener removal added
 *
 * @author Sven Mawson
 * @author serso
 * @since 1.0
 */
public final class ListenableFutureTask<V> extends FutureTask<V>
		implements ListenableFuture<V> {

	// The execution list to hold our listeners.
	private final ExecutionList executionList = new ExecutionList();

	/**
	 * Creates a {@code ListenableFutureTask} that will upon running, execute the
	 * given {@code Callable}.
	 *
	 * @param callable the callable task
	 * @since 10.0
	 */
	public static <V> ListenableFutureTask<V> create(Callable<V> callable) {
		return new ListenableFutureTask<V>(callable);
	}

	/**
	 * Creates a {@code ListenableFutureTask} that will upon running, execute the
	 * given {@code Runnable}, and arrange that {@code get} will return the
	 * given result on successful completion.
	 *
	 * @param runnable the runnable task
	 * @param result   the result to return on successful completion. If you don't
	 *                 need a particular result, consider using constructions of the form:
	 *                 {@code ListenableFuture<?> f = ListenableFutureTask.create(runnable,
	 *                 null)}
	 * @since 10.0
	 */
	public static <V> ListenableFutureTask<V> create(
			Runnable runnable, @Nullable V result) {
		return new ListenableFutureTask<V>(runnable, result);
	}

	private ListenableFutureTask(Callable<V> callable) {
		super(callable);
	}

	private ListenableFutureTask(Runnable runnable, @Nullable V result) {
		super(runnable, result);
	}

	@Override
	public void addListener(Runnable listener, Executor exec) {
		executionList.add(listener, exec);
	}

	public void removeListener(Runnable listener) {
		executionList.remove(listener);
	}

	/**
	 * Internal implementation detail used to invoke the listeners.
	 */
	@Override
	protected void done() {
		executionList.execute();
	}

	public void addListener(@Nonnull final FutureCallback<V> callback) {
		addListener(callback, MoreExecutors.sameThreadExecutor());
	}

	public void addListener(@Nonnull final FutureCallback<V> callback, @Nonnull Executor executor) {
		this.addListener(new RunnableListener(callback), executor);
	}

	public void removeListener(@Nullable final FutureCallback<V> callback) {
		if (callback == null) {
			return;
		}

		this.removeListener(new RunnableListener(callback));
	}

	public void removeAllListeners() {
		this.executionList.removeAll();
	}

	private class RunnableListener implements Runnable {

		@Nonnull
		private final FutureCallback<V> callback;

		public RunnableListener(@Nonnull FutureCallback<V> callback) {
			this.callback = callback;
		}

		@Override
		public void run() {
			try {
				V value = getUninterruptibly(ListenableFutureTask.this);
				callback.onSuccess(value);
			} catch (ExecutionException e) {
				callback.onFailure(e.getCause());
			} catch (RuntimeException e) {
				callback.onFailure(e);
			} catch (Error e) {
				callback.onFailure(e);
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			final RunnableListener that = (RunnableListener) o;

			if (!callback.equals(that.callback)) return false;

			return true;
		}

		@Override
		public int hashCode() {
			return callback.hashCode();
		}
	}
}
