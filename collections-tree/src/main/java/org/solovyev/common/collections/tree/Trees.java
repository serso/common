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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Trees {

	private Trees() {
		throw new AssertionError();
	}

	/*
	**********************************************************************
	*
	*                           PUBLIC
	*
	**********************************************************************
	*/

	@Nonnull
	public static <N> MutableTree<N> newLinkedTree(@Nullable N root) {
		return LinkedTree.newInstance(root);
	}

	@Nonnull
	public static <N> MutableTree<N> unmodifiableTree(@Nonnull MutableTree<N> tree) {
		return UnmodifiableTree.wrap(tree);
	}

	@Nonnull
	public static <N> MutableTree<N> unmodifiableTree(@Nonnull Tree<N> tree) {
		return UnmodifiableTree.wrap(tree);
	}

	@Nonnull
	public static <N> TreeIterator<N> newDepthTreeIterator(@Nonnull Tree<N> tree) {
		return new DepthTreeIterator<N>(tree);
	}

	@Nonnull
	public static <N> TreeIterator<N> newDepthTreeIterator(@Nonnull TreeNode<N> teeNode) {
		return new DepthTreeIterator<N>(teeNode);
	}


	/*
	**********************************************************************
	*
	*                           PACKAGE LOCAL
	*
	**********************************************************************
	*/

	@Nonnull
	static <T> List<? extends MutableTreeNode<T>> unmodifiableMutableTreeNodes(@Nonnull Collection<? extends MutableTreeNode<T>> treeNodes) {
		final List<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>(treeNodes.size());

		for (MutableTreeNode<T> treeNode : treeNodes) {
			result.add(UnmodifiableTreeNode.wrap(treeNode));
		}

		return Collections.unmodifiableList(result);
	}

	@Nonnull
	static <T> List<? extends MutableTreeNode<T>> unmodifiableTreeNodes(@Nonnull Collection<? extends TreeNode<T>> treeNodes) {
		final List<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>(treeNodes.size());

		for (TreeNode<T> treeNode : treeNodes) {
			result.add(UnmodifiableTreeNode.wrap(treeNode));
		}

		return Collections.unmodifiableList(result);
	}
}
