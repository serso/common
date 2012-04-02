package org.solovyev.common.collections.tree;

import java.util.Iterator;

/**
 * User: serso
 * Date: 4/2/12
 * Time: 10:51 PM
 */
public interface TreeIterator<T> extends Iterator<TreeNode<T>> {

    /**
     *
     * @return depth
     */
    int getDepth();
}
