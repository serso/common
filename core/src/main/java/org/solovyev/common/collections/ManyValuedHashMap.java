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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * User: serso
 * Date: 03.07.2009
 * Time: 13:33:23
 */

/**
 * in ManyValuedMap can be stored lists of elements identificated by some key
 */
public class ManyValuedHashMap<K, V> implements ManyValuedMap<K, V>, Serializable {

    //linked hash map guarantees right key order
    private Map<K, List<V>> map = new LinkedHashMap<K, List<V>>();
    private boolean isNeedToCheckUniqueness;
    private boolean lock = false;

    /**
     * @param needToCheckUniqueness if true then every time when element added uniqueness will be checked
     */
    public ManyValuedHashMap(boolean needToCheckUniqueness) {
        isNeedToCheckUniqueness = needToCheckUniqueness;
    }

    public ManyValuedHashMap() {
        this(false);
    }

    public ManyValuedHashMap<K, V> put2(K key, List<V> newValues) {
        put(key, newValues);
        return this;
    }

    public List<V> put(K key, List<V> newValues) {
        if (newValues == null) {
            return put(key, (V) null);
        } else if (newValues.isEmpty()) {
            return put(key, (V) null);
        } else {
            //noinspection unchecked
            return put(key, (V[]) newValues.toArray());
        }
    }

    public List<V> put(K key, V... newValues) {
        checkLock();
        List<V> curValues = null;
        if (newValues != null && key != null) {
            curValues = this.map.get(key);

            if (curValues == null) {
                //new values  - add
                curValues = new ArrayList<V>();
                curValues.addAll(Arrays.asList(newValues));
                this.map.put(key, curValues);
            } else {
                // for this key values were added
                if (isNeedToCheckUniqueness) {
                    //if is need to check uniqueness - check
                    Integer index;
                    for (V newValue : newValues) {
                        index = null;
                        for (int i = 0; i < curValues.size(); i++) {
                            if (curValues.get(i).equals(newValue)) {
                                index = i;
                                break;
                            }
                        }
                        if (index == null) {
                            curValues.add(newValue);
                        } /*else {
                            //no need to add
                            //this value is already stored in map
                        }*/
                    }
                } else {
                    //otherwise add
                    curValues.addAll(Arrays.asList(newValues));
                }
            }
        } else if (key != null) {
            curValues = this.map.get(key);

            if (curValues == null) {
                curValues = new ArrayList<V>();
                this.map.put(key, curValues);
            }
        }

        return curValues;
    }

    public boolean containsValue(Object value) {
        boolean result = false;
        for (List<V> values : this.map.values()) {
            for (V v : values) {
                if (v.equals(value)) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    public List<V> get(Object key) {
        return this.map.get(key);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public int size() {
        int size = 0;
        for (List<V> vs : map.values()) {
            size += vs.size();
        }
        return size;
    }

    public List<V> remove(Object key) {
        checkLock();
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> m) {
        checkLock();
        this.map.putAll(m);
    }

    public void clear() {
        checkLock();
        this.map.clear();
    }

    @Override
    public void clear(K key) {
        checkLock();
        List<V> curValues = this.map.get(key);
        if (curValues != null) {
            curValues.clear();
        }
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public Collection<List<V>> values() {
        return this.map.values();
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    public Map<K, List<V>> toMap() {
        return new HashMap<K, List<V>>(map);
    }

    @Override
    public ManyValuedHashMap<K, V> clone() {
        ManyValuedHashMap<K, V> clone = null;
        try {
            //noinspection unchecked
            clone = (ManyValuedHashMap<K, V>) super.clone();
            //IMPORTANT: NOT DEEP CLONE
            //noinspection unchecked
            clone.map = new LinkedHashMap<K, List<V>>();
            clone.map.putAll(this.map);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
        return clone;
    }

    @Override
    public void sort(Comparator<? super V> c) {
        for (List<V> list : map.values()) {
            Collections.sort(list, c);
        }
    }

    @Override
    public List<V> getAllValues() {
        final List<V> result = new ArrayList<V>();
        for (List<V> list : map.values()) {
            result.addAll(list);
        }
        return result;
    }

    public Collection<List<V>> values(Comparator<? super K> c) {
        List<Map.Entry<K, List<V>>> entries = new ArrayList<Map.Entry<K, List<V>>>(entrySet());

        Collections.sort(entries, new EntryComparator(c));

        Collection<List<V>> result = new ArrayList<List<V>>();

        for (Map.Entry<K, List<V>> entry : entries) {
            result.add(entry.getValue());
        }

        return result;
    }

    private class EntryComparator implements Comparator<Map.Entry<K, List<V>>> {

        private Comparator<? super K> keyComparator = null;

        private EntryComparator(Comparator<? super K> keyComparator) {
            this.keyComparator = keyComparator;
        }

        @Override
        public int compare(Map.Entry<K, List<V>> o1, Map.Entry<K, List<V>> o2) {
            return keyComparator.compare(o1.getKey(), o2.getKey());
        }
    }

    @Override
    public void lock() {
        this.lock = true;
    }

    private void checkLock() {
        if (this.lock) {
            throw new UnsupportedOperationException();
        }
    }
}
