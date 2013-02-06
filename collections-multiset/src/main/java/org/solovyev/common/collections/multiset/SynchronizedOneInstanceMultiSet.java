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

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 4:17 PM
 */
public class SynchronizedOneInstanceMultiSet<E> extends SynchronizedMultiSet<E> implements OneInstanceMultiSet<E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private SynchronizedOneInstanceMultiSet(@NotNull OneInstanceMultiSet<E> delegate) {
        super(delegate);
    }

    private SynchronizedOneInstanceMultiSet(@NotNull OneInstanceMultiSet<E> delegate, @NotNull Object mutex) {
        super(delegate, mutex);
    }

    @NotNull
    public static <E> SynchronizedOneInstanceMultiSet<E> wrap(@NotNull OneInstanceMultiSet<E> delegate) {
        return new SynchronizedOneInstanceMultiSet<E>(delegate);
    }

    @NotNull
    public static <E> SynchronizedOneInstanceMultiSet<E> wrap(@NotNull OneInstanceMultiSet<E> delegate, @NotNull Object mutex) {
        return new SynchronizedOneInstanceMultiSet<E>(delegate, mutex);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

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