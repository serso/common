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
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.solovyev.common.interval.Interval;
import org.solovyev.common.interval.IntervalLimit;
import org.solovyev.common.interval.Intervals;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * User: serso
 * Date: 9/20/11
 * Time: 11:56 PM
 */
public final class NumberIntervalMapper<T extends Number & Comparable<T>> extends AbstractIntervalMapper<T> {

	/*
	**********************************************************************
	*
	*                           CONSTRUCTORS
	*
	**********************************************************************
	*/

	private NumberIntervalMapper(@Nonnull Class<T> clazz) {
		super(NumberMapper.of(clazz));
	}

	private NumberIntervalMapper(@Nonnull Class<T> clazz, @Nonnull String delimiter) {
		super(NumberMapper.of(clazz), delimiter);
	}

	@Nonnull
	public static <T extends Number & Comparable<T>> NumberIntervalMapper<T> of(@Nonnull Class<T> clazz) {
		return new NumberIntervalMapper<T>(clazz);
	}

	@Nonnull
	public static <T extends Number & Comparable<T>> NumberIntervalMapper<T> newInstance(@Nonnull Class<T> clazz, @Nonnull String delimiter) {
		return new NumberIntervalMapper<T>(clazz, delimiter);
	}

	/*
	**********************************************************************
	*
	*                           FIELDS
	*
	**********************************************************************
	*/

	@Nonnull
	@Override
	protected Interval<T> newInstance(@Nullable T left, @Nullable T right) {
		final IntervalLimit<T> leftLimit;
		if (left == null) {
			leftLimit = Intervals.newLowestLimit();
		} else {
			leftLimit = Intervals.newLimit(left, true);
		}

		final IntervalLimit<T> rightLimit;
		if (right == null) {
			rightLimit = Intervals.newHighestLimit();
		} else {
			rightLimit = Intervals.newLimit(right, true);
		}

		return Intervals.newInstance(leftLimit, rightLimit);
	}
}
