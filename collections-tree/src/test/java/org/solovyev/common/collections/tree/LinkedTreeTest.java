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

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.JPredicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * User: serso
 * Date: 4/1/12
 * Time: 11:08 PM
 */
public class LinkedTreeTest {
	/*

1
├──── 2
│     ├──── 3
│     │     ├──── 4
│     │     └──── 5
│     └───── 6
├──── 7
├──── 8
│     ├──── 9
│     └──── 10
└──── 11
	  └──── 12
			└──── 13
				  └──── 14
	 */
	@Nonnull
	static MutableTree<Integer> createMockTree() {
		final MutableTree<Integer> result = Trees.newLinkedTree(1);

		MutableTreeNode<Integer> root = result.getRoot();
		MutableTreeNode<Integer> child2 = root.addChild(2);
		MutableTreeNode<Integer> child3 = child2.addChild(3);
		child3.addChild(4);
		child3.addChild(5);
		child2.addChild(6);
		root.addChild(7);
		MutableTreeNode<Integer> child8 = root.addChild(8);
		child8.addChild(9);
		child8.addChild(10);
		root.addChild(11).addChild(12).addChild(13).addChild(14);


		return result;
	}

	@Test
	public void testRemoveNodeIf() throws Exception {
		// remove all even elements
		MutableTree<Integer> tree = createMockTree();
		tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
			@Override
			public boolean apply(@Nullable TreeNode<Integer> input) {
				return input.getValue() % 2 == 0;
			}
		});

		Assert.assertEquals(3, tree.getSize());
		for (TreeNode<Integer> node : tree) {
			Assert.assertTrue(node.getValue() % 2 != 0);
		}

		// remove all elements
		tree = createMockTree();
		tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
			@Override
			public boolean apply(@Nullable TreeNode<Integer> input) {
				return true;
			}
		});
		Assert.assertEquals(1, tree.getSize());


		// remove all odd elements
		tree = createMockTree();
		tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
			@Override
			public boolean apply(@Nullable TreeNode<Integer> input) {
				return input.getValue() % 2 != 0;
			}
		});

		Assert.assertEquals(5, tree.getSize());
		for (TreeNode<Integer> node : tree) {
			if (node != tree.getRoot()) {
				Assert.assertTrue(node.getValue() % 2 == 0);
			}
		}
	}

	@Test
	public void testDepthTreeIterator() throws Exception {
		MutableTree<Integer> tree = createMockTree();

		for (TreeIterator<Integer> it = Trees.newDepthTreeIterator(tree); it.hasNext(); ) {
			final TreeNode<Integer> node = it.next();
			Assert.assertEquals(node.getDepth(), it.getDepth());
			System.out.println(it.getDepth() + " ->" + node.getValue());
		}
	}
}
