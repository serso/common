/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.collections.tree;

import javax.annotation.Nonnull;

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
    @Nonnull
    TreeNode<T> getRoot();

    /**
     * Note: this method might be not fast as iteration over
     * all nodes in the tree might be done
     *
     * @return total number of elements in the tree
     */
    int getSize();

    /**
     * NOTE: returned iterator which may support remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @Nonnull
    @Override
    TreeIterator<T> iterator();

    /**
     * NOTE: returned iterator which may support remove() BUT root cannot be removed in any case
     * @return iterator which iterates over all elements in the tree
     */
    @Nonnull
    TreeIterator<T> getIterator();

    @Nonnull
    List<? extends TreeNode<T>> getAllNodes();
}
