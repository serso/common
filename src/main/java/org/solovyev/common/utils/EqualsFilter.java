package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 9/9/11
 * Time: 2:38 PM
 */
public class EqualsFilter<T> implements FilterRule<T> {

	@NotNull
	private final T filterObject;

	public EqualsFilter(@NotNull T filterObject) {
		this.filterObject = filterObject;
	}

	@Override
	public boolean isFiltered(T object) {
		return EqualsTool.areEqual(filterObject, object);
	}
}
