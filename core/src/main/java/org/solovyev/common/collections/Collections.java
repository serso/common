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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.solovyev.common.Identifiable;
import org.solovyev.common.JPredicate;
import org.solovyev.common.Selectable;
import org.solovyev.common.equals.Equalizer;
import org.solovyev.common.equals.EqualsFinder;
import org.solovyev.common.filter.FilterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:54:18 PM
 */
public class Collections {

    protected Collections() {
        throw new AssertionError();
    }

    @Nonnull
    public static String toString(@Nonnull Collection<Object> objects, @Nonnull String separator) {
        StringBuilder sb = new StringBuilder();

        LoopData ld = new LoopData(objects);
        for (Object o : objects) {
            if (ld.isFirstAndNext()) {
                sb.append(separator);
            }
            sb.append(o);
        }

        return sb.toString();
    }

    public static boolean notEmpty(@Nullable Collection<?> c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(@Nullable Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(@Nullable Object[] array) {
        return array == null || array.length == 0;
    }

    @Nonnull
    public static <T> Collection<T> setNotNull(@Nullable Collection<T> c, @Nonnull Collection<T> defaultValue) {
        Collection<T> result;

        if (c != null) {
            result = c;
        } else {
            defaultValue.clear();
            result = defaultValue;
        }

        return result;
    }

    public static <T> void addUnique(@Nonnull List<T> list, @Nonnull List<T> list2) {
        for (T t : list2) {
            addUnique(list, t);
        }
    }

    public static <T> void addUnique(@Nonnull List<T> list, @Nonnull T object) {
        boolean isFound = false;

        for (T t : list) {
            isFound = t.equals(object);
            if (isFound) {
                break;
            }
        }

        if (!isFound) {
            list.add(object);
        }
    }

    @Nonnull
    public static <T> List<Selectable<T>> selectable(@Nonnull List<T> list) {
        final List<Selectable<T>> result = new ArrayList<Selectable<T>>();

        for (T t : list) {
            result.add(new Selectable<T>(t));
        }

        return result;
    }

    /**
     * Method finds the subtraction of two maps
     *
     * @param m1  first map
     * @param m2  second map
     * @param <K> key
     * @param <V> value
     * @return map which equals first map subtracted by the second map
     */
    public static <K, V> Map<K, V> subtract(final Map<K, V> m1, final Map<K, V> m2) {
        final Map<K, V> result = new HashMap<K, V>();

        V value;
        for (Map.Entry<K, V> entry1 : m1.entrySet()) {
            value = m2.get(entry1.getKey());
            if (value == null) {
                result.put(entry1.getKey(), entry1.getValue());
            }
        }

        return result;
    }

    /**
     * Method returns first list element.
     *
     * @param list list of elements
     * @param <T>  type of element in list
     * @return null if list is null, or if list is empty, first element otherwise
     */
    @Nullable
    public static <T> T getFirstListElement(List<? extends T> list) {
        T result = null;

        if (!isEmpty(list)) {
            result = list.get(0);
        }

        return result;
    }

    /**
     * Method returns any first collection element.
     *
     * @param collection collection of elements
     * @param <T>        type of element in collection
     * @return null if collection is null, or if collections is empty, any first element otherwise
     */
    @Nullable
    public static <T> T getFirstCollectionElement(Collection<? extends T> collection) {
        T result = null;

        if (!isEmpty(collection)) {
            for (T t : collection) {
                result = t;
                break;
            }
        }

        return result;
    }

    /**
     * Method returns last list element.
     *
     * @param list list of elements
     * @param <T>  type of element in list
     * @return null if list is null, or if list is empty, last element otherwise
     */
    public static <T> T getLastListElement(List<? extends T> list) {
        T result = null;

        if (!isEmpty(list)) {
            result = list.get(list.size() - 1);
        }

        return result;
    }


    public static <T> void removeEntriesByKeys(Map<T, ?> map, List<T> keys) {
        for (T object : keys) {
            map.remove(object);
        }
    }


    /**
     * @param map map of objects
     * @return 'true' if represented map is empty or null, 'false' otherwise
     */
    public static boolean isEmpty(@Nullable Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * Method returns set of keys for specified value (by equals())
     *
     * @param map   map
     * @param value value
     * @param <T>   key type
     * @param <E>   value type
     * @return set of keys referred to specified value
     */
    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        final Set<T> keys = new HashSet<T>();

        if (map != null) {
            for (Map.Entry<T, E> entry : map.entrySet()) {
                if (entry.getValue().equals(value)) {
                    keys.add(entry.getKey());
                }
            }
        }

        return keys;
    }


    public static <T> boolean contains(@Nonnull T value, @Nullable Collection<T> list, @Nonnull Equalizer<T> equalizer) {
        return contains(value, list, FilterType.included, equalizer);
    }

    public static <T> boolean notContains(@Nonnull T value, @Nullable Collection<T> list, @Nonnull Equalizer<T> equalizer) {
        return contains(value, list, FilterType.excluded, equalizer);
    }

    public static <T> boolean contains(@Nonnull T value, @Nullable Collection<T> list, @Nonnull FilterType filterType, @Nullable Equalizer<T> equalizer) {
        return contains(list, filterType, new EqualsFinder<T>(value, equalizer));
    }

    public static <T> boolean contains(@Nullable Collection<T> list, @Nonnull FilterType filterType, @Nonnull JPredicate<T> finder) {
        boolean found = find(list, finder) != null;

        final boolean result;
        if (filterType == FilterType.included) {
            result = found;
        } else {
            result = !found;
        }

        return result;
    }

    @Nullable
    public static <T> T find(@Nonnull T value, @Nullable Collection<T> list, @Nullable Equalizer<T> equalizer) {
        return find(list, new EqualsFinder<T>(value, equalizer));
    }

    public static <T> T find(@Nullable Collection<T> list, @Nonnull JPredicate<T> finder) {
        T result = null;

        if (!isEmpty(list)) {
            for (T t : list) {
                if (finder.apply(t)) {
                    result = t;
                    break;
                }
            }
        }

        return result;
    }

    @Nullable
    public static <T> T removeFirst(@Nullable Collection<T> list, @Nonnull JPredicate<T> finder) {
        T result = null;

        if (!isEmpty(list)) {
            for (Iterator<T> it = list.iterator(); it.hasNext(); ) {
                final T t = it.next();
                if (finder.apply(t)) {
                    result = t;
                    it.remove();
                    break;
                }
            }
        }

        return result;
    }

    @Nonnull
    public static <T> List<T> removeAll(@Nullable Collection<T> list, @Nonnull JPredicate<T> finder) {
        final List<T> result = new ArrayList<T>();

        if (!isEmpty(list)) {
            for (Iterator<T> it = list.iterator(); it.hasNext(); ) {
                final T t = it.next();
                if (finder.apply(t)) {
                    result.add(t);
                    it.remove();
                }
            }
        }

        return result;
    }

    public static <T> boolean contains(@Nonnull T value, @Nonnull FilterType filterType, @Nullable Collection<T> list) {
        return contains(value, list, filterType, null);
    }

    public static <T> boolean contains(@Nonnull T value, @Nullable Collection<T> list) {
        return contains(value, FilterType.included, list);
    }

    public static <T> boolean contains(@Nonnull T value, @Nullable T... array) {
        return contains(value, Arrays.asList(array));
    }

    public static <T extends Comparable> List<T> toIdsList(@Nonnull Collection<? extends Identifiable<T>> list) {
        final List<T> result = new ArrayList<T>(list.size());

        for (Identifiable<T> t : list) {
            result.add(t.getId());
        }

        return result;
    }

    public static List<String> toStringList(@Nonnull List<? extends Enum> idsList) {
        final List<String> result = new ArrayList<String>();

        for (Enum enumElement : idsList) {
            result.add(enumElement.name());
        }

        return result;
    }

    @Nonnull
    public static <T> List<T> asList(T... ts) {
        final List<T> result = new ArrayList<T>();

        if (!Collections.isEmpty(ts)) {
            for (T t : ts) {
                result.add(t);
            }
        }

        return result;
    }

    public static <T, V> SortedMap<T, V> toSortedMap(@Nonnull Map<T, V> map, @Nullable Comparator<? super T> comparator) {
        final SortedMap<T, V> result;

        if (comparator != null) {
            result = new TreeMap<T, V>(comparator);
        } else {
            result = new TreeMap<T, V>();
        }

        result.putAll(map);

        return result;
    }

    public static <T> Iterable<T> reversed(@Nonnull List<T> list) {
        return new Reversed<T>(list);
    }

    public static <T> Iterable<T> reversed(@Nonnull T[] array) {
        return new Reversed<T>(Arrays.asList(array));
    }

    // copied from guava: com.google.common.collect.Iterators.removeIf()
    public static <T> boolean removeIf(@Nonnull Iterator<T> removeFrom,
                                       @Nonnull JPredicate<? super T> predicate) {
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (predicate.apply(removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Nullable
    public static <T> T find(@Nonnull Iterator<T> iterator,
                             @Nonnull JPredicate<? super T> predicate) {
        while (iterator.hasNext()) {
            final T next = iterator.next();
            if (predicate.apply(next)) {
                return next;
            }
        }

        return null;
    }


    public static class Reversed<T> implements Iterable<T> {

        @Nonnull
        private final List<T> original;

        public Reversed(@Nonnull List<T> original) {
            this.original = original;
        }

        public Iterator<T> iterator() {
            final ListIterator<T> i = original.listIterator(original.size());

            return new Iterator<T>() {
                public boolean hasNext() {
                    return i.hasPrevious();
                }

                public T next() {
                    return i.previous();
                }

                public void remove() {
                    i.remove();
                }
            };
        }
    }

    @Nonnull
    public static <T> T[] concat(@Nonnull T[] first, @Nonnull T[] second) {
        final T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Nonnull
    public static byte[] concat(@Nonnull byte[] first, @Nonnull byte[] second) {
        final byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Nonnull
    public static <E> List<List<E>> split(@Nonnull List<E> list, int chunkSize) {
        final int size = list.size();

        final List<List<E>> result = new ArrayList<List<E>>(size / chunkSize + 1);

        int i = 0;
        int l = 0;
        int r = chunkSize;
        while (r <= size) {
            result.add(list.subList(l, r));

            // update step
            i++;
            l = i * chunkSize;
            r = (i + 1) * chunkSize;
        }

        // if something left - add not full chunk
        if (l < size) {
            result.add(list.subList(l, size));
        }

        return result;
    }
}

