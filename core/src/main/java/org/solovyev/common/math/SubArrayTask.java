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

		final int[] sums = calculateSums(a, n);
		final MaxsMins maxsMins = calculateMaxsMins(sums, n);
		return calculateMaxDiff(maxsMins, sums, n);
	}

	private static int calculateMaxDiff(@Nonnull MaxsMins maxsMins, int[] sums, int n) {
		int result = Integer.MIN_VALUE;
		for (int i = 1; i < n - 1; i++) {
			final int max1 = Math.abs(2 * sums[i] - maxsMins.rightMaxs[i - 1] - maxsMins.leftMaxs[i + 1]);
			final int max2 = Math.abs(2 * sums[i] - maxsMins.rightMins[i + 1] - maxsMins.leftMins[i - 1]);
			result = Math.max(result, Math.max(max1, max2));
		}
		return result;
	}

	@Nonnull
	private static MaxsMins calculateMaxsMins(int[] sums, int n) {
		final MaxsMins maxsMins = new MaxsMins(n);
		maxsMins.leftMaxs[0] = sums[0];
		maxsMins.leftMins[0] = sums[0];
		maxsMins.rightMaxs[0] = sums[n - 1];
		maxsMins.rightMins[0] = sums[n - 1];
		for (int i = 1; i < n; i++) {
			maxsMins.leftMaxs[i] = Math.max(maxsMins.leftMaxs[i - 1], sums[i]);
			maxsMins.rightMaxs[n - i - 1] = Math.max(maxsMins.rightMaxs[n - i], sums[n - i]);
			maxsMins.leftMins[i] = Math.min(maxsMins.leftMins[i - 1], sums[i]);
			maxsMins.rightMins[n - i - 1] = Math.min(maxsMins.rightMins[n - i], sums[n - i]);
		}
		return maxsMins;
	}

	private static int[] calculateSums(int[] a, int n) {
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
