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
public final class DepthTreeIterator<T> implements TreeIterator<T> {

    @NotNull
    private Iterator<? extends TreeNode<T>> iterator;

    @Nullable
    private DepthTreeIterator<T> childIterator;

    private final int depth;

    @Nullable
    private TreeNode<T> lastSelfResult;

    public DepthTreeIterator(@NotNull Collection<? extends TreeNode<T>> nodes) {
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
            } else {
                childIterator = new DepthTreeIterator<T>(lastSelfResult.getOwnChildren(), depth + 1);
            }
        }
        
        if (childrenHasNext()) {
            lastSelfResult = null;
            return this.childIterator.next();
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
