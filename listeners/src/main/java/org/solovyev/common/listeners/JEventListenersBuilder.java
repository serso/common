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
import org.solovyev.common.JBuilder;

/**
 * User: serso
 * Date: 2/2/13
 * Time: 1:12 PM
 */
public final class JEventListenersBuilder<L extends JEventListener<? extends E>, E extends JEvent> implements JBuilder<JEventListeners<L, E>> {

    private int eventThreadsCount = 1;

    private boolean weakReference = true;

    @Nonnull
    private final Class<E> baseEventType;

    private JEventListenersBuilder(@Nonnull Class<E> baseEventType) {
        this.baseEventType = baseEventType;
    }

    @Nonnull
    static <L extends JEventListener<? extends E>, E extends JEvent> JEventListenersBuilder<L, E> newFor(@Nonnull Class<E> baseEventType) {
        return new JEventListenersBuilder<L, E>(baseEventType);
    }

    @Nonnull
    static <L extends JEventListener<? extends JEvent>> JEventListenersBuilder<L, JEvent> newForJEvent() {
        return new JEventListenersBuilder<L, JEvent>(JEvent.class);
    }

    /**
     * Means that events must be fired on the same thread which calls {@link JEventListeners#fireEvent(E)} method
     * @return current builder
     */
    @Nonnull
    public JEventListenersBuilder<L, E> onCallerThread() {
        this.eventThreadsCount = 0;
        return this;
    }

    @Nonnull
    public JEventListenersBuilder<L, E> onBackgroundThread() {
        this.eventThreadsCount = 1;
        return this;
    }

    @Nonnull
    public JEventListenersBuilder<L, E> onBackgroundThreads(int eventThreadsCount) {
        this.eventThreadsCount = eventThreadsCount;
        return this;
    }

    @Nonnull
    public JEventListenersBuilder<L, E> withWeakReferences() {
        this.weakReference = true;
        return this;
    }

    @Nonnull
    public JEventListenersBuilder<L, E> withHardReferences() {
        this.weakReference = false;
        return this;
    }

    @Nonnull
    @Override
    public JEventListeners<L, E> create() {
        final JEventListeners<L, E> result;

        final JListeners<L> listeners;

        if ( weakReference ) {
            listeners = Listeners.newWeakRefListeners();
        } else {
            listeners = Listeners.newHardRefListeners();
        }

        result = EventListenersImpl.newInstance(listeners, baseEventType, eventThreadsCount);


        return result;
    }
}
