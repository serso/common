package org.solovyev.common.collections.tree;

import com.google.common.base.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:27 PM
 */

/**
 * Mutable interface of TreeNode
 */
public interface MutableTreeNode<T> extends TreeNode<T> {

    void setData(@Nullable T data);

    @NotNull
    Collection<? extends MutableTreeNode<T>> getOwnChildren();

    @NotNull
    Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator();

    @NotNull
    Collection<? extends MutableTreeNode<T>> getAllChildren();

    /**
     * @param node node to be added. The parent specified in the node will be overwritten by this tree node
     */
    void addChild(@NotNull MutableTreeNode<T> node);

    /**
     * @param data data to be added in the node
     * @return just added tree node
     */
    @NotNull
    MutableTreeNode<T> addChild(@NotNull T data);

    /**
     * Method removes from OWN children elements which are applied by predicate
     * @param predicate removal condition
     */
    void removeOwnChildIf(@NotNull Predicate<TreeNode<T>> predicate);

    /**
     * Method removes from ALL children elements which are applied by predicate
     * @param predicate removal condition
     */
    void removeChildIf(@NotNull Predicate<TreeNode<T>> predicate);


    /**
     * @param parent parent of current node
     */
    void setParent(@Nullable TreeNode<T> parent);
}
