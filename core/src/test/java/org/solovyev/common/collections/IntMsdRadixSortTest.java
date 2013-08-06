package org.solovyev.common.collections;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.junit.Assert.assertEquals;
import static org.solovyev.common.collections.IntMsdRadixSort.BITS;

public class IntMsdRadixSortTest extends ArrayNonComparisonSortTest {

	public IntMsdRadixSortTest() {
		super(0, Integer.MAX_VALUE);
	}

	@Override
	public void testSort() throws Exception {
		super.testSort();
		testIntSort(new Integer[]{1335553392,821585291,370543740});
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

	@Override
	@Nonnull
	protected IntMsdRadixSort newSort() {
		return new IntMsdRadixSort();
	}
}
