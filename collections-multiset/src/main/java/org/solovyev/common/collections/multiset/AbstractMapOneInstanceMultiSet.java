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
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 1:49 PM
 */
public class AbstractMapOneInstanceMultiSet<E> extends AbstractMultiSet<E> implements OneInstanceMultiSet<E> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final Map<E, Value<E>> backingMap;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    protected AbstractMapOneInstanceMultiSet(@NotNull Map<E, Value<E>> backingMap) {
        this.backingMap = backingMap;
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public int remove(@Nullable E e, int count) {
        MultiSets.checkRemove(count);

        final int oldCount = count(e);

        if (oldCount > count) {
            setCount(e, oldCount - count);
        } else {
            this.backingMap.remove(e);
        }

        return oldCount;
    }

    @Override
    public int setCount(E e, int count) {
        MultiSets.checkSetCount(count);

        final Value<E> oldValue = this.backingMap.put(e, new Value<E>(e, count));

        if (oldValue == null) {
            return 0;
        } else {
            return oldValue.count;
        }
    }

    @Override
    public int count(E e) {
        return count0(e);
    }

    private int count0(Object e) {
        final Value<E> value = this.backingMap.get(e);
        return value == null ? 0 : value.count;
    }

    @NotNull
    @Override
    public Collection<E> getAll(E e) {
        final Value<E> value = this.backingMap.get(e);

        if (value == null) {
            return Collections.emptyList();
        } else {
            return getAsList(value);
        }
    }

    @NotNull
    private Collection<E> getAsList(@NotNull Value<E> value) {
        final Object[] array = getAsArray(value);
        return (Collection<E>) Arrays.asList(array);
    }

    @NotNull
    private E[] getAsArray(@NotNull Value<E> value) {
        final Object[] result = new Object[value.count];
        Arrays.fill(result, value.element);
        return (E[]) result;
    }

    @NotNull
    @Override
    public Set<E> toElementSet() {
        final Set<E> result = new HashSet<E>(this.backingMap.size());

        for (Value<E> value : this.backingMap.values()) {
            if (value.count > 0) {
                result.add(value.element);
            }
        }

        return result;
    }

    @Override
    public boolean add(E e, int count) {
        MultiSets.checkAdd(count);

        final Value<E> oldValue = this.backingMap.get(e);
        if (oldValue == null) {
            this.backingMap.put(e, new Value<E>(e, count));
        } else {
            this.backingMap.put(e, new Value<E>(e, oldValue.count + count));
        }

        return count > 0;
    }

    @Override
    public int size() {
        int result = 0;

        for (Value<E> v : backingMap.values()) {
            result += v.count;
        }

        return result;
    }

    @Override
    public boolean contains(Object e) {
        return count0(e) > 0;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new MapBasedMultiSetIterator();
    }

    private class MapBasedMultiSetIterator implements Iterator<E> {

        private final Iterator<Map.Entry<E, Value<E>>> entryIterator;
        private Map.Entry<E, Value<E>> currentEntry;
        private int occurrencesLeft;
        private boolean canRemove;

        MapBasedMultiSetIterator() {
            this.entryIterator = backingMap.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return occurrencesLeft > 0 || entryIterator.hasNext();
        }

        @Override
        public E next() {
            if (occurrencesLeft == 0) {
                currentEntry = entryIterator.next();
                occurrencesLeft = currentEntry.getValue().count;
            }
            occurrencesLeft--;
            canRemove = true;
            return currentEntry.getKey();
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("No calls to next() since the last call to remove()");
            }

            int count = currentEntry.getValue().count;
            if (count <= 0) {
                throw new ConcurrentModificationException();
            }

            if (currentEntry.getValue().addAndGetCount(-1) == 0) {
                entryIterator.remove();
            }
            canRemove = false;
        }
    }

    @NotNull
    @Override
    public Object[] toArray() {
        final Object[] result = new Object[size()];

        int j = 0;
        for (Value<E> value : backingMap.values()) {
            for (int i = 0; i < value.count; i++) {
                result[j++] = value.element;
            }
        }

        return result;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] result) {

        int j = 0;
        for (Value<E> value : backingMap.values()) {
            for (int i = 0; i < value.count; i++) {
                result[j++] = (T) value.element;
            }
        }

        return result;
    }

    @Override
    public void clear() {
        this.backingMap.clear();
    }

    protected static class Value<E> {

        @NotNull
        private E element;

        @NotNull
        private Integer count;

        private Value(@NotNull E element, @NotNull Integer count) {
            this.element = element;
            this.count = count;
        }

        @NotNull
        public Integer addAndGetCount(int offset) {
            this.count += offset;
            return this.count;
        }
    }


}
