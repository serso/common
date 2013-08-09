package org.solovyev.common.collections.tree;

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class BinarySearchTree<T> {

	@Nonnull
	private BinaryTreeNode<T> root;

	@Nonnull
	private final Comparator<? super T> comparator;

	BinarySearchTree(@Nonnull BinaryTreeNode<T> root, @Nonnull Comparator<? super T> comparator) {
		this.root = root;
		this.comparator = comparator;
	}

	@Nonnull
	public BinaryTreeNode<T> addNode(@Nullable T value) {
		return root.addNode(value, comparator);
	}

	@Nonnull
	public BinaryTreeNode<T> addNodeTo(@Nonnull BinaryTreeNode<T> node, @Nullable T value) {
		return node.addNode(value, comparator);
	}

	public boolean removeNode(@Nonnull BinaryTreeNode<T> node) {
		return node.removeSelfFromTree(this);
	}

	@Nonnull
	BinaryTreeNode<T> getRoot() {
		return root;
	}

	void setRoot(@Nonnull BinaryTreeNode<T> root) {
		this.root = root;
	}
}
