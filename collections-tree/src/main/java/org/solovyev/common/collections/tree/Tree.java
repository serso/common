package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:15 PM
 */

/**
 * Class representing tree data structure.
 * @param <T> type of values stored in the tree nodes
 */
public interface Tree<T> extends Iterable<TreeNode<T>> {

    /**
     * @return root of the tree (one unique tree node without parent)
     */
    @NotNull
    TreeNode<T> getRoot();

    /**
     * @return total number of elements in the tree
     */
    int getSize();

    /**
     * NOTE: returned iterator which may support remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @NotNull
    @Override
    TreeIterator<T> iterator();

    /**
     * NOTE: returned iterator which may support remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @NotNull
    TreeIterator<T> getIterator();

    @NotNull
    List<? extends TreeNode<T>> getAllNodes();
}
