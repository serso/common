package org.solovyev.common.collections.multiset;

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 2:25 PM
 */
public class HashMapOneInstanceMultiSet<E> extends AbstractMapOneInstanceMultiSet<E> {

    public HashMapOneInstanceMultiSet() {
        super(new HashMap<E, Value<E>>());
    }

    public HashMapOneInstanceMultiSet(int capacity) {
        super(new HashMap<E, Value<E>>(capacity));
    }

    public HashMapOneInstanceMultiSet(Map<? extends E, ? extends Value<E>> m) {
        super(new HashMap<E, Value<E>>(m));
    }
}
