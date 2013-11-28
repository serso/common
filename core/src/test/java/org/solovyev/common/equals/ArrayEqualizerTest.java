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

package org.solovyev.common.equals;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.Objects;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 3/3/13
 * Time: 10:22 AM
 */
public class ArrayEqualizerTest {

	@Test
	public void testNaturalEquals() throws Exception {
		final Equalizer<Integer[]> equalizer = ArrayEqualizer.newWithNaturalEquals();

		Integer[] first = new Integer[]{1, 2, 3};
		Integer[] second = new Integer[]{1, 2, 3};

		Assert.assertTrue(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{1, 2, 3, 5};
		second = new Integer[]{1, 2, 3, 4};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{1, 2, 3};
		second = new Integer[]{1, 2, 3, 4};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{1, 2, 3, 5};
		second = new Integer[]{1, 2, 3};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{1, 2, 3};
		second = new Integer[]{3, 2, 1};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{};
		second = new Integer[]{};
		Assert.assertTrue(Objects.areEqual(first, second, equalizer));

		first = new Integer[]{0};
		second = new Integer[]{new Integer(0)};
		Assert.assertTrue(Objects.areEqual(first, second, equalizer));
	}

	@Test
	public void testCustomEquals() throws Exception {
		final Equalizer<TestJObject[]> equalizer = ArrayEqualizer.newWithNestedEqualizer(new Equalizer<TestJObject>() {
			@Override
			public boolean areEqual(@Nonnull TestJObject first, @Nonnull TestJObject second) {
				return first.getField().equals(second.getField());
			}
		});

		TestJObject[] first = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3")};
		TestJObject[] second = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3")};

		Assert.assertTrue(Objects.areEqual(first, second, equalizer));

		first = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3"), TestJObject.newInstance("5")};
		second = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3"), TestJObject.newInstance("4")};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3")};
		second = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3"), TestJObject.newInstance("4")};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3"), TestJObject.newInstance("5")};
		second = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3")};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new TestJObject[]{TestJObject.newInstance("1"), TestJObject.newInstance("2"), TestJObject.newInstance("3")};
		second = new TestJObject[]{TestJObject.newInstance("3"), TestJObject.newInstance("2"), TestJObject.newInstance("1")};
		Assert.assertFalse(Objects.areEqual(first, second, equalizer));

		first = new TestJObject[]{};
		second = new TestJObject[]{};
		Assert.assertTrue(Objects.areEqual(first, second, equalizer));


	}

	private static final class TestJObject {

		@Nonnull
		private final String field;

		private TestJObject(@Nonnull String field) {
			this.field = field;
		}

		private static TestJObject newInstance(@Nonnull String field) {
			return new TestJObject(field);
		}

		@Nonnull
		public String getField() {
			return field;
		}
	}
}
