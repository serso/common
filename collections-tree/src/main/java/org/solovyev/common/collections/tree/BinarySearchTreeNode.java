package org.solovyev.common.collections.tree;

import org.solovyev.common.text.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

final class BinarySearchTreeNode<T> {

	@Nullable
	private final T value;

	@Nullable
	private BinarySearchTreeNode<T> parent;

	@Nullable
	private BinarySearchTreeNode<T> leftChild;

	@Nullable
	private BinarySearchTreeNode<T> rightChild;

	BinarySearchTreeNode(@Nullable T value, @Nullable BinarySearchTreeNode<T> parent) {
		this.value = value;
		this.parent = parent;
	}

	@Nullable
	public T getValue() {
		return value;
	}

	@Nullable
	public BinarySearchTreeNode<T> getLeftChild() {
		return leftChild;
	}

	@Nullable
	public BinarySearchTreeNode<T> getRightChild() {
		return rightChild;
	}

	@Nullable
	public BinarySearchTreeNode<T> getParent() {
		return parent;
	}

	@Nonnull
	BinarySearchTreeNode<T> addNode(@Nullable T newValue, @Nonnull Comparator<? super T> comparator) {
		final int compare = comparator.compare(newValue, value);
		if (compare < 0) {
			if (leftChild != null) {
				return leftChild.addNode(newValue, comparator);
			} else {
				leftChild = Trees.newBinarySearchTreeNode(newValue, this);
				return leftChild;
			}
		} else {
			if (rightChild != null) {
				return rightChild.addNode(newValue, comparator);
			} else {
				rightChild = Trees.newBinarySearchTreeNode(newValue, this);
				return rightChild;
			}
		}
	}

	public boolean removeChildSubtree(@Nonnull BinarySearchTreeNode<T> child) {
		if (leftChild != null && leftChild.equals(child)) {
			child.removeParent();
			leftChild = null;
			return true;
		}

		if (rightChild != null && rightChild.equals(child)) {
			child.removeParent();
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
					removed = parent.removeChildSubtree(this);
				}
			}
		} else if (leftChild == null && rightChild != null) {
			replaceSubtree(tree, rightChild);
		} else if (leftChild != null && rightChild == null) {
			replaceSubtree(tree, leftChild);
		} else {
			final BinarySearchTreeNode<T> leftMostNode = findLeftMostNode(rightChild);
			leftMostNode.removeSelfFromTree(tree);

			leftMostNode.leftChild = leftChild;
			if(leftMostNode.leftChild != null) {
				leftMostNode.leftChild.parent = leftMostNode;
			}

			leftMostNode.rightChild = rightChild;
			if(leftMostNode.rightChild != null) {
				leftMostNode.rightChild.parent = leftMostNode;
			}

			if(tree.getRoot().equals(this)) {
				tree.setRoot(leftMostNode);
			} else {
				replaceSubtree(tree, leftMostNode);
			}

			leftChild = null;
			rightChild = null;
		}

		return removed;
	}

	private void replaceSubtree(@Nonnull BinarySearchTree<T> tree, @Nonnull BinarySearchTreeNode<T> newNode) {
		if (newNode.parent != null) {
			newNode.parent.removeChildSubtree(newNode);
		}

		if (tree.getRoot().equals(this)) {
			tree.setRoot(newNode);
		} else {
			newNode.parent = this.parent;

			boolean wasLeft = false;
			boolean wasRight = false;

			if (this.parent != null) {
				if (this.parent.leftChild != null && this.parent.leftChild.equals(this)) {
					wasLeft = true;
				}
				if (this.parent.rightChild != null && this.parent.rightChild.equals(this)) {
					wasRight = true;
				}
				this.parent.removeChildSubtree(this);
			}

			if (wasLeft) {
				newNode.parent.leftChild = newNode;
			}

			if (wasRight) {
				newNode.parent.rightChild = newNode;
			}
		}
	}

	@Nonnull
	private BinarySearchTreeNode<T> findLeftMostNode(@Nonnull BinarySearchTreeNode<T> node) {
		if (node.leftChild == null) {
			return node;
		} else {
			return findLeftMostNode(node.leftChild);
		}
	}

	@Nullable
	BinarySearchTreeNode<T> findNode(@Nullable T value, @Nonnull Comparator<? super T> comparator) {
		final int compare = comparator.compare(this.value, value);
		if(compare < 0) {
			return tryFindNode(value, comparator, rightChild);
		} else if (compare > 0) {
			return tryFindNode(value, comparator, leftChild);
		} else {
			return this;
		}
	}

	@Nullable
	private static <T> BinarySearchTreeNode<T> tryFindNode(@Nullable T value, @Nonnull Comparator<? super T> comparator, @Nullable BinarySearchTreeNode<T> node) {
		if(node != null) {
			return node.findNode(value, comparator);
		} else {
			return null;
		}
	}

	void toString(int depth, @Nonnull StringBuilder out, @Nullable String prefix) {
		out.append(Strings.repeat(" ", depth));
		if (prefix != null) {
			out.append(prefix).append(":");
		}
		out.append(this.value);
		out.append("\n");

		if (rightChild != null) {
			rightChild.toString(depth + 1, out, "r");
		}

		if (leftChild != null) {
			leftChild.toString(depth + 1, out, "l");
		}
	}

	public boolean isThisLeftChildOf(@Nonnull BinarySearchTreeNode<T> parent) {
		final BinarySearchTreeNode<T> parentLeftChild = parent.getLeftChild();
		if(parentLeftChild != null && parentLeftChild.equals(this)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isThisRightChildOf(@Nonnull BinarySearchTreeNode<T> parent) {
		final BinarySearchTreeNode<T> parentRightChild = parent.getRightChild();
		if(parentRightChild != null && parentRightChild.equals(this)) {
			return true;
		} else {
			return false;
		}
	}
}
