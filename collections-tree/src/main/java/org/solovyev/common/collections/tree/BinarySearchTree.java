package org.solovyev.common.collections.tree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

final class BinarySearchTree<T> {

	@Nonnull
	private BinarySearchTreeNode<T> root;

	@Nonnull
	private final Comparator<? super T> comparator;

	BinarySearchTree(@Nonnull BinarySearchTreeNode<T> root, @Nonnull Comparator<? super T> comparator) {
		this.root = root;
		this.comparator = comparator;
	}

	@Nonnull
	public BinarySearchTreeNode<T> addNode(@Nullable T value) {
		return root.addNode(value, comparator);
	}

	@Nonnull
	public BinarySearchTreeNode<T> addNodeTo(@Nonnull BinarySearchTreeNode<T> node, @Nullable T value) {
		return node.addNode(value, comparator);
	}

	public boolean removeNode(@Nonnull BinarySearchTreeNode<T> node) {
		return node.removeSelfFromTree(this);
	}

	@Nullable
	public BinarySearchTreeNode<T> findNode(@Nullable T value) {
		return root.findNode(value, comparator);
	}

	@Nonnull
	BinarySearchTreeNode<T> getRoot() {
		return root;
	}

	void setRoot(@Nonnull BinarySearchTreeNode<T> root) {
		this.root = root;
	}

	@Nonnull
	public Comparator<? super T> getComparator() {
		return comparator;
	}
}
