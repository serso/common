package org.solovyev.common.history;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.interval.IntervalLimit;
import org.solovyev.common.interval.IntervalLimitImpl;

/**
 * User: serso
 * Date: 8/9/12
 * Time: 12:39 PM
 */
public class IntervalLimitImplTest {

    @Test
    public void testCompareTo() throws Exception {
        IntervalLimit<Integer> lowest = IntervalLimitImpl.newLowest();
        IntervalLimit<Integer> highest = IntervalLimitImpl.newHighest();
        IntervalLimit<Integer> il1 = IntervalLimitImpl.newInstance(10, true);
        IntervalLimit<Integer> il2 = IntervalLimitImpl.newInstance(10, false);
        IntervalLimit<Integer> il3 = IntervalLimitImpl.newInstance(11, true);
        IntervalLimit<Integer> il4 = IntervalLimitImpl.newInstance(11, false);

        Assert.assertTrue(lowest.compareTo(lowest) == 0);
        Assert.assertTrue(lowest.compareTo(highest) < 0);
        Assert.assertTrue(lowest.compareTo(il1) < 0);
        Assert.assertTrue(lowest.compareTo(il2) < 0);
        Assert.assertTrue(lowest.compareTo(il3) < 0);
        Assert.assertTrue(lowest.compareTo(il4) < 0);

    }
}
