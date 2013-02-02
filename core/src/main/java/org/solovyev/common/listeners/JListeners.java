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

import java.util.Collection;

/**
 * User: serso
 * Date: 2/1/13
 * Time: 8:38 PM
 */

/**
 * Listeners container.
 * Note: all methods of implementation of this class should be synchronized.
 *
 * @param <L> listener type
 */
public interface JListeners<L> {

    /**
     * Adds <var>listener</var> to container.
     * After this method is called {@link JListeners#getListeners()} should return collection containing this <var>listener</var>.
     *
     * Note: implementation of this interface may accept or may not accept same listener objects
     *
     * @param listener  listener to be added to container
     * @return true if listener was added to the container, false otherwise
     *
     */
    boolean addListener(@NotNull L listener);

    /**
     * Removes <var>listener</var> from container.
     * After this method is called {@link JListeners#getListeners()} should return collection not containing this <var>listener</var>.
     *
     * @param listener listener to be removed from container
     *
     * @return true if listener was removed, false if listener was not in container
     */
    boolean removeListener(@NotNull L listener);

    /**
     * @return collection of listeners of container. This collection must be a mutable copy of original collection
     */
    @NotNull
    Collection<L> getListeners();

    /**
     * @return collection of listeners of specified <var>type</var> of container. This collection must be a mutable copy of original collection
     */
    @NotNull
    <LE extends L> Collection<LE> getListenersOfType(@NotNull Class<LE> type);
}
