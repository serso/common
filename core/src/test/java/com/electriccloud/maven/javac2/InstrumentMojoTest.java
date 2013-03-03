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

package com.electriccloud.maven.javac2;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.solovyev.common.interval.Interval;
import org.solovyev.common.interval.Intervals;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 3/3/13
 * Time: 4:59 PM
 */
public class InstrumentMojoTest {

    @Test
    public void testInstrumentation() throws Exception {
        try {
            Interval<String> interval = Intervals.newPoint(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // ok
        }

        TestObject testObject = new TestObject();

        try {
            testObject.setNotNullField(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // ok
        }

        try {
            testObject.setNonnullField(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // ok
        }

        testObject.setNotNullField("test");
        testObject.setNonnullField("test");

    }

    private static final class TestObject {

        @NotNull
        private String notNullField;

        @Nonnull
        private String nonnullField;

        @NotNull
        public String getNotNullField() {
            return notNullField;
        }

        public void setNotNullField(@NotNull String notNullField) {
            this.notNullField = notNullField;
        }

        @Nonnull
        public String getNonnullField() {
            return nonnullField;
        }

        public void setNonnullField(@Nonnull String nonnullField) {
            this.nonnullField = nonnullField;
        }
    }
}
