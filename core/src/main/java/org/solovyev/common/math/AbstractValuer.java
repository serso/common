package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Converter;

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
