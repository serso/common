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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface HistoryHelper<T> {

    boolean isEmpty();

    @Nullable
    T getLastHistoryState();

    boolean isUndoAvailable();

    @Nullable
    T undo(@Nullable T currentState);

    boolean isRedoAvailable();

    @Nullable
    T redo(@Nullable T currentState);

    boolean isActionAvailable(@NotNull HistoryAction historyAction);

    @Nullable
    T doAction(@NotNull HistoryAction historyAction, @Nullable T currentState);

    void addState(@Nullable T currentState);

    @NotNull
    List<T> getStates();

    void clear();
}
