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

import static org.junit.Assert.assertEquals;
import static org.solovyev.common.collections.tree.PrefixTreeNode.newPrefixTreeNode;

/**
 * User: serso
 * Date: 8/10/13
 * Time: 6:47 PM
 */
public class PrefixTreeNodeTest {

	@Test
	public void testShouldContainCorrectValues() throws Exception {
		PrefixTreeNode node = newPrefixTreeNode("123", 0);
		checkNode(node, '1', "1", false);
		node.addString("123", 1);
		checkNode(node.getChild('2'), '2', "12", false);
		node.addString("123", 2);
		checkNode(node.getChild('2').getChild('3'), '3', "123", true);
		node.addString("12", 1);
		checkNode(node.getChild('2'), '2', "12", true);
	}

	private void checkNode(@Nonnull PrefixTreeNode node, char expectedCharacter, String expectedString, boolean expectedLastCharacter) {
		assertEquals(Character.valueOf(expectedCharacter), node.getCharacter());
		assertEquals(expectedString, node.getString());
		assertEquals(expectedLastCharacter, node.isLastCharacter());
	}
}
