package org.solovyev.common.math.calculators;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:32 AM
 */
public abstract class AbstractCalculator<T extends Number> implements Calculator<T> {

	private final Class<T> clazz;

	public AbstractCalculator(Class<T> clazz) {
		this.clazz = clazz;
	}

	@NotNull
	@Override
	public Class<T> getObjectClass() {
		return this.clazz;
	}
}
