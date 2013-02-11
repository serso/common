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

import org.jetbrains.annotations.NotNull;

public final class Intervals {

	private Intervals() {
		throw new AssertionError();
	}

	@NotNull
	public static <T extends Comparable<T>> Interval<T> newPoint(@NotNull T point) {
		return newInstance(IntervalLimitImpl.newInstance(point, true), IntervalLimitImpl.newInstance(point, true));
	}

    @NotNull
    public static <T extends Comparable<T>> Interval<T> newInterval(@NotNull T left, boolean leftClosed, @NotNull T right, boolean rightClosed) {
        return newInstance(newLimit(left, leftClosed), newLimit(right, rightClosed));
    }

	@NotNull
	public static <T extends Comparable<T>> Interval<T> newClosedInterval(@NotNull T left, @NotNull T right) {
		return newInstance(newClosedLimit(left), newClosedLimit(right));
	}

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newClosedLimit(@NotNull T value) {
        return newLimit(value, true);
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newLimit(@NotNull T value, boolean closed) {
        return IntervalLimitImpl.newInstance(value, closed);
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newOpenedLimit(@NotNull T value) {
        return newLimit(value, false);
    }

	@NotNull
	public static <T extends Comparable<T>> IntervalImpl<T> newInstance(@NotNull IntervalLimit<T> left,
																		@NotNull IntervalLimit<T> right) {
		return IntervalImpl.newInstance(left, right);
	}

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newLowestLimit() {
        return IntervalLimitImpl.newLowest();
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newHighestLimit() {
        return IntervalLimitImpl.newHighest();
    }
}
