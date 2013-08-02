package org.solovyev.common.collections;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

public class MergeSortTest extends ArraySortTest {
	@Nonnull
	@Override
	protected ArraySort<Integer> newArraySort() {
		return new MergeSort<Integer>();
	}

	@Test
	public void testSort() throws Exception {
		final Integer[] actual = {1, 0};
		final Integer[] expected = {0, 1};
		newArraySort().sort(actual, Collections.<Integer>naturalComparator());
		Assert.assertArrayEquals(expected, actual);
	}
}
