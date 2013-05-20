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

package org.solovyev.common.clone;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.Objects;
import org.solovyev.common.equals.ArrayEqualizer;
import org.solovyev.common.equals.ListEqualizer;
import org.solovyev.common.equals.SameEqualizer;
import org.solovyev.common.interval.Interval;
import org.solovyev.common.interval.Intervals;

import java.util.ArrayList;
import java.util.List;

public class CloneablesTest {

	@Test
	public void testCloneList() throws Exception {
		final List<Interval<Integer>> intervals = new ArrayList<Interval<Integer>>();
		intervals.add(Intervals.newClosedInterval(10, 20));
		intervals.add(Intervals.newClosedInterval(-10, 20));
		intervals.add(Intervals.newClosedInterval(10, 520));

		final List<Interval<Integer>> intervalsCopy = Cloneables.cloneList(intervals);

		Assert.assertTrue(intervalsCopy.size() == intervals.size());
		Assert.assertTrue(Objects.areEqual(intervals, intervalsCopy, ListEqualizer.<Interval<Integer>>newWithNaturalEquals(true)));
		Assert.assertFalse(Objects.areEqual(intervals, intervalsCopy, ListEqualizer.newWithNestedEqualizer(true, SameEqualizer.<Interval<Integer>>getInstance())));
	}

	@Test
	public void testCloneArray() throws Exception {
		Interval[] expected = new Interval[3];
		expected[0] = Intervals.newPoint(2);
		expected[1] = Intervals.newClosedInterval(0, 3);
		expected[2] = Intervals.newInterval(10, false, 15, true);

		Interval[] actual = Cloneables.deepClone(expected);
		Assert.assertTrue(Objects.areEqual(expected, actual, ArrayEqualizer.newWithNaturalEquals()));
		Assert.assertFalse(Objects.areEqual(expected, actual, ArrayEqualizer.newWithNestedEqualizer(SameEqualizer.getInstance())));
	}
}
