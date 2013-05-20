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

import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.UnmodifiableIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

class UnmodifiableTreeNode<T> implements MutableTreeNode<T> {

	/*
	**********************************************************************
	*
	*                           FIELDS
	*
	**********************************************************************
	*/

	@Nonnull
	private final MutableTreeNode<T> treeNode;

	/*
	**********************************************************************
	*
	*                           CONSTRUCTORS
	*
	**********************************************************************
	*/

	private UnmodifiableTreeNode(@Nonnull MutableTreeNode<T> treeNode) {
		this.treeNode = treeNode;
	}

	@Nonnull
	public static <T> UnmodifiableTreeNode<T> wrap(@Nonnull MutableTreeNode<T> treeNode) {
		return new UnmodifiableTreeNode<T>(treeNode);
	}

	@Nonnull
	public static <T> UnmodifiableTreeNode<T> wrap(@Nonnull TreeNode<T> treeNode) {
		if (treeNode instanceof MutableTreeNode) {
			return wrap((MutableTreeNode<T>) treeNode);
		} else {
			return new UnmodifiableTreeNode<T>(TreeNodeAdapter.adapt(treeNode));
		}
	}


	/*
	**********************************************************************
	*
	*                           FIELDS
	*
	**********************************************************************
	*/

	@Nullable
	public MutableTreeNode<T> findOwnChild(@Nonnull JPredicate<TreeNode<T>> finder) {
		return treeNode.findOwnChild(finder);
	}

	public void setValue(@Nullable T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Nonnull
	public Collection<? extends MutableTreeNode<T>> getOwnChildren() {
		return Trees.unmodifiableMutableTreeNodes(treeNode.getOwnChildren());
	}

	@Override
	@Nonnull
	public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
		return UnmodifiableIterator.wrap(treeNode.getOwnChildrenIterator());
	}

	@Override
	@Nonnull
	public Collection<? extends MutableTreeNode<T>> getAllChildren() {
		return Trees.unmodifiableMutableTreeNodes(treeNode.getAllChildren());
	}

	public void addChild(@Nonnull MutableTreeNode<T> node) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	public MutableTreeNode<T> addChild(@Nonnull T value) {
		throw new UnsupportedOperationException();
	}

	@Nonnull
	public MutableTreeNode<T> addChildIfNotExists(@Nonnull T value) {
		throw new UnsupportedOperationException();
	}

	public void removeOwnChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
		throw new UnsupportedOperationException();
	}

	public void removeChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
		throw new UnsupportedOperationException();
	}

	public void setParent(@Nullable TreeNode<T> parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Nullable
	public T getValue() {
		return treeNode.getValue();
	}

	@Override
	@Nonnull
	public Iterator<TreeNode<T>> iterator() {
		return UnmodifiableIterator.wrap(treeNode.iterator());
	}

	@Override
	@Nonnull
	public Iterator<? extends TreeNode<T>> getIterator() {
		return UnmodifiableIterator.wrap(treeNode.getIterator());
	}

	@Override
	public int getSize() {
		return treeNode.getSize();
	}

	@Override
	public boolean isLeaf() {
		return treeNode.isLeaf();
	}

	@Override
	public boolean isRoot() {
		return treeNode.isRoot();
	}

	@Override
	public int getDepth() {
		return treeNode.getDepth();
	}

	@Override
	@Nullable
	public TreeNode<T> getParent() {
		final TreeNode<T> result = treeNode.getParent();
		if (result != null) {
			return wrap(result);
		} else {
			return null;
		}
	}

	/*
	**********************************************************************
	*
	*                           STATIC
	*
	**********************************************************************
	*/

	private static class TreeNodeAdapter<T> implements MutableTreeNode<T> {

		@Nonnull
		private final TreeNode<T> treeNode;

		private TreeNodeAdapter(@Nonnull TreeNode<T> treeNode) {
			this.treeNode = treeNode;
		}

		@Nonnull
		private static <T> TreeNodeAdapter<T> adapt(@Nonnull TreeNode<T> treeNode) {
			return new TreeNodeAdapter<T>(treeNode);
		}

		@Nullable
		public MutableTreeNode<T> findOwnChild(@Nonnull JPredicate<TreeNode<T>> finder) {
			final TreeNode<T> result = treeNode.findOwnChild(finder);
			if (result != null) {
				return wrap(result);
			} else {
				return null;
			}
		}

		@Override
		public void setValue(@Nullable T value) {
			throw new AssertionError("Should never happen!");
		}

		@Override
		@Nonnull
		public Collection<? extends MutableTreeNode<T>> getOwnChildren() {
			return Trees.unmodifiableTreeNodes(treeNode.getOwnChildren());
		}

		@Override
		@Nonnull
		public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
			return UnmodifiableIterator.wrap(TreeNodeIteratorAdapter.adapt(treeNode.getOwnChildrenIterator()));
		}

		@Override
		@Nullable
		public T getValue() {
			return treeNode.getValue();
		}

		@Override
		@Nonnull
		public Iterator<TreeNode<T>> iterator() {
			return UnmodifiableIterator.wrap(treeNode.iterator());
		}

		@Override
		@Nonnull
		public Iterator<? extends TreeNode<T>> getIterator() {
			return UnmodifiableIterator.wrap(treeNode.getIterator());
		}

		@Override
		@Nonnull
		public Collection<? extends MutableTreeNode<T>> getAllChildren() {
			return Trees.unmodifiableTreeNodes(treeNode.getAllChildren());
		}

		@Override
		public void addChild(@Nonnull MutableTreeNode<T> node) {
			throw new AssertionError("Should never happen!");
		}

		@Nonnull
		@Override
		public MutableTreeNode<T> addChild(@Nonnull T value) {
			throw new AssertionError("Should never happen!");
		}

		@Nonnull
		@Override
		public MutableTreeNode<T> addChildIfNotExists(@Nonnull T value) {
			throw new AssertionError("Should never happen!");
		}

		@Override
		public void removeOwnChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
			throw new AssertionError("Should never happen!");
		}

		@Override
		public void removeChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
			throw new AssertionError("Should never happen!");
		}

		@Override
		public void setParent(@Nullable TreeNode<T> parent) {
			throw new AssertionError("Should never happen!");
		}

		@Override
		public int getSize() {
			return treeNode.getSize();
		}

		@Override
		public boolean isLeaf() {
			return treeNode.isLeaf();
		}

		@Override
		public boolean isRoot() {
			return treeNode.isRoot();
		}

		@Override
		public int getDepth() {
			return treeNode.getDepth();
		}

		@Override
		@Nullable
		public TreeNode<T> getParent() {
			return treeNode.getParent();
		}
	}

	private static class TreeNodeIteratorAdapter<T> implements Iterator<MutableTreeNode<T>> {

		@Nonnull
		private final Iterator<? extends TreeNode<T>> i;

		private TreeNodeIteratorAdapter(@Nonnull Iterator<? extends TreeNode<T>> i) {
			this.i = i;
		}

		@Nonnull
		private static <T> Iterator<MutableTreeNode<T>> adapt(@Nonnull Iterator<? extends TreeNode<T>> i) {
			return new TreeNodeIteratorAdapter<T>(i);
		}

		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public MutableTreeNode<T> next() {
			return wrap(i.next());
		}

		@Override
		public void remove() {
			i.remove();
		}
	}
}
