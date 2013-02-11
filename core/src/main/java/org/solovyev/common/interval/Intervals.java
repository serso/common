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
	public static <T extends Comparable<T>> Interval<T> newClosed(@NotNull T left, @NotNull T right) {
		return newInstance(IntervalLimitImpl.newInstance(left, true), IntervalLimitImpl.newInstance(right, true));
	}

	@NotNull
	public static <T extends Comparable<T>> IntervalImpl<T> newInstance(@NotNull IntervalLimit<T> left,
																		@NotNull IntervalLimit<T> right) {
		return IntervalImpl.newInstance(left, right);
	}
}
