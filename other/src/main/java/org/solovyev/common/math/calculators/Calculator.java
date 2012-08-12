package org.solovyev.common.math.calculators;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.equals.Equalizer;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:30 AM
 */
public interface Calculator<T> extends Equalizer<T>{

	@NotNull
	Class<T> getObjectClass();

	@NotNull
	T multiply(@NotNull T l, @NotNull T r);

	@NotNull
	T divide(@NotNull T l, @NotNull T r);

	@NotNull
	T summarize(@NotNull T l, @NotNull T r);

	@NotNull
	T subtract (@NotNull T l, @NotNull  T r);

	@NotNull
	T getZero();

}
