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

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.interval.IntervalImpl;

/**
 * User: serso
 * Date: 1/30/13
 * Time: 8:09 PM
 */
public class NumberIntervalMapperTest {

    @Test
    public void testParse() throws Exception {
        final NumberIntervalMapper<Float> mapper = NumberIntervalMapper.of(Float.class);

        Assert.assertEquals(IntervalImpl.newClosed(1.2f, 12.2f), mapper.parseValue("1.2;12.2"));
        Assert.assertEquals(IntervalImpl.newPoint(0f), mapper.parseValue("0;0"));
    }
}
