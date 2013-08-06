/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.collections;

import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
 * User: serso
 * Date: 8/7/13
 * Time: 12:46 AM
 */
public abstract class ArrayNonComparisonSortTest {

	private final int minValue;
	private final int maxValue;

	protected ArrayNonComparisonSortTest() {
		minValue = Integer.MIN_VALUE;
		maxValue = Integer.MAX_VALUE;
	}

	protected ArrayNonComparisonSortTest(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

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
			final Integer[] actual = CollectionsTest.generateIntListWithCollisions(size, maxValue).toArray(new Integer[size]);
			final Integer[] actualCopy = actual.clone();
			try {
				testIntSort(actual);
			} catch (AssertionError e) {
				System.out.println(Arrays.toString(actualCopy));
				throw e;
			}
		}
	}

	protected void testIntSort(@Nonnull Integer[] actual) {
		final Integer[] expected = actual.clone();

		Arrays.sort(expected);
		newSort().sort(actual);

		assertArrayEquals(expected, actual);
	}

	@Nonnull
	protected abstract ArrayNonComparisonSort<Integer> newSort();
}
