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

package org.solovyev.common.collections.tree;

import org.junit.Test;

import javax.annotation.Nonnull;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.solovyev.common.collections.tree.Trees.newPrefixTree;

/**
 * User: serso
 * Date: 8/10/13
 * Time: 6:38 PM
 */
public class PrefixTreeTest {

	@Test
	public void testShouldAddStrings() throws Exception {
		final PrefixTree prefixTree = createTestTree();
		assertNotNull(prefixTree.getRoot().getChild('t'));
		assertNotNull(prefixTree.getRoot().getChild('t').getChild('o'));
		assertNotNull(prefixTree.getRoot().getChild('t').getChild('e'));
		assertNotNull(prefixTree.getRoot().getChild('t').getChild('e').getChild('a'));
		assertNotNull(prefixTree.getRoot().getChild('t').getChild('e').getChild('d'));
		assertNotNull(prefixTree.getRoot().getChild('t').getChild('e').getChild('n'));
		assertNotNull(prefixTree.getRoot().getChild('a'));
		assertNotNull(prefixTree.getRoot().getChild('i'));
		assertNotNull(prefixTree.getRoot().getChild('i').getChild('n'));
		assertNotNull(prefixTree.getRoot().getChild('i').getChild('n').getChild('n'));
	}

	@Nonnull
	private PrefixTree createTestTree() {
		final PrefixTree prefixTree = newPrefixTree();
		prefixTree.addString("to");
		prefixTree.addString("tea");
		prefixTree.addString("ted");
		prefixTree.addString("ten");
		prefixTree.addString("a");
		prefixTree.addString("i");
		prefixTree.addString("in");
		prefixTree.addString("inn");
		return prefixTree;
	}

	@Test
	public void testShouldFindStrings() throws Exception {
		final PrefixTree tree = createTestTree();
		assertFalse(tree.containsString("t"));
		assertFalse(tree.containsString("te"));
		assertTrue(tree.containsString("ted"));
		assertTrue(tree.containsString("ten"));
		assertTrue(tree.containsString("tea"));
		assertFalse(tree.containsString("teak"));
		assertTrue(tree.containsString("to"));

	}
}
