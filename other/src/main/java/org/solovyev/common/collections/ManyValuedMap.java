package org.solovyev.common.collections;

import java.io.Serializable;
import java.util.*;

/*
 * User: serso
 * Date: 03.07.2009
 * Time: 13:32:07
 */

/*
* Providers easily work with maps of lists
* */
public interface ManyValuedMap<K, V> extends Cloneable, Map<K, List<V>>, Serializable {

    public List<V> put(K key, V... values);

    public void clear(K key);

    public ManyValuedMap<K, V> clone();

    public void sort(Comparator<? super V> c);

    public List<V> getAllValues();

    public Collection<List<V>> values(Comparator<? super K> c);

    public void lock();

    public Map<K, List<V>> toMap();

}