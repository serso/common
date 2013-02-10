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

package org.solovyev.common.interval;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 2/10/13
 * Time: 8:19 PM
 */
public class IntervalImplTest {

    @Test
    public void testContains() throws Exception {
        final Interval<Integer> interval1 = IntervalImpl.newInstance(IntervalLimitImpl.newInstance(10, true), IntervalLimitImpl.newInstance(20, true));
        final Interval<Integer> interval2 = IntervalImpl.newInstance(IntervalLimitImpl.newInstance(12, true), IntervalLimitImpl.newInstance(18, true));
        Assert.assertTrue(interval1.contains(interval2));
    }
}
