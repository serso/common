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

import java.util.*;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:02 PM
 */
public abstract class AbstractMultiSet<E> extends AbstractCollection<E> implements MultiSet<E> {

    @Override
    public boolean add(E e) {
        return this.add(e, 1);
    }

    @Override
    public boolean remove(Object o) {
        // if number of elements before was positive then remove() method call must change the collection
        return this.remove((E) o, 1) > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (Object o : c) {
            modified = modified | this.remove(o);
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;

        for (E e : toElementSet()) {
            if (!c.contains(e)) {
                modified = modified | remove(e);
            }
        }

        return modified;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MultiSet) {
            final MultiSet that = (MultiSet) obj;

            if (this.size() == that.size()) {
                boolean equals = true;

                for (E e : this) {
                    if (this.count(e) != that.count(e)) {
                        equals = false;
                        break;
                    }
                }

                return equals;
            }
        }


        return false;
    }

    @Override
    public int hashCode() {

        // not good solution but still better than nothing.
        // Problem: set doesn't require any particular order of it's elements, for equals method it is not significant information
        // however hashCode method depends on each implementation of multiset and use the order of elements returned by iterator
        // Solution (used): get all the hash codes of all elements in the multiset, sort them and then sum ()
        // Solution (not used): demand from the subclass specific order of it's elements

        final List<Integer> hashCodes = new ArrayList<Integer>(this.size());
        for (E e : this) {
            hashCodes.add(e == null ? 0 : e.hashCode());
        }

        Collections.sort(hashCodes);

        int result = 1;

        for (Integer hashCode : hashCodes) {
            result = 31 * result + hashCode;
        }

        return result;
    }
}
