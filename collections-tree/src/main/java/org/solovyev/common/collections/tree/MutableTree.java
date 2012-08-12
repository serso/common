package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.IPredicate;

import java.util.List;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 1:39 PM
 */
public interface MutableTree<T> extends Tree<T> {

    @NotNull
    MutableTreeNode<T> getRoot();

    void removeNodeIf(@NotNull IPredicate<? super TreeNode<T>> filter);

    @NotNull
    @Override
    List<? extends MutableTreeNode<T>> getAllNodes();
}
