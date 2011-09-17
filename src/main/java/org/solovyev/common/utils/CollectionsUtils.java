/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.Identifiable;
import org.solovyev.common.html.Selectable;

import java.util.*;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:54:18 PM
 */
public class CollectionsUtils {

	@NotNull
	public static String toString(@NotNull Collection<Object> objects, @NotNull String separator) {
		StringBuilder sb = new StringBuilder();

		LoopData ld = new LoopData(objects);
		for (Object o : objects) {
			if (ld.isFirstAndNext()) {
				sb.append(separator);
			}
			sb.append(o);
		}

		return sb.toString();
	}

	public static boolean notEmpty(@Nullable Collection<?> c) {
		return !isEmpty(c);
	}

	public static boolean isEmpty(@Nullable Collection<?> c) {
		return c == null || c.isEmpty();
	}

	public static boolean isEmpty(@Nullable Object[] array) {
		return array == null || array.length == 0;
	}

	@NotNull
	public static <T> Collection<T> setNotNull(@Nullable Collection<T> c, @NotNull Collection<T> defaultValue) {
		Collection<T> result;

		if (c != null) {
			result = c;
		} else {
			defaultValue.clear();
			result = defaultValue;
		}

		return result;
	}

	public static <T> void addUnique(@NotNull List<T> list, @NotNull List<T> list2) {
		for (T t : list2) {
			addUnique(list, t);
		}
	}

	public static <T> void addUnique(@NotNull List<T> list, @NotNull T object) {
		boolean isFound = false;

		for (T t : list) {
			isFound = t.equals(object);
			if (isFound) {
				break;
			}
		}

		if (!isFound) {
			list.add(object);
		}
	}

	@NotNull
	public static <T> List<Selectable<T>> selectable(@NotNull List<T> list) {
		final List<Selectable<T>> result = new ArrayList<Selectable<T>>();

		for (T t : list) {
			result.add(new Selectable<T>(t));
		}

		return result;
	}

	/**
	 * Method finds the subtraction of two maps
	 *
	 * @param m1  first map
	 * @param m2  second map
	 * @param <K> key
	 * @param <V> value
	 * @return map which equals first map subtracted by the second map
	 */
	public static <K, V> Map<K, V> subtract(final Map<K, V> m1, final Map<K, V> m2) {
		final Map<K, V> result = new HashMap<K, V>();

		V value;
		for (Map.Entry<K, V> entry1 : m1.entrySet()) {
			value = m2.get(entry1.getKey());
			if (value == null) {
				result.put(entry1.getKey(), entry1.getValue());
			}
		}

		return result;
	}

	/**
	 * @param string	- string where stored list of objects separated by delimiter
	 * @param delimiter - delimiter with which string will be split
	 * @param mapper	- object that will create objects of specified type
	 * @param <T>       - type of object
	 * @return list of objects, not null
	 */
	@NotNull
	public static <T> List<T> split(@Nullable String string, @NotNull String delimiter, @NotNull Mapper<T> mapper) {
		final List<T> result = new ArrayList<T>();

		if (string != null && !string.isEmpty()) {
			@SuppressWarnings({"ConstantConditions"}) final String[] parts = string.split(delimiter);

			if (!CollectionsUtils.isEmpty(parts)) {
				for (String part : parts) {
					result.add(mapper.parseValue(part));
				}
			}
		}

		return result;
	}

	/**
	 * @param string	- string where stored list of objects separated by delimiter
	 * @param delimiter - delimiter with which string will be split
	 * @return list of objects, not null
	 */
	@NotNull
	public static List<String> split(@Nullable String string, @NotNull String delimiter) {
		return split(string, delimiter, new StringMapper());
	}

	@Nullable
	public static <T> String formatValue(@Nullable List<T> values, @NotNull String delimiter, @NotNull Mapper<T> inputDataType) {
		String result = null;
		if (!isEmpty(values)) {
			final StringBuilder sb = new StringBuilder();

			final LoopData ld = new LoopData(values);
			for (T value : values) {
				sb.append(inputDataType.formatValue(value));
				if (!ld.isLastAndNext()) {
					sb.append(delimiter);
				}
			}

			result = sb.toString();
		}

		return result;
	}

	/**
	 * Method returns first list element.
	 *
	 * @param list list of elements
	 * @param <T>  type of element in list
	 * @return null if list is null, or if list is empty, first element otherwise
	 */
	@Nullable
	public static <T> T getFirstListElement(List<? extends T> list) {
		T result = null;

		if (!isEmpty(list)) {
			result = list.get(0);
		}

		return result;
	}

	/**
	 * Method returns any first collection element.
	 *
	 * @param collection collection of elements
	 * @param <T>        type of element in collection
	 * @return null if collection is null, or if collections is empty, any first element otherwise
	 */
	@Nullable
	public static <T> T getFirstCollectionElement(Collection<? extends T> collection) {
		T result = null;

		if (!isEmpty(collection)) {
			for (T t : collection) {
				result = t;
				break;
			}
		}

		return result;
	}

