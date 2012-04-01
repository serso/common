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

    @NotNull
    @Override
    Iterator<TreeNode<T>> iterator();

    @NotNull
    Iterator<TreeNode<T>> getIterator();
}
