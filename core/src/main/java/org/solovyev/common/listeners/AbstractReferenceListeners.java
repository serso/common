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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Solovyev_S
 * Date: 20.09.12
 * Time: 16:43
 */
public class AbstractReferenceListeners<L> implements IListeners<L> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final List<WeakReference<L>> listeners = new ArrayList<WeakReference<L>>();

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private AbstractReferenceListeners() {
    }

    @NotNull
    public static <L> IListeners<L> newInstance() {
        return new AbstractReferenceListeners<L>();
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
            boolean contains = Collections.contains(listeners, FilterType.included, new WeakReferencePredicate<L>(listener));

            if (!contains) {
                listeners.add(new WeakReference<L>(listener));
            }
        }
    }

    @Override
    public void removeListener(@NotNull L listener) {
        synchronized (listeners) {
            Collections.removeIf(listeners.iterator(), new WeakReferencePredicate<L>(listener));
        }
    }

    @Override
    @NotNull
    public List<L> getListeners() {
        final List<L> result;

        synchronized (listeners) {
            result = new ArrayList<L>(listeners.size());

            // copy listeners and remove garbage collected references
            for ( Iterator<WeakReference<L>> it = listeners.iterator(); it.hasNext();  ) {
                final WeakReference<L> r = it.next();
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

    private static class WeakReferencePredicate<T> implements JPredicate<WeakReference<T>> {

        @NotNull
        private final T t;

        public WeakReferencePredicate(@NotNull T t) {
            this.t = t;
        }

        @Override
        public boolean apply(@Nullable WeakReference<T> r) {
            final T t = r != null ? r.get() : null;
            return this.t.equals(t);
        }
    }
}
