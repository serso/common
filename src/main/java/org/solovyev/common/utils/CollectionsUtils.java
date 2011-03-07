/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.html.Selectable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public static <T> List<T> setNotNull(@Nullable List<T> c, @NotNull List<T> defaultValue) {
		List<T> result;

		if (c != null) {
			result = c;
		} else {
			defaultValue.clear();
			result = defaultValue;
		}

		return result;
	}

	public static <T> void addUnique (@NotNull List<T> list, @NotNull List<T> list2) {
		for (T t : list2) {
			addUnique(list, t);
		}
	}

	public static <T> void addUnique (@NotNull List<T> list, @NotNull T object) {
		boolean isFound = false;

		for (T t : list) {
			isFound = t.equals(object);
			if ( isFound ) {
				break;
			}
		}

		if ( !isFound ) {
			list.add(object);
		}
	}

	@NotNull
	public static <T> List<Selectable<T>> selectable (@NotNull List<T> list) {
		final List<Selectable<T>> result = new ArrayList<Selectable<T>>();

		for (T t : list) {
			result.add(new Selectable<T>(t));
		}

		return result;
	}
}

