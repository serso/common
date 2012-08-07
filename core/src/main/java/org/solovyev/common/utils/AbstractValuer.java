package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:33 PM
 */
public abstract class AbstractValuer<T> implements Valuer<T>, Converter<T, Double> {

	@NotNull
	@Override
	public Double convert(@NotNull T t) {
		return getValue(t);
	}
}
