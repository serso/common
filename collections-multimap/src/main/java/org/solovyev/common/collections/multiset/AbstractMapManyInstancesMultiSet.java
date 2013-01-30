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

import java.util.*;

/**
 * User: serso
 * Date: 7/9/12
 * Time: 6:28 PM
 */
abstract class AbstractMapManyInstancesMultiSet<E> extends AbstractMultiSet<E> implements ManyInstancesMultiSet<E> {

    @NotNull
    private final Map<E, List<E>> backingMap;

    protected AbstractMapManyInstancesMultiSet(@NotNull Map<E, List<E>> backingMap) {
        this.backingMap = backingMap;
    }

    @Override
    public int count(E e) {
        return get(e).size();
    }

    // always returns unmodifiable list
    @NotNull
    private List<E> get(E e) {
        final List<E> list = backingMap.get(e);
        return list == null ? Collections.<E>emptyList() : Collections.unmodifiableList(list);
    }

    @NotNull
    @Override
    public Collection<E> getAll(E e) {
        return get(e);
    }

    @NotNull
    @Override
    public Set<E> toElementSet() {
        final Set<E> result = new HashSet<E>();

        for (Map.Entry<E, List<E>> entry : backingMap.entrySet()) {
            final List<E> list = entry.getValue();
            if (list != null && !list.isEmpty()) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    @Override
    public boolean add(E e, int count) {
        MultiSets.checkAdd(count);

        final List<E> oldList = get(e);

        final List<E> newList = new ArrayList<E>(oldList);
        for (int i = 0; i < count; i++) {
            newList.add(e);
        }

        this.backingMap.put(e, newList);

        return count > 0;
    }

    @Override
    public int remove(E e, int count) {
        MultiSets.checkRemove(count);

        final List<E> list = backingMap.get(e);

        final int result = list == null ? 0 : list.size();

        int i = 0;
        if (list != null) {
            for (Iterator<E> it = list.iterator(); it.hasNext() && i < count; i++) {
                it.next();
                it.remove();
            }
        }

        return result;
    }

    @Override
    public int size() {
        int result = 0;

        for (List<E> list : backingMap.values()) {
            result += list.size();
        }

        return result;
    }

    @Override
    public boolean contains(Object o) {
        return !get((E) o).isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return new ValueIterator();
    }

    private class ValueIterator implements Iterator<E> {
        private final Iterator<Map.Entry<E, List<E>>> keyIterator;
        private E key;
        private Collection<E> collection;
        private Iterator<E> valueIterator;

        private ValueIterator() {
            keyIterator = backingMap.entrySet().iterator();
            if (keyIterator.hasNext()) {
                findValueIteratorAndKey();
            } else {
                valueIterator = (Iterator<E>) MultiSets.EMPTY_MODIFIABLE_ITERATOR;
            }
        }

        void findValueIteratorAndKey() {
            Map.Entry<E, List<E>> entry = keyIterator.next();
            key = entry.getKey();
            collection = entry.getValue();
            valueIterator = collection.iterator();
        }

        @Override
        public boolean hasNext() {
            return keyIterator.hasNext() || valueIterator.hasNext();
        }

        @Override
        public E next() {
            if (!valueIterator.hasNext()) {
                findValueIteratorAndKey();
            }

            return valueIterator.next();
        }

        @Override
        public void remove() {
            valueIterator.remove();
            if (collection.isEmpty()) {
                keyIterator.remove();
            }
        }
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }
}
