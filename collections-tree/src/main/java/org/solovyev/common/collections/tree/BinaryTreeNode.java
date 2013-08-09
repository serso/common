package org.solovyev.common.collections.tree;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.solovyev.common.collections.tree.Trees.newBinaryTreeNode;

final class BinaryTreeNode<T> {

	@Nullable
	private final T value;

	@Nullable
	private BinaryTreeNode<T> parent;

	@Nullable
	private BinaryTreeNode<T> leftChild;

	@Nullable
	private BinaryTreeNode<T> rightChild;

	BinaryTreeNode(@Nullable T value, @Nullable BinaryTreeNode<T> parent) {
		this.value = value;
		this.parent = parent;
	}

	@Nullable
	public T getValue() {
		return value;
	}

	@Nullable
	public BinaryTreeNode<T> getLeftChild() {
		return leftChild;
	}

	@Nullable
	public BinaryTreeNode<T> getRightChild() {
		return rightChild;
	}

	@Nullable
	public BinaryTreeNode<T> getParent() {
		return parent;
	}

	@Nonnull
	BinaryTreeNode<T> addNode(@Nullable T newValue, @Nonnull Comparator<? super T> comparator) {
		final int compare = comparator.compare(newValue, value);
		if (compare < 0) {
			if (leftChild != null) {
				return leftChild.addNode(newValue, comparator);
			} else {
				leftChild = newBinaryTreeNode(newValue, this);
				return leftChild;
			}
		} else {
			if (rightChild != null) {
				return rightChild.addNode(newValue, comparator);
			} else {
				rightChild = newBinaryTreeNode(newValue, this);
				return rightChild;
			}
		}
	}

	public boolean removeChild(@Nonnull BinaryTreeNode<T> node) {
		if (leftChild != null && leftChild.equals(node)) {
			leftChild = null;
			return true;
		}

		if (rightChild != null && rightChild.equals(node)) {
			rightChild = null;
			return true;
		}

		return false;
	}

	private void removeParent() {
		this.parent = null;
	}

	boolean removeSelfFromTree(@Nonnull BinarySearchTree<T> tree) {
		boolean removed = false;

		if (leftChild == null && rightChild == null) {
			if (tree.getRoot().equals(this)) {
				throw new IllegalArgumentException("Tree must have at least one node (root)");
			} else {
				if (parent != null) {
					removed = parent.removeChild(this);
					removeParent();
				}
			}
		} else if (leftChild == null && rightChild != null) {
			replaceWithNode(tree, rightChild);
		} else if (leftChild != null && rightChild == null) {
			replaceWithNode(tree, leftChild);
		} else {
			final BinaryTreeNode<T> leftMostNode = findLeftMostNode(rightChild);
			final BinaryTreeNode<T> leftMostRightChild = leftMostNode.getRightChild();
			if (leftMostRightChild != null) {
				leftMostNode.replaceWithNode(tree, leftMostRightChild);
			}
			if (leftChild != null) {
				leftMostNode.leftChild = leftChild;
				leftChild.parent = leftMostNode;
			}
			replaceWithNode(tree, leftMostNode);
		}

		return removed;
	}

	private void replaceWithNode(@Nonnull BinarySearchTree<T> tree, @Nonnull BinaryTreeNode<T> node) {
		if(node.parent != null) {
			node.parent.removeChild(node);
		}

		if (tree.getRoot().equals(this)) {
			node.removeParent();
			tree.setRoot(node);
		} else {
			node.parent = this.parent;
			if(node.parent.leftChild != null && node.parent.leftChild.equals(this)) {
				node.parent.leftChild = node;
			}

			if(node.parent.rightChild != null && node.parent.rightChild.equals(this)) {
				node.parent.rightChild = node;
			}

			this.removeParent();
		}
	}

	@Nonnull
	private BinaryTreeNode<T> findLeftMostNode(@Nonnull BinaryTreeNode<T> node) {
		if (node.leftChild == null) {
			return node;
		} else {
			return findLeftMostNode(node.leftChild);
		}
	}
}
