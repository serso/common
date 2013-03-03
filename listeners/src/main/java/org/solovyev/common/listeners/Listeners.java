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

import java.lang.ref.Reference;

/**
 * User: serso
 * Date: 2/1/13
 * Time: 9:31 PM
 */
public final class Listeners {

    private Listeners() {
        throw new AssertionError();
    }

    /**
     * Creates instance of listeners container which uses weak references for listeners.
     * That means next: listeners might not be removed after usage (sometimes it's even impossible)
     * and if there are no any references to them in application they be removed by GC
     *
     * @param <L> type of listeners
     * @return listeners container with weak references
     */
    @Nonnull
    public static <L> JListeners<L> newWeakRefListeners() {
        return ReferenceListeners.newWeakReferenceInstance();
    }

    /**
     * Creates instance of listeners container which uses hard references for listeners.
     * Note: all unused listeners must be removed from container manually to prevent memory leaks
     *
     * @param <L> type of listeners
     * @return listeners container with hard references
     */
    @Nonnull
    public static <L> JListeners<L> newHardRefListeners() {
        return ReferenceListeners.newHardReferenceInstance();
    }

    /**
     * Create instance of listeners container with custom logic for creating references to listeners.
     * Ths usage example is next: suppose almost all listeners should use hard references except one or two,
     * then reference producer may return weak references for those 'special' listeners
     *
     * @param referenceProducer reference producer
     * @param <R> reference type
     * @param <L> listener type
     *
     * @return listeners container with user-defined references
     */
    @Nonnull
    public static <R extends Reference<L>, L> JListeners<L> newRefListeners(@Nonnull ReferenceProducer<R, L> referenceProducer) {
        return ReferenceListeners.newInstance(referenceProducer);
    }

    /**
     * Creates {@link JEventListeners} object which runs on ONE background thread, uses WEAK references and accepts all event types
     * @return event bus
     */
    @Nonnull
    public static JEventListeners<JEventListener<? extends JEvent>, JEvent> newEventBus() {
        return JEventListenersBuilder.newForJEvent().withWeakReferences().onBackgroundThread().create();
    }

    /**
     * Creates {@link JEventListeners} object which runs on ONE background thread, uses WEAK references and accepts only sub classes of <var>baseEventType</var>
     * @return event bus
     */
    @Nonnull
    public static <E extends JEvent> JEventListeners<JEventListener<? extends E>, E> newEventBusFor(@Nonnull Class<E> baseEventType) {
        return JEventListenersBuilder.newFor(baseEventType).withWeakReferences().onBackgroundThread().create();
    }

    @Nonnull
    public static <L extends JEventListener<?>> JEventListenersBuilder<JEventListener<? extends JEvent>, JEvent> newEventListenersBuilder() {
        return JEventListenersBuilder.newForJEvent();
    }

    @Nonnull
    public static <L extends JEventListener<?>, E extends JEvent> JEventListenersBuilder<JEventListener<? extends E>, E> newEventListenersBuilderFor(@Nonnull Class<E> baseEventType) {
        return JEventListenersBuilder.newFor(baseEventType);
    }
}
