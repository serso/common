package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:50 PM
 */
public class HashMapOneInstanceMultiSetTest extends AbstractMultiSetTest {

    @NotNull
    @Override
    public <E> MultiSet<E> createMultiSet() {
        return new HashMapOneInstanceMultiSet<E>();
    }
}
