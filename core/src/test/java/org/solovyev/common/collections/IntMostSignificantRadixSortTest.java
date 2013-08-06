package org.solovyev.common.collections;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.solovyev.common.collections.IntMostSignificantRadixSort.BITS;

public class IntMostSignificantRadixSortTest {

	@Test
	public void testSort() throws Exception {
		testIntSort(new Integer[]{1});
		testIntSort(new Integer[]{1, 2});
		testIntSort(new Integer[]{2, 1});
		testIntSort(new Integer[]{2, 1, 2});
		testIntSort(new Integer[]{3, 2, 1});
		testIntSort(new Integer[]{1, 2, 3});
	}

	private void testIntSort(@Nonnull Integer[] actual) {
		final Integer[] expected = actual.clone();

		Arrays.sort(expected);
		newSort().sort(actual);

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testAssertBitsCorrect() throws Exception {
		Assert.assertEquals(1, BITS[0] >> 30);
		Assert.assertEquals(1, BITS[1] >> 29);
		Assert.assertEquals(1, BITS[2] >> 28);
		Assert.assertEquals(1, BITS[3] >> 27);
		Assert.assertEquals(1, BITS[30]);

	}

	@Test
	public void testGetMaxAbsNumber() throws Exception {
		final IntMostSignificantRadixSort sort = newSort();
		assertEquals(Integer.valueOf(1), sort.getMaxAbsNumber(new Integer[]{1}));
		assertEquals(Integer.valueOf(2), sort.getMaxAbsNumber(new Integer[]{-2}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE + 1}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE - 1), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE + 2}));

	}

	@Nonnull
	private IntMostSignificantRadixSort newSort() {
		return new IntMostSignificantRadixSort();
	}
}
