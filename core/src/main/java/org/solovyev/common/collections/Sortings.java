package org.solovyev.common.collections;

import javax.annotation.Nonnull;

import static org.solovyev.common.collections.Collections.naturalComparator;

/**
 * User: serso
 * Date: 8/1/13
 * Time: 10:01 PM
 */
final class Sortings {

	private static final QuickSort quickSort = new QuickSort();

	private Sortings() {
		throw new AssertionError();
	}

	public static <T extends Comparable<? super T>> void quickSort(@Nonnull T[] a) {
		quickSort.sort(a, naturalComparator());
	}

	static <T> void swap(@Nonnull T[] a, int i, int j) {
		if (i != j) {
			final T tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

}
