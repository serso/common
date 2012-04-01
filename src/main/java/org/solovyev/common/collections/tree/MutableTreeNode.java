package org.solovyev.common.collections.tree;

import com.google.common.base.Predicate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:27 PM
 */
public interface MutableTreeNode<T> extends TreeNode<T> {

    @NotNull
    Collection<? extends MutableTreeNode<T>> getOwnChildren();

    @NotNull
    Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator();

    /**
     * @param node node to be added
     */
    void addChild(@NotNull MutableTreeNode<T> node);

    /**
     * @param data data to be added in the node
     * @return just added tree node
     */
    @NotNull
    MutableTreeNode<T> addChild(@NotNull T data);

    void removeOwnChildIf(@NotNull Predicate<TreeNode<T>> predicate);

    void removeChildIf(@NotNull Predicate<TreeNode<T>> predicate);
}
