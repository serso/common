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

/**
 * User: serso
 * Date: 9/16/11
 * Time: 11:42 PM
 */
public interface HistoryControl<T> {

    /**
     * Method moves through the history states
     *
     * @param historyAction action to be done
     */
    void doHistoryAction(@NotNull HistoryAction historyAction);

    /**
     * Method sets current history state
     *
     * @param state state to be set as current
     */
    void setCurrentHistoryState(@NotNull T state);


    /**
     * @return current history state
     */
    @NotNull
    T getCurrentHistoryState();
}
