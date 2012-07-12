package org.solovyev.common.collections.multiset;

import java.util.HashMap;
import java.util.List;

/**
 * User: serso
 * Date: 7/12/12
 * Time: 2:45 PM
 */
public class HashMapManyInstancesMultiSet<E> extends AbstractMapManyInstancesMultiSet<E> {

    public HashMapManyInstancesMultiSet() {
        super(new HashMap<E, List<E>>());
    }

    public HashMapManyInstancesMultiSet(int capacity) {
        super(new HashMap<E, List<E>>(capacity));
    }

    public HashMapManyInstancesMultiSet(int capacity, float loadFactor) {
        super(new HashMap<E, List<E>>(capacity, loadFactor));
    }
}
