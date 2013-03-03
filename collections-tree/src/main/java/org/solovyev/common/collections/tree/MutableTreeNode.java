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
import javax.annotation.Nullable;
import org.solovyev.common.JPredicate;

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

    @Nullable
    MutableTreeNode<T> findOwnChild(@Nonnull JPredicate<TreeNode<T>> finder);

    void setValue(@Nullable T value);

    @Nonnull
    Collection<? extends MutableTreeNode<T>> getOwnChildren();

    @Nonnull
    Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator();

    @Nonnull
    Collection<? extends MutableTreeNode<T>> getAllChildren();

    /**
     * @param node node to be added. The parent specified in the node will be overwritten by this tree node
     */
    void addChild(@Nonnull MutableTreeNode<T> node);

    /**
     * @param value data to be added in the node
     * @return just added tree node
     */
    @Nonnull
    MutableTreeNode<T> addChild(@Nonnull T value);

    @Nonnull
    MutableTreeNode<T>  addChildIfNotExists(@Nonnull T value);

    /**
     * Method removes from OWN children elements which are applied by predicate
     * @param predicate removal condition
     */
    void removeOwnChildIf(@Nonnull JPredicate<TreeNode<T>> predicate);

    /**
     * Method removes from ALL children elements which are applied by predicate
     * @param predicate removal condition
     */
    void removeChildIf(@Nonnull JPredicate<TreeNode<T>> predicate);


    /**
     * @param parent parent of current node
     */
    void setParent(@Nullable TreeNode<T> parent);
}
