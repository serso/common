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

import javax.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 2:25 PM
 */
public class HashMapOneInstanceMultiSet<E> extends AbstractMapOneInstanceMultiSet<E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTOR
    *
    **********************************************************************
    */

    private HashMapOneInstanceMultiSet() {
        super(new HashMap<E, Value<E>>());
    }

    private HashMapOneInstanceMultiSet(int capacity) {
        super(new HashMap<E, Value<E>>(capacity));
    }

    private HashMapOneInstanceMultiSet(Map<? extends E, ? extends Value<E>> m) {
        super(new HashMap<E, Value<E>>(m));
    }

    @Nonnull
    public static <E> HashMapOneInstanceMultiSet<E> newInstance() {
        return new HashMapOneInstanceMultiSet<E>();
    }

    @Nonnull
    public static <E> HashMapOneInstanceMultiSet<E> newInstance(int capacity) {
        return new HashMapOneInstanceMultiSet<E>(capacity);
    }

    @Nonnull
    public static <E> HashMapOneInstanceMultiSet<E> from(@Nonnull Map<? extends E, ? extends Value<E>> m) {
        return new HashMapOneInstanceMultiSet<E>(m);
    }
}
