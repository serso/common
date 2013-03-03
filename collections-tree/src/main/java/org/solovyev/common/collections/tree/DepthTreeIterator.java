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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 1:36 PM
 */

/**
 * Tree iterator implementing depth-first search
 *
 * @param <T> type of data in node
 */
final class DepthTreeIterator<T> implements TreeIterator<T> {

    @NotNull
    private Iterator<? extends TreeNode<T>> iterator;

    @Nullable
    private DepthTreeIterator<T> childIterator;

    private final int depth;

    @Nullable
    private TreeNode<T> lastSelfResult;

    DepthTreeIterator(@NotNull TreeNode<T> root) {
        this(new ArrayList<TreeNode<T>>(Arrays.asList(root)));
    }

	DepthTreeIterator(@NotNull Tree<T> tree) {
		this(tree.getRoot());
	}

    DepthTreeIterator(@NotNull Collection<? extends TreeNode<T>> nodes) {
        this(nodes, 0);
    }

    private DepthTreeIterator(@NotNull Collection<? extends TreeNode<T>> nodes, int depth) {
        this(nodes.iterator(), depth);
    }

    private DepthTreeIterator(@NotNull Iterator<? extends TreeNode<T>> iterator, int depth) {
        this.iterator = iterator;
        this.depth = depth;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || selfHasNext() || childrenHasNext();
    }

    private boolean childrenHasNext() {
        return childIterator != null && childIterator.hasNext();
    }

    private boolean selfHasNext() {
        return lastSelfResult != null && !lastSelfResult.getOwnChildren().isEmpty();
    }

    @Override
    public TreeNode<T> next() {
        if (selfHasNext()) {
            if (lastSelfResult instanceof MutableTreeNode) {
                childIterator = new DepthTreeIterator<T>(((MutableTreeNode<T>) lastSelfResult).getOwnChildrenIterator(), depth + 1);
            } else if ( lastSelfResult != null ) {
                childIterator = new DepthTreeIterator<T>(lastSelfResult.getOwnChildren(), depth + 1);
            } else {
                throw new ConcurrentModificationException("Tree iterator is not tread safe!");
            }
        }
        
        if (childrenHasNext()) {
            lastSelfResult = null;
            if (this.childIterator != null) {
                return this.childIterator.next();
            } else {
                throw new ConcurrentModificationException("Tree iterator is not tread safe!");
            }
        } else {
            childIterator = null;
            lastSelfResult = iterator.next();

            return lastSelfResult;
        }
    }

    @Override
    public int getDepth() {
        if (this.childIterator != null) {
            return this.childIterator.getDepth();
        } else {
            return this.depth;
        }
    }

    @Override
    public void remove() {
        if (this.childIterator != null) {
            this.childIterator.remove();
        } else {
            this.iterator.remove();
        }
    }
}
