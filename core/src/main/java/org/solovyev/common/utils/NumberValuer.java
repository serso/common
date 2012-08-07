package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:23 PM
 */
public class NumberValuer<T extends Number> extends AbstractValuer<T>{

	@Override
	@NotNull
	public Double getValue(@NotNull T item) {
		return item.doubleValue();
	}
}
