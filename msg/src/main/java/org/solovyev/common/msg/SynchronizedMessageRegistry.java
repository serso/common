package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.SynchronizedObject;

public class SynchronizedMessageRegistry extends SynchronizedObject<MessageRegistry> implements MessageRegistry {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */
    private SynchronizedMessageRegistry(@NotNull MessageRegistry delegate) {
        super(delegate);
    }

    private SynchronizedMessageRegistry(@NotNull MessageRegistry delegate, @NotNull Object mutex) {
        super(delegate, mutex);
    }

    @NotNull
    public static MessageRegistry wrap(@NotNull MessageRegistry delegate) {
        return new SynchronizedMessageRegistry(delegate);
    }

    @NotNull
    public static MessageRegistry wrap(@NotNull MessageRegistry delegate, @NotNull Object mutex) {
        return new SynchronizedMessageRegistry(delegate, mutex);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public void addMessage(@NotNull Message message) {
        synchronized (this.mutex) {
            delegate().addMessage(message);
        }
    }

    @Override
    public boolean hasMessage() {
        synchronized (this.mutex) {
            return delegate().hasMessage();
        }
    }

    @NotNull
    @Override
    public Message getMessage() {
        synchronized (this.mutex) {
            return delegate().getMessage();
        }
    }
}
