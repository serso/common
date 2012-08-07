package org.solovyev.common.collections.multiset;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:51 PM
 */
public class HashMapManyInstancesMultiSetTest extends AbstractMultiSetTest {

    @NotNull
    @Override
    public <E> MultiSet<E> createMultiSet() {
        return new HashMapManyInstancesMultiSet<E>();
    }
}
