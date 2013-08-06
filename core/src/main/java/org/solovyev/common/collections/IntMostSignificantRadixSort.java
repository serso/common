package org.solovyev.common.collections;

import javax.annotation.Nonnull;


final class IntMostSignificantRadixSort implements ArrayNonComparisonSort<Integer> {

	private final static int BYTES_COUNT = 4;
	private final static int BITS_COUNT = BYTES_COUNT * 8 - 1;
	final static int[] BITS = new int[BITS_COUNT];

	static {
		for (int bit = 0; bit < BITS.length; bit++) {
			BITS[bit] = 1 << (BITS_COUNT - bit - 1);
		}
	}

	@Override
	public void sort(@Nonnull Integer[] numbers) {
		if (numbers.length == 0 || numbers.length == 1) {
			return;
		}

		//final Integer maxAbsNumber = getMaxAbsNumber(numbers);

		sortByBit(numbers, 0, numbers.length - 1, 0);
	}

	private void sortByBit(@Nonnull Integer[] numbers, int start, int end, int bitIndex) {
		if(bitIndex >= BITS.length) {
			return;
		}

		if(start >= end) {
			return;
		}

		final int bit = BITS[bitIndex];
		int j = end;
		int i = start;
		for (; i < j; i++) {
			final Integer iNumber = numbers[i];
			final int iBit = iNumber & bit;

			while (i < j) {
				final Integer jNumber = numbers[j];
				final int jBit = jNumber & bit;
				if (iBit > jBit) {
					numbers[j] = iNumber;
					numbers[i] = jNumber;

					j--;
					break;
				} else {
					j--;
				}
			}
		}

		sortByBit(numbers, start, j, bitIndex + 1);
		sortByBit(numbers, j + 1, end, bitIndex + 1);
	}

	@Nonnull
	Integer getMaxAbsNumber(@Nonnull Integer[] numbers) {
		Integer maxAbsNumber = 0;
		for (Integer number : numbers) {
			if (number >= 0) {
				if (number > maxAbsNumber) {
					maxAbsNumber = number;
				}
			} else if (number == Integer.MIN_VALUE) {
				maxAbsNumber = Integer.MAX_VALUE;
			} else {
				if (-number > maxAbsNumber) {
					maxAbsNumber = -number;
				}
			}
		}
		return maxAbsNumber;
	}
}
