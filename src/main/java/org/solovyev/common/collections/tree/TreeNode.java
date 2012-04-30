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

/**
 * Class representing the element of the tree
 */
public interface TreeNode<T> extends Iterable<TreeNode<T>> {

    /**
     * NOTE: immutable collection is returned
     * @return OWN children of the node (first level children)
     */
    @NotNull
    Collection<? extends TreeNode<T>> getOwnChildren();

    /**
     * @return iterator over the OWN children of the node (first level children).
     * This iterator depending on the implementation may support or may not support java.util.Iterator#remove() method.
     */
    @NotNull
    Iterator<? extends TreeNode<T>> getOwnChildrenIterator();

    /**
     * @return data stored with the object (may be null is no actual data is stored)
     */
    @Nullable
    T getData();

    // NOTE: this method iterates through ALL children (children of children etc)

    /**
     * @return iterator over ALL children of the current node (children of children etc).
     * This iterator depending on the implementation  may support or may not support java.util.Iterator#remove() method.
     * The actual traversal algorithm is not determined - it just guarantees iterating over all children
     */
    @Override
    @NotNull
    Iterator<TreeNode<T>> iterator();

    @NotNull
    Iterator<? extends TreeNode<T>> getIterator();

    @NotNull
    Collection<? extends TreeNode<T>> getAllChildren();


    /**
     * @return number of ALL children of current node
     */
    int getSize();

    /**
     * @return true if current node is leaf (= has no children)
     */
    boolean isLeaf();

    /**
     * @return true if current node is root (=has no parent)
     */
    boolean isRoot();

    /**
     * @return depth of the current node in the tree (= number of parents of current node)
     */
    int getDepth();

    /**
     * @return parent node to current node (if and only if root then result is null)
     */
    @Nullable
    TreeNode<T> getParent();
}
