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
import org.solovyev.common.collections.Collections;
import org.solovyev.common.text.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:22 PM
 */
class LinkedTree<T> implements MutableTree<T> {

	@Nonnull
	private MutableTreeNode<T> root;

	@edu.umd.cs.findbugs.annotations.SuppressWarnings(value = {"NP_NONNULL_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR"})
	private LinkedTree() {
	}

	@Nonnull
	static <T> MutableTree<T> newInstance(@Nullable T root) {
		final LinkedTree<T> result = new LinkedTree<T>();

		result.root = LinkedTreeNode.newInstance(root);

		return result;
	}

	@Nonnull
	@Override
	public MutableTreeNode<T> getRoot() {
		return this.root;
	}

	@Override
	public int getSize() {
		// root + children
		int result = 1 + this.root.getOwnChildren().size();

		for (MutableTreeNode<T> child : root.getOwnChildren()) {
			result += child.getSize();
		}

		return result;
	}

	@Override
	public void removeNodeIf(@Nonnull JPredicate<? super TreeNode<T>> filter) {
		Collections.removeIf(this.iterator(), filter);
	}

	@Nonnull
	@Override
	public List<? extends MutableTreeNode<T>> getAllNodes() {
		final List<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>();
		result.add(this.root);
		result.addAll(this.root.getAllChildren());
		return result;
	}

	@Nonnull
	@Override
	public DepthTreeIterator<T> iterator() {
		return new DepthTreeIterator<T>(this.root);
	}

	@Nonnull
	@Override
	public DepthTreeIterator<T> getIterator() {
		return iterator();
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();

		for (DepthTreeIterator<T> it = iterator(); it.hasNext(); ) {
			final TreeNode<T> node = it.next();
			result.append(Strings.repeat(" ", it.getDepth()));
			result.append(node.getValue());
			result.append("\n");
		}

		return result.toString();
	}
}
