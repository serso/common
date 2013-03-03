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

package org.solovyev.common.collections.multiset;

import org.solovyev.common.SynchronizedObject;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 4:10 PM
 */
public class SynchronizedMultiSet<E> extends SynchronizedObject<MultiSet<E>> implements MultiSet<E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    protected SynchronizedMultiSet(@Nonnull MultiSet<E> delegate) {
        super(delegate);
    }

    protected SynchronizedMultiSet(@Nonnull MultiSet<E> delegate, @Nonnull Object mutex) {
        super(delegate, mutex);
    }

    @Nonnull
    public static <E> SynchronizedMultiSet<E> wrap(@Nonnull MultiSet<E> delegate) {
        return new SynchronizedMultiSet<E>(delegate);
    }

    @Nonnull
    public static <E> SynchronizedMultiSet<E> wrap(@Nonnull MultiSet<E> delegate, @Nonnull Object mutex) {
        return new SynchronizedMultiSet<E>(delegate, mutex);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public int count(E e) {
        synchronized (mutex) {
            return delegate.count(e);
        }
    }

    @Nonnull
    @Override
    public Collection<E> getAll(E e) {
        synchronized (mutex) {
            return delegate.getAll(e);
        }
    }

    @Nonnull
    @Override
    public Set<E> toElementSet() {
        synchronized (mutex) {
            return delegate.toElementSet();
        }
    }

    @Override
    public boolean add(E e, int count) {
        synchronized (mutex) {
            return delegate.add(e, count);
        }
    }

    @Override
    public int remove(E e, int count) {
        synchronized (mutex) {
            return delegate.remove(e, count);
        }
    }

    @Override
    public int size() {
        synchronized (mutex) {
            return delegate.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (mutex) {
            return delegate.isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (mutex) {
            return delegate.contains(o);
        }
    }

    // must be manually synchronized (or may be NotSupportedOperationException should be thrown)
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        synchronized (mutex) {
            return delegate.iterator();
        }
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        synchronized (mutex) {
            return delegate.toArray();
        }
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        synchronized (mutex) {
            return delegate.toArray(a);
        }
    }

    @Override
    public boolean add(E e) {
        synchronized (mutex) {
            return delegate.add(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (mutex) {
            return delegate.remove(o);
        }
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        synchronized (mutex) {
            return delegate.containsAll(c);
        }
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        synchronized (mutex) {
            return delegate.addAll(c);
        }
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        synchronized (mutex) {
            return delegate.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        synchronized (mutex) {
            return delegate.retainAll(c);
        }
    }

    @Override
    public void clear() {
        synchronized (mutex) {
            delegate.clear();
        }
    }
}
