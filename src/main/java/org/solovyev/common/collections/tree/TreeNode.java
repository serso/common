package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:19 PM
 */
public interface TreeNode<T> {

    @NotNull
    Collection<? extends TreeNode<T>> getChildren();

    @Nullable
    T getData();
}