	/**
	 * Method returns last list element.
	 *
	 * @param list list of elements
	 * @param <T>  type of element in list
	 * @return null if list is null, or if list is empty, last element otherwise
	 */
	public static <T> T getLastListElement(List<? extends T> list) {
		T result = null;

		if (!isEmpty(list)) {
			result = list.get(list.size() - 1);
		}

		return result;
	}


	public static <T> void removeEntriesByKeys(Map<T, ?> map, List<T> keys) {
		for (T object : keys) {
			map.remove(object);
		}
	}


	/**
	 * @param map map of objects
	 * @return 'true' if represented map is empty or null, 'false' otherwise
	 */
	public static boolean isEmpty(@Nullable Map map) {
		return map == null || map.size() == 0;
	}

	/**
	 * Method returns set of keys for specified value (by equals())
	 *
	 * @param map   map
	 * @param value value
	 * @param <T>   key type
	 * @param <E>   value type
	 * @return set of keys referred to specified value
	 */
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
		final Set<T> keys = new HashSet<T>();

		if (map != null) {
			for (Map.Entry<T, E> entry : map.entrySet()) {
				if (entry.getValue().equals(value)) {
					keys.add(entry.getKey());
				}
			}
		}

		return keys;
	}


	public static <T> boolean contains(@NotNull T value, @Nullable Collection<T> list, @NotNull Equalizer<T> equalizer) {
		return contains(value, list, FilterType.included, equalizer);
	}

	public static <T> boolean notContains(@NotNull T value, @Nullable Collection<T> list, @NotNull Equalizer<T> equalizer) {
		return contains(value, list, FilterType.excluded, equalizer);
	}

	public static <T> boolean contains(@NotNull T value, @Nullable Collection<T> list, @NotNull FilterType filterType, @Nullable Equalizer<T> equalizer) {
		return contains(list, filterType, new EqualsFinder<T>(value, equalizer));
	}

	public static <T> boolean contains(@Nullable Collection<T> list, @NotNull FilterType filterType, @NotNull Finder<T> finder) {
		boolean found = get(list, finder) != null;

		final boolean result;
		if (filterType == FilterType.included) {
			result = found;
		} else {
			result = !found;
		}

		return result;
	}

	@Nullable
	public static <T> T get(@NotNull T value, @Nullable Collection<T> list, @Nullable Equalizer<T> equalizer) {
		return get(list, new EqualsFinder<T>(value, equalizer));
	}

	public static <T> T get( @Nullable Collection<T> list, @NotNull Finder<T> finder) {
		T result = null;

		if (!isEmpty(list)) {
			for (T t : list) {
				if (finder.isFound(t)) {
					result = t;
					break;
				}
			}
		}

		return result;
	}

	public static <T> boolean contains(@NotNull T value, @NotNull FilterType filterType, @Nullable Collection<T> list) {
		return contains(value, list, filterType, null);
	}

	public static <T> boolean contains(@NotNull T value, @Nullable Collection<T> list) {
		return contains(value, FilterType.included, list);
	}

	public static <T> boolean contains(@NotNull T value, @Nullable T... array) {
		return contains(value, Arrays.asList(array));
	}

	public static <T extends Comparable> List<T> toIdsList(@NotNull Collection<? extends Identifiable<T>> list) {
		final List<T> result = new ArrayList<T>(list.size());

		for (Identifiable<T> t : list) {
			result.add(t.getId());
		}

		return result;
	}

	public static List<String> toStringList(@NotNull List<? extends Enum> idsList) {
		final List<String> result = new ArrayList<String>();

		for (Enum enumElement : idsList) {
			result.add(enumElement.name());
		}

		return result;
	}

	public static <T> List<T> asList(T... ts) {
		final List<T> result = new ArrayList<T>();

		if (!CollectionsUtils.isEmpty(ts)) {
			for (T t : ts) {
				result.add(t);
			}
		}

		return result;
	}

	public static <T, V> SortedMap<T, V> toSortedMap(@NotNull Map<T, V> map, @Nullable Comparator<? super T> comparator) {
		final SortedMap<T, V> result;

		if (comparator != null) {
			result = new TreeMap<T, V>(comparator);
		} else {
			result = new TreeMap<T, V>();
		}

		result.putAll(map);

		return result;
	}

	public static <T> Iterable<T> reversed(@NotNull List<T> list) {
		return new Reversed<T>(list);
	}

	public static <T> Iterable<T> reversed(@NotNull T[] array) {
		return new Reversed<T>(Arrays.asList(array));
	}

	public static class Reversed<T> implements Iterable<T> {

		@NotNull
		private final List<T> original;

		public Reversed(@NotNull List<T> original) {
			this.original = original;
		}

		public Iterator<T> iterator() {
			final ListIterator<T> i = original.listIterator(original.size());

			return new Iterator<T>() {
				public boolean hasNext() {
					return i.hasPrevious();
				}

				public T next() {
					return i.previous();
				}

				public void remove() {
					i.remove();
				}
			};
		}
	}
}

