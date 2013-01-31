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

package org.solovyev.common.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class UnmodifiableIterator<T> implements Iterator<T> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */
    @NotNull
    private final Iterator<? extends T> i;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private UnmodifiableIterator(@NotNull Iterator<? extends T> i) {
        this.i = i;
    }

    public static <T> UnmodifiableIterator<T> wrap(@NotNull Iterator<? extends T> i) {
        return new UnmodifiableIterator<T>(i);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public boolean hasNext() {
        return i.hasNext();
    }

    @Override
    public T next() {
        return i.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
