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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:19 PM
 */

/**
 * Class representing the element of the tree
 */
public interface TreeNode<T> extends Iterable<TreeNode<T>> {

	@Nullable
	TreeNode<T> findOwnChild(@Nonnull JPredicate<TreeNode<T>> finder);

	/**
	 * NOTE: immutable collection is returned
	 *
	 * @return OWN children of the node (first level children)
	 */
	@Nonnull
	Collection<? extends TreeNode<T>> getOwnChildren();

	/**
	 * @return iterator over the OWN children of the node (first level children).
	 *         This iterator depending on the implementation may support or may not support java.util.Iterator#remove() method.
	 */
	@Nonnull
	Iterator<? extends TreeNode<T>> getOwnChildrenIterator();

	/**
	 * @return data stored with the object (may be null is no actual data is stored)
	 */
	@Nullable
	T getValue();

	// NOTE: this method iterates through ALL children (children of children etc)

	/**
	 * @return iterator over ALL children of the current node (children of children etc).
	 *         This iterator depending on the implementation  may support or may not support java.util.Iterator#remove() method.
	 *         The actual traversal algorithm is not determined - it just guarantees iterating over all children
	 */
	@Override
	@Nonnull
	Iterator<TreeNode<T>> iterator();

	@Nonnull
	Iterator<? extends TreeNode<T>> getIterator();

	@Nonnull
	Collection<? extends TreeNode<T>> getAllChildren();


	/**
	 * @return number of ALL children of current node
	 */
	int getSize();

	/**
	 * @return true if current node is leaf (= has no children)
	 */
	boolean isLeaf();

	/**
	 * @return true if current node is root (=has no parent)
	 */
	boolean isRoot();

	/**
	 * @return depth of the current node in the tree (= number of parents of current node)
	 */
	int getDepth();

	/**
	 * @return parent node to current node (if and only if root then result is null)
	 */
	@Nullable
	TreeNode<T> getParent();
}
