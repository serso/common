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

    @NotNull
    public static <L extends JEventListener<?>> JEventListeners<L, JEvent> newWeakRefEventListeners() {
        return SimpleEventListeners.newInstance(Listeners.<L>newWeakRefListeners());
    }

    @NotNull
    public static <L extends JEventListener<?>> JEventListeners<L, JEvent> newHardRefEventListeners() {
        return SimpleEventListeners.newInstance(Listeners.<L>newHardRefListeners());
    }

    @NotNull
    public static <R extends Reference<L>, L extends JEventListener<?>> JEventListeners<L, JEvent> newRefEventListeners(@NotNull ReferenceProducer<R, L> referenceProducer) {
        return SimpleEventListeners.newInstance(Listeners.<R, L>newRefListeners(referenceProducer));
    }

    @NotNull
    public static <L extends JEventListener<?>, E extends JEvent> JEventListeners<L, E> newWeakRefEventListenersOf(@NotNull Class<E> eventType) {
        return SimpleEventListeners.newInstance(Listeners.<L>newWeakRefListeners(), eventType);
    }

    @NotNull
    public static <L extends JEventListener<?>, E extends JEvent> JEventListeners<L, E> newHardRefEventListenersOf(@NotNull Class<E> eventType) {
        return SimpleEventListeners.newInstance(Listeners.<L>newHardRefListeners(), eventType);
    }
}
