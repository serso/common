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
final class ReferenceListeners<R extends Reference<L>, L> implements JListeners<L> {

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
    public static <L> ReferenceListeners<WeakReference<L>, L> newHardReferenceInstance() {
        return new ReferenceListeners<WeakReference<L>, L>(new HardReferenceProducer<L>());
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public boolean addListener(@NotNull final L listener) {
        synchronized (listeners) {
            boolean contains = Collections.contains(listeners, FilterType.included, new ReferencePredicate<R, L>(listener));

            if (!contains) {
                listeners.add(referenceProducer.newReference(listener));
            }

            return !contains;
        }
    }

    @Override
    public boolean removeListener(@NotNull final L listener) {
        synchronized (listeners) {
            if (referenceProducer instanceof HardReferenceProducer) {
                return Collections.removeIf(listeners.iterator(), new JPredicate<R>() {
                    @Override
                    public boolean apply(@Nullable R r) {
                        final L l = r != null ? r.get() : null;
                        boolean removed = listener.equals(l);
                        if ( removed ) {
                            // we must clean reference in order to avoid memory leak
                            ((HardReferenceProducer) referenceProducer).remove((WeakReference) r);
                        }
                        return removed;
                    }
                });
            } else {
                return Collections.removeIf(listeners.iterator(), new ReferencePredicate<R, L>(listener));
            }
        }
    }

    @Override
    public void removeAll() {
        synchronized (listeners) {

            if (referenceProducer instanceof HardReferenceProducer) {
                ((HardReferenceProducer) referenceProducer).removeAll();
            }

            listeners.clear();
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

    @NotNull
    @Override
    public <LE extends L> List<LE> getListenersOfType(@NotNull Class<LE> type) {
        final List<L> listeners = getListeners();

        final List<LE> result = new ArrayList<LE>(listeners.size());
        for (L listener : listeners) {
           if (type.isAssignableFrom(listener.getClass())) {
                result.add((LE) listener);
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

    private static class WeakReferenceProducer<L> implements ReferenceProducer<WeakReference<L>, L> {

        @NotNull
        @Override
        public WeakReference<L> newReference(@NotNull L listener) {
            return new WeakReference<L>(listener);
        }
    }

    private static class HardReferenceProducer<L> implements ReferenceProducer<WeakReference<L>, L> {

        @NotNull
        private final List<ReferenceHolder<L>> references = new ArrayList<ReferenceHolder<L>>();

        @NotNull
        @Override
        public WeakReference<L> newReference(@NotNull L listener) {
            final WeakReference<L> result = new WeakReference<L>(listener);

            // store hard reference to prevent GC
            references.add(ReferenceHolder.newInstance(result, listener));

            return result;
        }

        public boolean remove(@NotNull final WeakReference<L> reference) {
            return Collections.removeIf(references.iterator(), new JPredicate<ReferenceHolder<L>>() {
                @Override
                public boolean apply(@Nullable ReferenceHolder<L> referenceHolder) {
                    return referenceHolder != null && referenceHolder.reference == reference;
                }
            });
        }

        public void removeAll() {
            references.clear();
        }

        private static class ReferenceHolder<R> {
            @NotNull
            private final WeakReference<R> reference;

            // do not remove: this object should be stored here in order not to be garbage collected
            @NotNull
            private final R referent;

            private ReferenceHolder(@NotNull WeakReference<R> reference, @NotNull R referent) {
                this.reference = reference;
                this.referent = referent;
            }


            private static <R> ReferenceHolder<R> newInstance(@NotNull WeakReference<R> reference, @NotNull R referent) {
                return new ReferenceHolder<R>(reference, referent);
            }
        }
    }

}
