package org.solovyev.common.collections.multimap;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.collections.multiset.ArrayListMultiSet;
import org.solovyev.common.collections.multiset.MultiSet;

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
