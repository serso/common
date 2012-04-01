package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

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
public final class DepthTreeIterator<T> implements Iterator<TreeNode<T>> {

    @NotNull
    private Iterator<? extends TreeNode<T>> iterator;

    @Nullable
    private DepthTreeIterator<T> childIterator;

    private final int depth;

    public DepthTreeIterator(@NotNull Collection<? extends TreeNode<T>> nodes) {
        this(nodes, 0);
    }

    private DepthTreeIterator(@NotNull Collection<? extends TreeNode<T>> nodes, int depth) {
        this.iterator = nodes.iterator();
        this.depth = depth;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || (childIterator != null && childIterator.hasNext());
    }

    @Override
    public TreeNode<T> next() {
        if (childIterator != null) {
            final TreeNode<T> result = this.childIterator.next();

            if (!this.childIterator.hasNext()) {
                this.childIterator = null;
            }

            return result;
        } else {
            final TreeNode<T> result = iterator.next();

            if (!result.getChildren().isEmpty()) {
                childIterator = new DepthTreeIterator<T>(result.getChildren(), depth + 1);
            }

            return result;
        }
    }

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
