package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 4:02 PM
 */
public abstract class SynchronizedObject {

    @NotNull
    private final Object delegate;

    @NotNull
    protected final Object mutex;

    public SynchronizedObject(@NotNull Object delegate) {
        this.delegate = delegate;
        this.mutex = this;
    }

    public SynchronizedObject(@NotNull Object delegate, @NotNull Object mutex) {
        this.delegate = delegate;
        this.mutex = mutex;
    }

    @NotNull
    protected Object delegate() {
        return delegate;
    }

    // for manually synchronization it is allows to use mutex
    @NotNull
    public Object getMutex() {
        return mutex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SynchronizedObject)) return false;

        SynchronizedObject that = (SynchronizedObject) o;

        synchronized (mutex) {
            if (!delegate.equals(that.delegate)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        synchronized (mutex) {
            return delegate.hashCode();
        }
    }

    @Override
    public String toString() {
        synchronized (mutex) {
            return delegate.toString();
        }
    }
}
