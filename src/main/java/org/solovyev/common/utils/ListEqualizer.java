package org.solovyev.common.utils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:05 PM
 */
public class ListEqualizer<T> implements Equalizer<List<T>> {

	private final boolean checkOrder;

	@Nullable
	protected final Equalizer<T> nestedEqualizer;

	public ListEqualizer(boolean checkOrder, @Nullable Equalizer<T> nestedEqualizer) {
		this.checkOrder = checkOrder;
		this.nestedEqualizer = nestedEqualizer;
	}

	@Override
	public boolean equals(@Nullable List<T> first, @Nullable List<T> second) {
		final EqualsTool.Result equalsResult = EqualsTool.getEqualsResult(first, second);
		boolean result = false;
		if (equalsResult.areBothNulls()) {
			result = true;
		} else if (equalsResult.areBothNotNulls()) {

			if (first.size() == second.size()) {
				if (checkOrder) {
					result = true;
					for (int i = 0; i < first.size(); i++) {
						final T el1 = first.get(i);
						final T el2 = second.get(i);

						if (!EqualsTool.areEqual(el1, el2, nestedEqualizer)) {
							result = false;
							break;
						}

					}
				} else {
					result = new CollectionEqualizer<T>(nestedEqualizer).equals(first, second);
				}
			}
		}

		return result;
	}
}
