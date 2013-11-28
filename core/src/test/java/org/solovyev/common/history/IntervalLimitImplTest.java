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

package org.solovyev.common.history;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.interval.IntervalLimit;
import org.solovyev.common.interval.Intervals;

/**
 * User: serso
 * Date: 8/9/12
 * Time: 12:39 PM
 */
public class IntervalLimitImplTest {

	@Test
	public void testCompareTo() throws Exception {
		IntervalLimit<Integer> lowest = Intervals.newLowestLimit();
		IntervalLimit<Integer> highest = Intervals.newHighestLimit();
		IntervalLimit<Integer> il1 = Intervals.newLimit(10, true);
		IntervalLimit<Integer> il2 = Intervals.newLimit(10, false);
		IntervalLimit<Integer> il3 = Intervals.newLimit(11, true);
		IntervalLimit<Integer> il4 = Intervals.newLimit(11, false);

		Assert.assertTrue(lowest.compareTo(lowest) == 0);
		Assert.assertTrue(lowest.compareTo(highest) < 0);
		Assert.assertTrue(lowest.compareTo(il1) < 0);
		Assert.assertTrue(lowest.compareTo(il2) < 0);
		Assert.assertTrue(lowest.compareTo(il3) < 0);
		Assert.assertTrue(lowest.compareTo(il4) < 0);

	}
}
