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

package org.solovyev.common.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 1/30/13
 * Time: 8:09 PM
 */
public class DiscreteNormalizerTest {

	@Test
	public void testNormalize() throws Exception {
		DiscreteNormalizer dn = new DiscreteNormalizer(0, 10, 1d);

		Assert.assertTrue(Maths.equals(0, dn.normalize(0.5), 2));
		Assert.assertTrue(Maths.equals(0, dn.normalize(0.99), 2));
		Assert.assertTrue(Maths.equals(0.1, dn.normalize(1), 2));
		Assert.assertTrue(Maths.equals(0.1, dn.normalize(1.01), 2));
		Assert.assertTrue(Maths.equals(1, dn.normalize(10), 2));
		Assert.assertTrue(Maths.equals(0.9, dn.normalize(9.99), 2));
	}

	@Test
	public void testDenormalize() throws Exception {
		DiscreteNormalizer dn = new DiscreteNormalizer(0, 10, 1d);

		Assert.assertTrue(Maths.equals(0, dn.normalize(dn.denormalize(0)), 2));
		Assert.assertTrue(Maths.equals(0.1, dn.normalize(dn.denormalize(0.1)), 2));
		Assert.assertTrue(Maths.equals(1, dn.normalize(dn.denormalize(1)), 2));
		Assert.assertTrue(Maths.equals(0.9, dn.normalize(dn.denormalize(0.9)), 2));
	}
}
