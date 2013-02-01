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

package org.solovyev.common.listeners;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.Collections;
import org.solovyev.common.filter.FilterType;

import java.lang.ref.HardReference;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Solovyev_S
 * Date: 20.09.12
 * Time: 16:43
 */
public class ReferenceListeners<R extends Reference<L>, L> implements Listeners<L> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final List<R> listeners = new ArrayList<R>();

    @NotNull
    private final ReferenceProducer<R, L> referenceProducer;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private ReferenceListeners(@NotNull ReferenceProducer<R, L> referenceProducer) {
        this.referenceProducer = referenceProducer;
    }

    /**
     * Creates listeners container with custom <var>ReferenceProducer</var>.
     * This producer might return different types of references for different listeners if needed.
     *
     * @param referenceProducer reference producer. This producer might return different types of references if needed for different listeners
     * @param <R> type of reference
     * @param <L> type of listener
     *
     * @return listeners container
     */
    @NotNull
    public static <R extends Reference<L>, L> ReferenceListeners<R, L> newInstance(@NotNull ReferenceProducer<R, L> referenceProducer) {
        return new ReferenceListeners<R, L>(referenceProducer);
    }

    @NotNull
    public static <L> ReferenceListeners<WeakReference<L>, L> newWeakReferenceInstance() {
        return new ReferenceListeners<WeakReference<L>, L>(new WeakReferenceProducer<L>());
    }

    @NotNull
    public static <L> ReferenceListeners<HardReference<L>, L> newHardReferenceInstance() {
        return new ReferenceListeners<HardReference<L>, L>(new HardReferenceProducer<L>());
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public void addListener(@NotNull final L listener) {
        synchronized (listeners) {
            boolean contains = Collections.contains(listeners, FilterType.included, new ReferencePredicate<R, L>(listener));

            if (!contains) {
                listeners.add(referenceProducer.newReference(listener));
            }
        }
    }

    @Override
    public void removeListener(@NotNull L listener) {
        synchronized (listeners) {
            Collections.removeIf(listeners.iterator(), new ReferencePredicate<R, L>(listener));
        }
    }

    @Override
    @NotNull
    public List<L> getListeners() {
        final List<L> result;

        synchronized (listeners) {
            result = new ArrayList<L>(listeners.size());

            // copy listeners and remove garbage collected references
            for ( Iterator<R> it = listeners.iterator(); it.hasNext();  ) {
                final R r = it.next();
                final L l = r.get();
                if ( l == null ) {
                    it.remove();
                } else {
                    result.add(l);
                }
            }
        }

        return result;
    }

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    private static class ReferencePredicate<R extends Reference<L>, L> implements JPredicate<R> {

        @NotNull
        private final L l;

        public ReferencePredicate(@NotNull L l) {
            this.l = l;
        }

        @Override
        public boolean apply(@Nullable R r) {
            final L l = r != null ? r.get() : null;
            return this.l.equals(l);
        }
    }

    public static interface ReferenceProducer<R extends Reference<L>, L> {

        @NotNull
        R newReference(@NotNull L listener);
    }

    private static class WeakReferenceProducer<L> implements ReferenceProducer<WeakReference<L>, L> {

        @NotNull
        @Override
        public WeakReference<L> newReference(@NotNull L listener) {
            return new WeakReference<L>(listener);
        }
    }

    private static class HardReferenceProducer<L> implements ReferenceProducer<HardReference<L>, L> {

        @NotNull
        @Override
        public HardReference<L> newReference(@NotNull L listener) {
            return new HardReference<L>(listener);
        }
    }

}
