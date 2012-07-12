package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 2:31 PM
 */
public class ArrayListMultiSetTest extends AbstractMultiSetTest {

    @NotNull
    @Override
    public <E> MultiSet<E> createMultiSet() {
        return new ArrayListMultiSet<E>();
    }
}
