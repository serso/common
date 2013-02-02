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

    @NotNull
    public static <L> JListeners<L> newWeakRefListeners() {
        return ReferenceListeners.newWeakReferenceInstance();
    }

    @NotNull
    public static <L> JListeners<L> newHardRefListeners() {
        return ReferenceListeners.newHardReferenceInstance();
    }

    @NotNull
    public static <R extends Reference<L>, L> JListeners<L> newRefListeners(@NotNull ReferenceProducer<R, L> referenceProducer) {
        return ReferenceListeners.newInstance(referenceProducer);
    }

    /**
     * Creates {@link JEventListeners} object which runs on ONE background thread, uses WEAK references and accepts all event types
     * @return event bus
     */
    @NotNull
    public static JEventListeners<JEventListener<? extends JEvent>, JEvent> newEventBus() {
        return JEventListenersBuilder.newForJEvent().withWeakReferences().onBackgroundThread().create();
    }

    /**
     * Creates {@link JEventListeners} object which runs on ONE background thread, uses WEAK references and accepts only sub classes of <var>baseEventType</var>
     * @return event bus
     */
    @NotNull
    public static <E extends JEvent> JEventListeners<JEventListener<? extends E>, E> newEventBusFor(@NotNull Class<E> baseEventType) {
        return JEventListenersBuilder.newFor(baseEventType).withWeakReferences().onBackgroundThread().create();
    }

    @NotNull
    public static <L extends JEventListener<?>> JEventListenersBuilder<JEventListener<? extends JEvent>, JEvent> newEventListenersBuilder() {
        return JEventListenersBuilder.newForJEvent();
    }

    @NotNull
    public static <L extends JEventListener<?>, E extends JEvent> JEventListenersBuilder<JEventListener<? extends E>, E> newEventListenersBuilderFor(@NotNull Class<E> baseEventType) {
        return JEventListenersBuilder.newFor(baseEventType);
    }
}
