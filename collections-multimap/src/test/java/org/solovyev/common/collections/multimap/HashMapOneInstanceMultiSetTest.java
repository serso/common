package org.solovyev.common.collections.multimap;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.collections.multiset.HashMapOneInstanceMultiSet;
import org.solovyev.common.collections.multiset.MultiSet;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:50 PM
 */
public class HashMapOneInstanceMultiSetTest extends AbstractMultiSetTest {

    @NotNull
    @Override
    public <E> MultiSet<E> createMultiSet() {
        return HashMapOneInstanceMultiSet.newInstance();
    }
}
