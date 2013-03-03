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

/**
 * User: serso
 * Date: 2/1/13
 * Time: 9:17 PM
 */
public interface JEventListeners<L extends JEventListener<? extends E>, E extends JEvent> {

    /**
     * Calls {@link JEventListener#onEvent(E)} for every listener added to container.
     * Note: method calls may be done on current thread or on some background thread depending on implementation
     *
     * @param event to be fired
     */
    void fireEvent(@Nonnull E event);

    /**
     * Adds <var>listener</var> to container.
     *
     * Note: implementation of this interface may accept or may not accept same listener objects
     *
     * @param listener  listener to be added to container
     * @return true if listener was added to the container, false otherwise
     *
     */
    boolean addListener(@Nonnull L listener);

    /**
     * Removes <var>listener</var> from container.
     *
     * @param listener listener to be removed from container
     *
     * @return true if listener was removed, false if listener was not in container
     */
    boolean removeListener(@Nonnull L listener);


    /**
     * Removes all registered listeners
     */
    void removeListeners();
}
