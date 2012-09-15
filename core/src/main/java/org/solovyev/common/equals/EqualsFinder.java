package org.solovyev.common.equals;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JPredicate;

/**
 * User: serso
 * Date: 9/17/11
 * Time: 10:16 PM
 */
public class EqualsFinder<T> implements JPredicate<T> {

	@NotNull
	private final T modelObject;

	@Nullable
	private final Equalizer<T> equalizer;

	public EqualsFinder(@NotNull T modelObject, @Nullable Equalizer<T> equalizer) {
		this.modelObject = modelObject;
		this.equalizer = equalizer;
	}

	@Override
	public boolean apply(@Nullable T object) {
		return EqualsTool.areEqual(modelObject, object, equalizer);
	}
}
