package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 4:17 PM
 */
public class SynchronizedOneInstanceMultiSet<E> extends SynchronizedMultiSet<E> implements OneInstanceMultiSet<E> {

    public SynchronizedOneInstanceMultiSet(@NotNull OneInstanceMultiSet<E> delegate) {
        super(delegate);
    }

    public SynchronizedOneInstanceMultiSet(@NotNull OneInstanceMultiSet<E> delegate, @NotNull Object mutex) {
        super(delegate, mutex);
    }

    @NotNull
    @Override
    protected OneInstanceMultiSet<E> delegate() {
        return (OneInstanceMultiSet<E>) super.delegate();
    }

    @Override
    public int setCount(E e, int count) {
        synchronized (mutex) {
            return delegate().setCount(e, count);
        }
    }
}
