package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.SynchronizedObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 4:10 PM
 */
public class SynchronizedMultiSet<E> extends SynchronizedObject implements MultiSet<E> {

    public SynchronizedMultiSet(@NotNull MultiSet<E> delegate) {
        super(delegate);
    }

    public SynchronizedMultiSet(@NotNull MultiSet<E> delegate, @NotNull Object mutex) {
        super(delegate, mutex);
    }

    @NotNull
    @Override
    protected MultiSet<E> delegate() {
        return (MultiSet<E>) super.delegate();
    }

    @Override
    public int count(E e) {
        synchronized (mutex) {
            return delegate().count(e);
        }
    }

    @NotNull
    @Override
    public Collection<E> getAll(E e) {
        synchronized (mutex) {
            return delegate().getAll(e);
        }
    }

    @NotNull
    @Override
    public Set<E> toElementSet() {
        synchronized (mutex) {
            return delegate().toElementSet();
        }
    }

    @Override
    public boolean add(E e, int count) {
        synchronized (mutex) {
            return delegate().add(e, count);
        }
    }

    @Override
    public int remove(E e, int count) {
        synchronized (mutex) {
            return delegate().remove(e, count);
        }
    }

    @Override
    public int size() {
        synchronized (mutex) {
            return delegate().size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (mutex) {
            return delegate().isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (mutex) {
            return delegate().contains(o);
        }
    }

    // must be manually synchronized (or may be NotSupportedOperationException should be thrown)
    @Override
    public Iterator<E> iterator() {
        synchronized (mutex) {
            return delegate().iterator();
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (mutex) {
            return delegate().toArray();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronized (mutex) {
            return delegate().toArray(a);
        }
    }

    @Override
    public boolean add(E e) {
        synchronized (mutex) {
            return delegate().add(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (mutex) {
            return delegate().remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (mutex) {
            return delegate().containsAll(c);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronized (mutex) {
            return delegate().addAll(c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (mutex) {
            return delegate().removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (mutex) {
            return delegate().retainAll(c);
        }
    }

    @Override
    public void clear() {
        synchronized (mutex) {
            delegate().clear();
        }
    }
}
