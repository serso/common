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

package org.solovyev.common.collections.tree;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 5:39 PM
 */
public class DepthTreeIteratorTest {

	@Test
	public void testToString() throws Exception {
		Assert.assertEquals("1\n" +
				" 2\n" +
				"  3\n" +
				"   4\n" +
				"   5\n" +
				"  6\n" +
				" 7\n" +
				" 8\n" +
				"  9\n" +
				"  10\n" +
				" 11\n" +
				"  12\n" +
				"   13\n" +
				"    14\n", LinkedTreeTest.createMockTree().toString());
	}

	@Test
	public void testHasNext() throws Exception {
		LinkedTreeTest.createMockTree();
	}

	@Test
	public void testNext() throws Exception {

	}

	@Test
	public void testRemove() throws Exception {

	}
}
