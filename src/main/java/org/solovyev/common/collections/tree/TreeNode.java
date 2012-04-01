package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:19 PM
 */
public interface TreeNode<T> extends Iterable<TreeNode<T>> {

    @NotNull
    Collection<? extends TreeNode<T>> getOwnChildren();

    // NOTE: this method iterates only through OWN children
    @NotNull
    Iterator<? extends TreeNode<T>> getOwnChildrenIterator();

    @Nullable
    T getData();

    // NOTE: this method iterates through ALL children (children of children etc)
    @Override
    @NotNull
    Iterator<TreeNode<T>> iterator();

    // number of ALL children (children of children etc)
    int getSize();

    boolean isLeaf();
}
