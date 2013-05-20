/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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