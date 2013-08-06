package org.solovyev.common.collections;

import javax.annotation.Nonnull;

import static org.solovyev.common.collections.Sortings.swap;


final class IntMsdRadixSort implements ArrayNonComparisonSort<Integer> {

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

		final int maxBitIndex = getMaxBitIndex(numbers);
		sortByBit(numbers, 0, numbers.length, maxBitIndex);
	}

	private void sortByBit(@Nonnull Integer[] numbers, int start, int end, int bitIndex) {
		if (bitIndex >= BITS.length) {
			return;
		}

		if (end - start <= 1) {
			return;
		}

		final int bit = BITS[bitIndex];

		int firstOne = end;
		for (int i = start; i < firstOne; i++) {
			final Integer currentNumber = numbers[i];
			final int currentBit = currentNumber & bit;

			if (currentBit != 0) {
				boolean allOnes = true;

				for (int j = firstOne - 1; j > i; j--) {
					final Integer nextNumber = numbers[j];
					final int nextBit = nextNumber & bit;

					if (currentBit > nextBit) {
						swap(numbers, i, j);
						firstOne = j;
						allOnes = false;
						break;
					}
				}

				if (allOnes) {
					firstOne = i;
				}
			}
		}

		sortByBit(numbers, start, firstOne, bitIndex + 1);
		sortByBit(numbers, firstOne, end, bitIndex + 1);
	}

	int getMaxBitIndex(@Nonnull Integer[] numbers) {
		int result = 32;
		final Integer maxAbsNumber = getMaxAbsNumber(numbers);
		for (int i = 0; i < BITS.length; i++) {
			final int bit = BITS[i];
			if ((bit & maxAbsNumber) != 0) {
				result = i;
				break;
			}
		}

		return result;
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
