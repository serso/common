package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:15 PM
 */
public interface Tree<T> extends Iterable<TreeNode<T>> {

    @NotNull
    TreeNode<T> getRoot();

    int getSize();

    /**
     * NOTE: returned iterator supports remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @NotNull
    @Override
    Iterator<TreeNode<T>> iterator();

    /**
     * NOTE: returned iterator supports remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @NotNull
    Iterator<TreeNode<T>> getIterator();
}
