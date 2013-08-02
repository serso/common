package org.solovyev.common.collections;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.System.*;

final class MergeSort<T> implements ArraySort<T> {
	@Override
	public void sort(@Nonnull T[] a, @Nonnull Comparator<? super T> c) {
		sort(a, 0, a.length - 1, c);
	}

	private void sort(@Nonnull T[] a, int l, int r, @Nonnull Comparator<? super T> c) {
		if(l < r) {
			final int m = l + (r - l) / 2;
			if (m > l) {
				sort(a, l, m - 1, c);
				sort(a, m, r, c);
				merge(a, l, r, m, c);
			}
		}
	}

	private void merge(@Nonnull T[] a, int l, int r, int m, @Nonnull Comparator<? super T> c) {
		final int lSize = (m - 1) - l;
		final int rSize = r - m;

		if(lSize <= 0 || rSize <= 0) {
			return;
		}

		// todo serso: these arrays better to create once in MergeSort.sort(T[], java.util.Comparator<? super T>)() and reuse
		final Object[] la = new Object[lSize];
		final Object[] ra = new Object[rSize];

		arraycopy(a, l, la, 0, lSize);
		arraycopy(a, m, ra, 0, rSize);

		int i = 0;
		int j = 0;
		int k = l;

		while (true) {
			final T lai = getI(la, i);
			final T raj = getI(ra, j);
			if(lai != null && raj != null) {
				if(c.compare(lai, raj) <= 0) {
					a[k] = lai;
					i++;
				} else {
					a[k] = raj;
					j++;
				}
			} else if (lai != null ) {
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
	private T getI(Object[] a, int i) {
		final Object ai;
		if(i < a.length) {
			ai = a[i];
		} else {
			ai = null;
		}

		return (T) ai;
	}
}
