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

package org.solovyev.common.clone;

import org.solovyev.common.JCloneable;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: serso
 * Date: 13.04.2009
 * Time: 11:26:54
 */

/**
 * Class contains utility methods for cloning
 */
public class Cloneables {

	protected Cloneables() {
		throw new AssertionError("Not intended for instantiation!");
	}

	@Nonnull
	public static <T extends JCloneable<T>> List<T> cloneList(@Nonnull List<T> source) {
		return deepListCloning(source, new ArrayList<T>(source.size()));
	}

	@Nonnull
	public static <T extends JCloneable<T>> List<T> cloneList(@Nonnull List<T> source,
															  @Nonnull List<T> destination) {
		return deepListCloning(source, destination);
	}

	@Nonnull
	public static <T extends JCloneable<T>> List<T> deepListCloning(@Nonnull List<T> source) {
		return deepListCloning(source, new ArrayList<T>(source.size()));
	}

	@Nonnull
	public static <T extends JCloneable<T>> List<T> deepListCloning(@Nonnull List<T> source,
																	@Nonnull List<T> destination) {
		// clone all elements in the list
		for (T element : source) {
			destination.add(element.clone());
		}

		return destination;
	}

	@Nonnull
	public static <K, V extends JCloneable<V>> Map<K, V> cloneMap(@Nonnull Map<K, V> source) {
		return cloneMap(source, new HashMap<K, V>(source.size()));
	}

	@Nonnull
	public static <K, V extends JCloneable<V>> Map<K, V> cloneMap(@Nonnull Map<K, V> source, @Nonnull Map<K, V> destination) {
		for (Map.Entry<K, V> entry : source.entrySet()) {
			final V value = entry.getValue();
			if (value != null) {
				destination.put(entry.getKey(), value.clone());
			} else {
				destination.put(entry.getKey(), null);
			}
		}

		return destination;
	}

	@Nonnull
	public static <K extends JCloneable<K>, V extends JCloneable<V>> Map<K, V> cloneMapWithKeys(@Nonnull Map<K, V> source) {
		final Map<K, V> result = new HashMap<K, V>(source.size());

		for (Map.Entry<K, V> entry : source.entrySet()) {
			final V value = entry.getValue();
			final K key = entry.getKey();
			if (value != null) {
				result.put(key.clone(), value.clone());
			} else {
				result.put(key.clone(), null);
			}
		}

		return result;
	}

	/**
	 * Method deeply clones specified <var>array</var>, which means that for each element
	 * {@link org.solovyev.common.JCloneable#clone()} method will be called.
	 *
	 * @param array array to beb deeply cloned
	 * @return deep clone of <var>array</var>
	 */
	public static <T extends JCloneable<T>> T[] deepClone(@Nonnull T[] array) {
		return deepArrayCloning(array);
	}

	/**
	 * Method deeply clones specified <var>array</var>, which means that for each element
	 * {@link org.solovyev.common.JCloneable#clone()} method will be called.
	 *
	 * @param array array to beb deeply cloned
	 * @return deep clone of <var>array</var>
	 * @deprecated use {@link Cloneables#deepClone(T[])} instead
	 */
	@Deprecated
	@Nonnull
	public static <T extends JCloneable<T>> T[] deepArrayCloning(@Nonnull T[] array) {
		final T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);

		// clone all elements of array
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].clone();
		}

		return result;
	}
}
