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

package org.solovyev.common.history;

import org.solovyev.common.SynchronizedObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * User: serso
 * Date: 3/3/13
 * Time: 5:37 PM
 */
public class SynchronizedHistoryHelper<T> extends SynchronizedObject<HistoryHelper<T>> implements HistoryHelper<T> {

    private SynchronizedHistoryHelper(@Nonnull HistoryHelper<T> delegate) {
        super(delegate);
    }

    @Nonnull
    public static <T> SynchronizedHistoryHelper<T> wrap(@Nonnull HistoryHelper<T> delegate) {
        return new SynchronizedHistoryHelper<T>(delegate);
    }

    @Override
    public boolean isEmpty() {
        synchronized (mutex) {
            return delegate.isEmpty();
        }
    }

    @Nullable
    @Override
    public T getLastHistoryState() {
        synchronized (mutex) {
            return delegate.getLastHistoryState();
        }
    }

    @Override
    public boolean isUndoAvailable() {
        synchronized (mutex) {
            return delegate.isUndoAvailable();
        }
    }

    @Nullable
    @Override
    public T undo(@Nullable T currentState) {
        synchronized (mutex) {
            return delegate.undo(currentState);
        }
    }

    @Override
    public boolean isRedoAvailable() {
        synchronized (mutex) {
            return delegate.isRedoAvailable();
        }
    }

    @Nullable
    @Override
    public T redo(@Nullable T currentState) {
        synchronized (mutex) {
            return delegate.redo(currentState);
        }
    }

    @Override
    public boolean isActionAvailable(@Nonnull HistoryAction historyAction) {
        synchronized (mutex) {
            return delegate.isActionAvailable(historyAction);
        }
    }

    @Nullable
    @Override
    public T doAction(@Nonnull HistoryAction historyAction, @Nullable T currentState) {
        synchronized (mutex) {
            return delegate.doAction(historyAction, currentState);
        }
    }

    @Override
    public void addState(@Nullable T currentState) {
        synchronized (mutex) {
            delegate.addState(currentState);
        }
    }

    @Nonnull
    @Override
    public List<T> getStates() {
        synchronized (mutex) {
            return delegate.getStates();
        }
    }

    @Override
    public void clear() {
        synchronized (mutex) {
            delegate.clear();
        }
    }
}
