package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/17/11
 * Time: 10:16 PM
 */
public class EqualsFinder<T> implements Finder<T> {

	@NotNull
	private final T modelObject;

	@Nullable
	private final Equalizer<T> equalizer;

	public EqualsFinder(@NotNull T modelObject, @Nullable Equalizer<T> equalizer) {
		this.modelObject = modelObject;
		this.equalizer = equalizer;
	}

	@Override
	public boolean isFound(@Nullable T object) {
		return EqualsTool.areEqual(modelObject, object, equalizer);
	}
}
