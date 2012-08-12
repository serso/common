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

    public ArrayListMultiSet() {
        super(new ArrayList<E>());
    }

    public ArrayListMultiSet(int capacity) {
        super(new ArrayList<E>(capacity));
    }

    public ArrayListMultiSet(@NotNull Collection<? extends E> c) {
        super(new ArrayList<E>(c));
    }

}
