package org.solovyev.common.collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

import static java.lang.System.arraycopy;

final class MergeSort<T> implements ArraySort<T> {

	@Override
	public void sort(@Nonnull T[] a, @Nonnull Comparator<? super T> c) {
		final T[] aux = a.clone();
		sort(a, 0, a.length - 1, c, aux);
	}

	private void sort(@Nonnull T[] a, int l, int r, @Nonnull Comparator<? super T> c, @Nonnull T[] aux) {
		if (r - l > 0) {
			final int m = l + (r - l + 1) / 2;
			sort(a, l, m - 1, c, aux);
			sort(a, m, r, c, aux);
			merge(a, l, r, m, c, aux);
		}
	}

	private void merge(@Nonnull T[] a, int l, int r, int m, @Nonnull Comparator<? super T> c, @Nonnull T[] aux) {
		final int lSize = m - l;
		final int rSize = r - m + 1;

		if (lSize <= 0 || rSize <= 0) {
			return;
		}

		if (c.compare(a[m - 1], a[m]) <= 0) {
			// already sorted
			return;
		}

		arraycopy(a, l, aux, 0, lSize);
		arraycopy(a, m, aux, lSize, rSize);

		int i = 0;
		int j = 0;
		int k = l;

		while (true) {
			final T lai = getI(aux, i, lSize);
			final T raj = getI(aux, lSize + j, lSize + rSize);
			if (lai != null && raj != null) {
				if (c.compare(lai, raj) <= 0) {
					a[k] = lai;
					i++;
				} else {
					a[k] = raj;
					j++;
				}
				k++;
			} else if (lai != null) {
				a[k] = lai;
				i++;
				k++;
			} else if (raj != null) {
				a[k] = raj;
				j++;
				k++;
			} else {
				break;
			}
		}
	}

	@Nullable
	private T getI(Object[] a, int i, int size) {
		final Object ai;
		if (i < size) {
			ai = a[i];
		} else {
			ai = null;
		}

		return (T) ai;
	}
}
