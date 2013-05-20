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
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.interval;

import javax.annotation.Nonnull;

public final class Intervals {

	private Intervals() {
		throw new AssertionError();
	}

	@Nonnull
	public static <T extends Comparable<T>> Interval<T> newPoint(@Nonnull T point) {
		return newInstance(IntervalLimitImpl.newInstance(point, true), IntervalLimitImpl.newInstance(point, true));
	}

	@Nonnull
	public static <T extends Comparable<T>> Interval<T> newInterval(@Nonnull T left, boolean leftClosed, @Nonnull T right, boolean rightClosed) {
		return newInstance(newLimit(left, leftClosed), newLimit(right, rightClosed));
	}

	@Nonnull
	public static <T extends Comparable<T>> Interval<T> newClosedInterval(@Nonnull T left, @Nonnull T right) {
		return newInstance(newClosedLimit(left), newClosedLimit(right));
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalLimit<T> newClosedLimit(@Nonnull T value) {
		return newLimit(value, true);
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalLimit<T> newLimit(@Nonnull T value, boolean closed) {
		return IntervalLimitImpl.newInstance(value, closed);
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalLimit<T> newOpenedLimit(@Nonnull T value) {
		return newLimit(value, false);
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalImpl<T> newInstance(@Nonnull IntervalLimit<T> left,
																		@Nonnull IntervalLimit<T> right) {
		return IntervalImpl.newInstance(left, right);
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalLimit<T> newLowestLimit() {
		return IntervalLimitImpl.newLowest();
	}

	@Nonnull
	public static <T extends Comparable<T>> IntervalLimit<T> newHighestLimit() {
		return IntervalLimitImpl.newHighest();
	}
}
