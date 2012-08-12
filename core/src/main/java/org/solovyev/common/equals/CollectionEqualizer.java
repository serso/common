package org.solovyev.common.equals;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.equals.Equalizer;
import org.solovyev.common.equals.EqualsUtils;

import java.util.Collection;

/**
 * User: serso
 * Date: 1/28/11
 * Time: 12:54 PM
 */
public class CollectionEqualizer<T> implements Equalizer<Collection<T>> {

	@Nullable
	protected final Equalizer<T> nestedEqualizer;

	public CollectionEqualizer(@Nullable Equalizer<T> nestedEqualizer) {
		this.nestedEqualizer = nestedEqualizer;
	}

	@Override
	public boolean equals(@Nullable Collection<T> first, @Nullable Collection<T> second) {
		final EqualsUtils.Result equalsResult = EqualsUtils.getEqualsResult(first, second);

		boolean result = false;
		if (equalsResult.areBothNulls()) {
			result = true;
		} else if (equalsResult.areBothNotNulls()) {

			//noinspection ConstantConditions
			if (first.size() == second.size()) {
				result = true;

				for (T el1 : first) {
					boolean found = false;

					for (T el2 : second) {
						if (EqualsUtils.areEqual(el1, el2, nestedEqualizer)) {
							found = true;
							break;
						}
					}

					if (!found) {
						result = false;
						break;
					}
				}
			}
		}

		return result;
	}

}
