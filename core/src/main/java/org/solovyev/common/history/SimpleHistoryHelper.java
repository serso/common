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

package org.solovyev.common.history;

import org.solovyev.common.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is not thread safe - use {@link SynchronizedHistoryHelper} instead in multi-threaded environment
 */
@NotThreadSafe
@SuppressWarnings("VO_VOLATILE_INCREMENT")
public class SimpleHistoryHelper<T> implements HistoryHelper<T> {

    /*
    **********************************************************************
    *
    *                           CONSTANTS
    *
    **********************************************************************
    */

    private static final int DEFAULT_HISTORY_CAPACITY = 100;
    private static final int START_HISTORY_INDEX = -1;

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @Nonnull
    private final List<T> history;

    private volatile int currentStateIndex = START_HISTORY_INDEX;

    private final int historyCapacity;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTOR
    *
    **********************************************************************
    */

    private SimpleHistoryHelper() {
        this(DEFAULT_HISTORY_CAPACITY);
    }

    private SimpleHistoryHelper(int historyCapacity) {
        this.historyCapacity = historyCapacity;
        this.history = new ArrayList<T>(historyCapacity);
    }

    @Nonnull
    public static <T> SimpleHistoryHelper<T> newInstance() {
        return newInstance(DEFAULT_HISTORY_CAPACITY);
    }

    @Nonnull
    public static <T> SimpleHistoryHelper<T> newInstance(int historyCapacity) {
        return new SimpleHistoryHelper<T>(historyCapacity);
    }



    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public T undo(@Nullable T currentState) {
        if (!isUndoAvailable()) {
            throw new IndexOutOfBoundsException();
        }

        currentStateIndex--;

        return history.get(currentStateIndex);
    }

    @Override
    public T redo(@Nullable T currentState) {
        if (!isRedoAvailable()) {
            throw new IndexOutOfBoundsException();
        }
        currentStateIndex++;
        return history.get(currentStateIndex);
    }

    @Override
    public void addState(@Nullable T currentState) {
        if (needToAdd(currentState)) {
            if (currentStateIndex == history.size() - 1) {
                if (currentStateIndex < historyCapacity - 1) {
                    currentStateIndex++;
                    history.add(currentState);
                } else {
                    history.remove(0);
                    history.add(currentState);
                }
            } else {
                assert currentStateIndex < history.size() - 1 : "Invalid history state index!";
                currentStateIndex++;
                history.set(currentStateIndex, currentState);
                while (history.size() > currentStateIndex + 1) {
                    history.remove(history.size() - 1);
                }
            }
        }
    }

    private boolean needToAdd(@Nullable T currentState) {
        boolean result;

        if (history.isEmpty()) {
            result = true;
        } else {
            result = !Objects.areEqual(getLastHistoryState(), currentState);
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return history.isEmpty();
    }

    @Override
    public T getLastHistoryState() {
        T result = null;

        if (currentStateIndex >= 0 && currentStateIndex < history.size()) {
            result = history.get(currentStateIndex);
        }

        return result;
    }

    @Override
    public boolean isUndoAvailable() {
        return currentStateIndex > 0;
    }

    @Override
    public boolean isRedoAvailable() {
        return currentStateIndex < history.size() - 1;
    }

    @Override
    public boolean isActionAvailable(@Nonnull HistoryAction historyAction) {
        boolean result = false;

        switch (historyAction) {
            case undo:
                result = isUndoAvailable();
                break;
            case redo:
                result = isRedoAvailable();
                break;
            default:
                throw new UnsupportedOperationException(historyAction + " is not supported!");
        }

        return result;
    }

    @Override
    public T doAction(@Nonnull HistoryAction historyAction, @Nullable T currentState) {
        T result = null;

        switch (historyAction) {
            case undo:
                result = undo(currentState);
                break;
            case redo:
                result = redo(currentState);
                break;
        }

        return result;
    }

    @Nonnull
    @Override
    public List<T> getStates() {
        return Collections.unmodifiableList(this.history);
    }

    @Override
    public void clear() {
        this.currentStateIndex = START_HISTORY_INDEX;
        this.history.clear();
    }
}
