package org.solovyev.common.collections;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.solovyev.common.collections.IntMsdRadixSort.BITS;

public class IntMsdRadixSortTest {

	@Test
	public void testSort() throws Exception {
		testIntSort(new Integer[]{1});
		testIntSort(new Integer[]{1, 2});
		testIntSort(new Integer[]{2, 1});
		testIntSort(new Integer[]{2, 1, 2});
		testIntSort(new Integer[]{3, 2, 1});
		testIntSort(new Integer[]{1, 2, 3});
		testIntSort(new Integer[]{0, 2, 1});
		testIntSort(new Integer[]{0, 3, 1, 2});
		testIntSort(new Integer[]{3, 2, 0, 1});
		testIntSort(new Integer[]{2, 4, 1, 0, 3});

		final Random random = new Random(new Date().getTime());
		for (int i = 0; i < 100; i++) {
			final int size = random.nextInt(10000) + 1;
			final Integer[] actual = CollectionsTest.generateIntList(size).toArray(new Integer[size]);
			final Integer[] actualCopy = actual.clone();
			try {
				testIntSort(actual);
			} catch (AssertionError e) {
				System.out.println(Arrays.toString(actualCopy));
				throw e;
			}
		}
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
		final IntMsdRadixSort sort = newSort();
		assertEquals(Integer.valueOf(1), sort.getMaxAbsNumber(new Integer[]{1}));
		assertEquals(Integer.valueOf(2), sort.getMaxAbsNumber(new Integer[]{-2}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MAX_VALUE, Integer.MIN_VALUE}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE + 1}));
		assertEquals(Integer.valueOf(Integer.MAX_VALUE - 1), sort.getMaxAbsNumber(new Integer[]{Integer.MIN_VALUE + 2}));

	}

	@Nonnull
	private IntMsdRadixSort newSort() {
		return new IntMsdRadixSort();
	}
}
