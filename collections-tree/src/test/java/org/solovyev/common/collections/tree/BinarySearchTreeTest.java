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
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.collections.tree;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class BinarySearchTreeTest {

	@Test
	public void testShouldCreateTree() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(2);

		assertNotNull(tree);
		assertEquals(Integer.valueOf(2), tree.getRoot().getValue());
	}

	@Test
	public void testShouldAddNodes() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(10);
		tree.addNode(30);
		tree.addNode(20);
		tree.addNode(40);
		tree.addNode(5);
		tree.addNode(3);
		tree.addNode(7);
		assertEquals(Integer.valueOf(10), tree.getRoot().getValue());
		assertEquals(Integer.valueOf(30), tree.getRoot().getRightChild().getValue());
		assertEquals(Integer.valueOf(5), tree.getRoot().getLeftChild().getValue());
		assertEquals(Integer.valueOf(3), tree.getRoot().getLeftChild().getLeftChild().getValue());
		assertEquals(Integer.valueOf(7), tree.getRoot().getLeftChild().getRightChild().getValue());
		assertEquals(Integer.valueOf(20), tree.getRoot().getRightChild().getLeftChild().getValue());
		assertEquals(Integer.valueOf(40), tree.getRoot().getRightChild().getRightChild().getValue());
	}

	@Test
	public void testShouldRemoveRootWithOnlyLeftChildren() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(4);
		final BinaryTreeNode<Integer> node = tree.addNode(2);
		tree.addNodeTo(node, 1);
		tree.addNodeTo(node, 3);

		final BinaryTreeNode<Integer> oldRoot = tree.getRoot();
		tree.removeNode(oldRoot);
		final BinaryTreeNode<Integer> newRoot = tree.getRoot();
		assertEquals(Integer.valueOf(2), newRoot.getValue());
		assertEquals(Integer.valueOf(1), newRoot.getLeftChild().getValue());
		assertEquals(Integer.valueOf(3), newRoot.getRightChild().getValue());
		assertNull(newRoot.getParent());
		assertNull(oldRoot.getLeftChild());
		assertNull(oldRoot.getRightChild());
	}

	@Test
	public void testShouldRemoveRootWithOnlyRightChildren() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(1);
		final BinaryTreeNode<Integer> node = tree.addNode(3);
		tree.addNodeTo(node, 2);
		tree.addNodeTo(node, 4);

		final BinaryTreeNode<Integer> oldRoot = tree.getRoot();
		tree.removeNode(oldRoot);
		final BinaryTreeNode<Integer> newRoot = tree.getRoot();
		assertEquals(Integer.valueOf(3), newRoot.getValue());
		assertEquals(Integer.valueOf(2), newRoot.getLeftChild().getValue());
		assertEquals(Integer.valueOf(4), newRoot.getRightChild().getValue());
		assertNull(newRoot.getParent());
		assertNull(oldRoot.getLeftChild());
		assertNull(oldRoot.getRightChild());
	}

	@Test
	public void testShouldRemoveNodeWithOnlyLeftChildren() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(5);
		final BinaryTreeNode<Integer> oldNode = tree.addNode(4);
		final BinaryTreeNode<Integer> node = tree.addNode(2);
		tree.addNodeTo(node, 1);
		tree.addNodeTo(node, 3);

		tree.removeNode(oldNode);
		final BinaryTreeNode<Integer> root = tree.getRoot();
		assertEquals(Integer.valueOf(5), root.getValue());
		assertNull(root.getRightChild());
		assertEquals(Integer.valueOf(2), root.getLeftChild().getValue());
		assertEquals(Integer.valueOf(1), root.getLeftChild().getLeftChild().getValue());
		assertEquals(Integer.valueOf(3), root.getLeftChild().getRightChild().getValue());
		assertEquals(root, node.getParent());
		assertEquals(node, root.getLeftChild());
		assertNull(root.getRightChild());

		assertRemovedNodeEmpty(oldNode);
	}

	@Test
	public void testShouldRemoveNodeWithOnlyRightChildren() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(1);
		final BinaryTreeNode<Integer> oldNode = tree.addNode(2);
		final BinaryTreeNode<Integer> node = tree.addNode(4);
		tree.addNodeTo(node, 3);
		tree.addNodeTo(node, 5);

		tree.removeNode(oldNode);
		final BinaryTreeNode<Integer> root = tree.getRoot();
		assertEquals(Integer.valueOf(1), root.getValue());
		assertNull(root.getLeftChild());
		assertEquals(Integer.valueOf(4), root.getRightChild().getValue());
		assertEquals(Integer.valueOf(3), root.getRightChild().getLeftChild().getValue());
		assertEquals(Integer.valueOf(5), root.getRightChild().getRightChild().getValue());
		assertEquals(root, node.getParent());
		assertEquals(node, root.getRightChild());
		assertNull(root.getLeftChild());

		assertRemovedNodeEmpty(oldNode);
	}

	private void assertRemovedNodeEmpty(@Nonnull BinaryTreeNode<Integer> node) {
		assertNull(node.getParent());
		assertNull(node.getLeftChild());
		assertNull(node.getRightChild());
	}

	@Test
	public void testShouldRemoveLeaf() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(1);
		final BinaryTreeNode<Integer> node = tree.addNode(3);
		final BinaryTreeNode<Integer> toBeRemoved1 = tree.addNodeTo(node, 2);
		final BinaryTreeNode<Integer> toBeRemoved2 = tree.addNodeTo(node, 4);

		tree.removeNode(toBeRemoved1);
		assertRemovedNodeEmpty(toBeRemoved1);
		Assert.assertNull(node.getLeftChild());
		Assert.assertNotNull(node.getRightChild());

		tree.removeNode(toBeRemoved2);
		assertRemovedNodeEmpty(toBeRemoved2);
		Assert.assertNull(node.getLeftChild());
		Assert.assertNull(node.getRightChild());
	}

	@Test
	public void testShouldNotRemoveLeafRoot() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(1);
		try {
			tree.removeNode(tree.getRoot());
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}

		assertNotNull(tree.getRoot());
	}

	@Test
	public void testShouldRemoveNode() throws Exception {
		final BinarySearchTree<Integer> tree = Trees.newBinaryTree(1);
		final BinaryTreeNode<Integer> oldNode = tree.addNode(3);
		tree.addNodeTo(oldNode, 2);
		tree.addNodeTo(oldNode, 4);

		tree.removeNode(oldNode);
		assertEquals(Integer.valueOf(1), tree.getRoot().getValue());
		assertNull(tree.getRoot().getLeftChild());
		assertEquals(Integer.valueOf(4), tree.getRoot().getRightChild().getValue());
		assertEquals(Integer.valueOf(2), tree.getRoot().getRightChild().getLeftChild().getValue());
		assertNull(tree.getRoot().getRightChild().getRightChild());

		assertRemovedNodeEmpty(oldNode);
	}
}
