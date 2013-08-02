package org.solovyev.common.collections;

import javax.annotation.Nonnull;
import java.util.Comparator;

import static org.solovyev.common.collections.Sortings.swap;

/**
* User: serso
* Date: 8/1/13
* Time: 10:37 PM
*/
final class QuickSort<T> implements ArraySort<T> {

	@Override
	public void sort(@Nonnull T[] a, @Nonnull Comparator<? super T> c) {
		sort(a, 0, a.length - 1, c);
	}

	private void sort(@Nonnull T[] a, int l, int r, @Nonnull Comparator<? super T> c) {
		if (l < r) {
			final int m = partition(a, l, r, c);
			sort(a, l, m - 1, c);
			sort(a, m + 1, r, c);
		}
	}

	private int partition(@Nonnull T[] a, int l, int r, @Nonnull Comparator<? super T> c) {
		final T pivot = a[r];
		int j = l - 1;
		for (int i = l; i < r; i++) {
			if (c.compare(a[i], pivot) <= 0) {
				j++;
				swap(a, i, j);
			}
		}
		swap(a, j + 1, r);
		return j + 1;
	}

}
