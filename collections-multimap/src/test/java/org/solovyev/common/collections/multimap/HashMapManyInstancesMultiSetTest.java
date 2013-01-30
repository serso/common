package org.solovyev.common.collections.multimap;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.collections.multiset.HashMapManyInstancesMultiSet;
import org.solovyev.common.collections.multiset.MultiSet;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:51 PM
 */
public class HashMapManyInstancesMultiSetTest extends AbstractMultiSetTest {

    @NotNull
    @Override
    public <E> MultiSet<E> createMultiSet() {
        return HashMapManyInstancesMultiSet.newInstance();
    }
}
