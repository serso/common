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

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 8/7/13
 * Time: 12:21 AM
 */
class IntCountingSort implements ArrayNonComparisonSort<Integer> {

	private final int maxNumber;

	IntCountingSort(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	@Override
	public void sort(@Nonnull Integer[] numbers) {
		if(numbers.length == 0 || numbers.length == 1) {
			return;
		}

		final int[] counts = new int[maxNumber + 1];

		for (Integer number : numbers) {
			if (number > maxNumber) {
				throw new IllegalArgumentException("Number must be less than or equal to " + maxNumber);
			} else if (number < 0) {
				throw new IllegalArgumentException("Number must be more than or equal to " + 0);
			}
			counts[number] = counts[number] + 1;
		}

		for (int i = 1; i < counts.length; i++) {
			counts[i] = counts[i-1] + counts[i];
		}

		final Integer[] out = new Integer[numbers.length];
		for (Integer number : numbers) {
			out[counts[number]-1] = number;
			counts[number]--;
		}
		System.arraycopy(out, 0, numbers, 0, numbers.length);
	}
}
