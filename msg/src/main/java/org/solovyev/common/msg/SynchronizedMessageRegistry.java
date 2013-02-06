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

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.SynchronizedObject;

class SynchronizedMessageRegistry extends SynchronizedObject<MessageRegistry> implements MessageRegistry {

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
