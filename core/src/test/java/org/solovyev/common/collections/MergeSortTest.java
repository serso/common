package org.solovyev.common.collections;

import javax.annotation.Nonnull;

public class MergeSortTest extends ArraySortTest {
	@Nonnull
	@Override
	protected ArraySort<Integer> newArraySort() {
		return new MergeSort<Integer>();
	}
}
