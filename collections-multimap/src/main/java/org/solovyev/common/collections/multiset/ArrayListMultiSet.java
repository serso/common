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

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: serso
 * Date: 7/6/12
 * Time: 1:37 PM
 */
public class ArrayListMultiSet<E> extends AbstractListMultiSet<E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private ArrayListMultiSet() {
        super(new ArrayList<E>());
    }

    private ArrayListMultiSet(int capacity) {
        super(new ArrayList<E>(capacity));
    }

    private ArrayListMultiSet(@NotNull Collection<? extends E> c) {
        super(new ArrayList<E>(c));
    }

    @NotNull
    public static <E> ManyInstancesMultiSet<E> newInstance() {
        return new ArrayListMultiSet<E>();
    }

    public static <E> ManyInstancesMultiSet<E> newInstance(int capacity) {
        return new ArrayListMultiSet<E>(capacity);
    }

    public static <E> ManyInstancesMultiSet<E> from(@NotNull Collection<? extends E> c) {
        return new ArrayListMultiSet<E>(c);
    }
}
