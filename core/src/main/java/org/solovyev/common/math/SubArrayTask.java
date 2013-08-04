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

package org.solovyev.common.math;

import javax.annotation.Nonnull;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * User: serso
 * Date: 8/5/13
 * Time: 12:22 AM
 */

// see http://www.codechef.com/JUNE13/problems/DELISH
final class SubArrayTask {

	static int solve(final int[] a) {
		final int n = a.length;
		if (n == 0) {
			return 0;
		}

		final int[] leftSums = calculateLeftSums(a, n);
		final int[] rightSums = calculateRightSums(a, n);
		final MaxsMins maxsMins = calculateMaxsMins(leftSums, rightSums, n);
		return calculateMaxDiff(maxsMins, n);
	}

	private static int calculateMaxDiff(@Nonnull MaxsMins maxsMins, int n) {
		int result = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			final int max1 = abs(maxsMins.rightMaxs[i - 1] - maxsMins.leftMins[i]);
			final int max2 = abs(maxsMins.rightMins[i] - maxsMins.leftMaxs[i - 1]);
			result = max(result, max(max1, max2));
		}
		return result;
	}

	@Nonnull
	private static MaxsMins calculateMaxsMins(int[] leftSums, int[] rightSums, int n) {
		final MaxsMins result = new MaxsMins(n);
		result.leftMaxs[0] = leftSums[0];
		result.leftMins[0] = leftSums[0];
		result.rightMaxs[n - 1] = rightSums[n - 1];
		result.rightMins[n - 1] = rightSums[n - 1];
		for (int i = 1; i < n; i++) {
			result.leftMaxs[i] = max(result.leftMaxs[i - 1], leftSums[i]);
			result.leftMins[i] = min(result.leftMins[i - 1], leftSums[i]);
			result.rightMaxs[n - i - 1] = max(result.rightMaxs[n - i], rightSums[n - i]);
			result.rightMins[n - i - 1] = min(result.rightMins[n - i], rightSums[n - i]);
		}
		return result;
	}

	private static int[] calculateRightSums(int[] a, int n) {
		final int[] sums = new int[n];
		sums[n-1] = a[n-1];
		for (int i = n - 2; i >= 0; i--) {
			sums[i] = sums[i + 1] + a[i];
		}
		return sums;
	}
	private static int[] calculateLeftSums(int[] a, int n) {
		final int[] sums = new int[n];
		sums[0] = a[0];
		for (int i = 1; i < n; i++) {
			sums[i] = sums[i - 1] + a[i];
		}
		return sums;
	}

	private static final class MaxsMins {
		private final int[] leftMaxs;
		private final int[] rightMaxs;
		private final int[] leftMins;
		private final int[] rightMins;

		private MaxsMins(int n) {
			leftMaxs = new int[n];
			rightMaxs = new int[n];
			leftMins = new int[n];
			rightMins = new int[n];
		}
	}
}
