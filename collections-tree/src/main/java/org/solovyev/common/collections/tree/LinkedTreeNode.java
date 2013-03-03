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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:24 PM
 */
class LinkedTreeNode<T> implements MutableTreeNode<T> {

    @Nullable
    private T value;

    @Nullable
    private TreeNode<T> parent;

    @Nonnull
    private Collection<MutableTreeNode<T>> children = new ArrayList<MutableTreeNode<T>>();

    private LinkedTreeNode() {
    }

    @Nonnull
    static <T> LinkedTreeNode<T> newInstance(@Nullable T value) {
        final LinkedTreeNode<T> result = new LinkedTreeNode<T>();

        result.value = value;

        return result;
    }

    @Nullable
    @Override
    public MutableTreeNode<T> findOwnChild(@Nonnull JPredicate<TreeNode<T>> finder) {
        return org.solovyev.common.collections.Collections.find(children.iterator(), finder);
    }

    @Override
    public void setValue(@Nullable T value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Collection<MutableTreeNode<T>> getOwnChildren() {
        return java.util.Collections.unmodifiableCollection(children);
    }

    @Nonnull
    @Override
    public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
        return this.children.iterator();
    }

    @Nonnull
    @Override
    public Collection<? extends MutableTreeNode<T>> getAllChildren() {
        final Collection<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>(children);

        for (MutableTreeNode<T> child : children) {
            result.addAll(child.getAllChildren());
        }

        return result;
    }

    @Override
    public void addChild(@Nonnull MutableTreeNode<T> node) {
        node.setParent(this);
        this.children.add(node);
    }

    @Nonnull
    @Override
    public MutableTreeNode<T> addChild(@Nonnull T value) {
        final LinkedTreeNode<T> node = LinkedTreeNode.newInstance(value);
        addChild(node);
        return node;
    }

    @Nonnull
    @Override
    public MutableTreeNode<T> addChildIfNotExists(@Nonnull final T value) {
        MutableTreeNode<T> result = this.findOwnChild(new JPredicate<TreeNode<T>>() {
            @Override
            public boolean apply(@Nullable TreeNode<T> input) {
                return input != null && value.equals(input.getValue());
            }
        });

        if ( result == null ) {
            result = this.addChild(value);
        }

        return result;
    }

    @Override
    public void removeOwnChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
        org.solovyev.common.collections.Collections.removeIf(this.children.iterator(), predicate);
    }

    @Override
    public void removeChildIf(@Nonnull JPredicate<TreeNode<T>> predicate) {
        org.solovyev.common.collections.Collections.removeIf(this.iterator(), predicate);
    }

    @Nullable
    @Override
    public T getValue() {
        return this.value;
    }

    @Nonnull
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new DepthTreeIterator<T>(this.children);
    }

    @Nonnull
    @Override
    public Iterator<? extends TreeNode<T>> getIterator() {
        return iterator();
    }

    @Override
    public int getSize() {
        int result = children.size();

        for (MutableTreeNode<T> child : children) {
            result += child.getSize();
        }

        return result;
    }

    @Override
    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    @Override
    public boolean isRoot() {
        return this.parent == null;
    }

    @Override
    public int getDepth() {
        int depth = 0;

        TreeNode<?> parent = this.parent;
        while( parent != null ) {
            parent = parent.getParent();
            depth++;
        }

        return depth;
    }

    @Override
    public String toString() {
        return "SimpleTreeNode{" +
                "value=" + value +
                ", number of own children=" + children.size() +
                '}';
    }

    @Override
    @Nullable
    public TreeNode<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(@Nullable TreeNode<T> parent) {
        this.parent = parent;
    }
}
