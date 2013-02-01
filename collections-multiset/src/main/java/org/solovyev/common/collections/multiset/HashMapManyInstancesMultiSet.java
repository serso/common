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

import java.util.HashMap;
import java.util.List;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:45 PM
 */
public class HashMapManyInstancesMultiSet<E> extends AbstractMapManyInstancesMultiSet<E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private HashMapManyInstancesMultiSet() {
        super(new HashMap<E, List<E>>());
    }

    private HashMapManyInstancesMultiSet(int capacity) {
        super(new HashMap<E, List<E>>(capacity));
    }

    private HashMapManyInstancesMultiSet(int capacity, float loadFactor) {
        super(new HashMap<E, List<E>>(capacity, loadFactor));
    }

    @NotNull
    public static <E> ManyInstancesMultiSet<E> newInstance() {
        return new HashMapManyInstancesMultiSet<E>();
    }

    @NotNull
    public static <E> ManyInstancesMultiSet<E> newInstance(int capacity) {
        return new HashMapManyInstancesMultiSet<E>(capacity);
    }

    @NotNull
    public static <E> ManyInstancesMultiSet<E> newInstance(int capacity, float loadFactor) {
        return new HashMapManyInstancesMultiSet<E>(capacity, loadFactor);
    }
}
