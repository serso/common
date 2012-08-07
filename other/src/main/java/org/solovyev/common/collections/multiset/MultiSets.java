package org.solovyev.common.collections.multiset;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 1:37 PM
 */
public class MultiSets {

    private MultiSets() {
        throw new AssertionError();
    }

    static void checkAdd(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Number of elements to add must not be negative!");
        }
    }

    static void checkRemove(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Number of elements to remove must be not negative!");
        }
    }

    static void checkSetCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Number of elements must be not negative!");
        }
    }

    static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR =
            new Iterator<Object>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Object next() {
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new IllegalStateException();
                }
            };
}
