package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * User: serso
 * Date: 7/5/12
 * Time: 2:22 PM
 */
class AbstractListMultiSet<E> extends AbstractMultiSet<E> implements ManyInstancesMultiSet<E> {

    @NotNull
    private final List<E> backingList;

    protected AbstractListMultiSet(@NotNull List<E> backingList) {
        this.backingList = backingList;
    }

    @NotNull
    @Override
    public Collection<E> getAll(E e) {
        final List<E> result = new ArrayList<E>();

        if (e == null) {
            for (E el : backingList) {
                if (el == null) {
                    result.add(el);
                }
            }
        } else {
            for (E el : backingList) {
                if (e.equals(el)) {
                    result.add(el);
                }
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

    @NotNull
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


        if ( e == null ) {

            for ( Iterator<E> it = backingList.iterator(); it.hasNext();  ) {
                final E el = it.next();

                if ( el == null ) {

                    result++;

                    if ( count > 0 ) {
                        it.remove();
                        count--;
                    }
                }

            }

        } else {

            for ( Iterator<E> it = backingList.iterator(); it.hasNext();  ) {
                final E el = it.next();

                if ( e.equals(el) ) {

                    result++;

                    if ( count > 0 ) {
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

    @Override
    public Iterator<E> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        //noinspection SuspiciousToArrayCall
        return this.backingList.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public void clear() {
        this.backingList.clear();
    }
}
