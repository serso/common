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

import org.solovyev.common.Objects;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * User: serso
 * Date: 7/5/12
 * Time: 2:22 PM
 */
class AbstractListMultiSet<E> extends AbstractMultiSet<E> implements ManyInstancesMultiSet<E> {

    @Nonnull
    private final List<E> backingList;

    protected AbstractListMultiSet(@Nonnull List<E> backingList) {
        this.backingList = backingList;
    }

    @Nonnull
    @Override
    public Collection<E> getAll(E e) {
        final List<E> result = new ArrayList<E>();

        for (E el : backingList) {
            if (Objects.areEqual(el, e)) {
                result.add(el);
            }
        }

        return result;
    }

    @Override
    public int count(E e) {
        int result = 0;

        if (e == null) {
            for (E el : backingList) {
                if (el == null) {
                    result++;
                }
            }
        } else {
            for (E el : backingList) {
                if (e.equals(el)) {
                    result++;
                }
            }
        }

        return result;
    }

    @Nonnull
    @Override
    public Set<E> toElementSet() {
        final Set<E> result = new HashSet<E>();

        for (E e : backingList) {
            result.add(e);
        }

        return result;
    }

    @Override
    public boolean add(E e, int count) {
        MultiSets.checkAdd(count);

        boolean result = false;

        for (int i = 0; i < count; i++) {
            result = true;
            this.backingList.add(e);
        }

        return result;
    }

    @Override
    public int remove(E e, int count) {
        MultiSets.checkRemove(count);

        int result = 0;


        if (e == null) {

            for (Iterator<E> it = backingList.iterator(); it.hasNext(); ) {
                final E el = it.next();

                if (el == null) {

                    result++;

                    if (count > 0) {
                        it.remove();
                        count--;
                    }
                }

            }

        } else {

            for (Iterator<E> it = backingList.iterator(); it.hasNext(); ) {
                final E el = it.next();

                if (e.equals(el)) {

                    result++;

                    if (count > 0) {
                        it.remove();
                        count--;
                    }
                }

            }

        }

        return result;
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.backingList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.backingList.contains(o);
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return this.backingList.iterator();
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        //noinspection SuspiciousToArrayCall
        return this.backingList.toArray(a);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return this.backingList.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public void clear() {
        this.backingList.clear();
    }
}
