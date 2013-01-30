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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.JCollections;

import java.util.*;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:24 PM
 */
public class TreeNodeImpl<T> implements MutableTreeNode<T> {

    @Nullable
    private T data;

    @Nullable
    private TreeNode<T> parent;

    @NotNull
    private Collection<MutableTreeNode<T>> children = new ArrayList<MutableTreeNode<T>>();

    private TreeNodeImpl() {
    }

    @NotNull
    public static <T> TreeNodeImpl<T> newInstance(@Nullable T data) {
        final TreeNodeImpl<T> result = new TreeNodeImpl<T>();

        result.data = data;

        return result;
    }

    @Nullable
    @Override
    public MutableTreeNode<T> findOwnChild(@NotNull JPredicate<TreeNode<T>> finder) {
        return JCollections.find(children.iterator(), finder);
    }

    @Override
    public void setData(@Nullable T data) {
        this.data = data;
    }

    @NotNull
    @Override
    public Collection<MutableTreeNode<T>> getOwnChildren() {
        return Collections.unmodifiableCollection(children);
    }

    @NotNull
    @Override
    public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
        return this.children.iterator();
    }

    @NotNull
    @Override
    public Collection<? extends MutableTreeNode<T>> getAllChildren() {
        final Collection<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>(children);

        for (MutableTreeNode<T> child : children) {
            result.addAll(child.getAllChildren());
        }

        return result;
    }

    @Override
    public void addChild(@NotNull MutableTreeNode<T> node) {
        node.setParent(this);
        this.children.add(node);
    }

    @NotNull
    @Override
    public MutableTreeNode<T> addChild(@NotNull T data) {
        final TreeNodeImpl<T> node = TreeNodeImpl.newInstance(data);
        addChild(node);
        return node;
    }

    @NotNull
    @Override
    public MutableTreeNode<T> addChildIfNotExists(@NotNull final T data) {
        MutableTreeNode<T> result = this.findOwnChild(new JPredicate<TreeNode<T>>() {
            @Override
            public boolean apply(@Nullable TreeNode<T> input) {
                return input != null && data.equals(input.getData());
            }
        });

        if ( result == null ) {
            result = this.addChild(data);
        }

        return result;
    }

    @Override
    public void removeOwnChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
        JCollections.removeIf(this.children.iterator(), predicate);
    }

    @Override
    public void removeChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
        JCollections.removeIf(this.iterator(), predicate);
    }

    @Nullable
    @Override
    public T getData() {
        return this.data;
    }

    @NotNull
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new DepthTreeIterator<T>(this.children);
    }

    @NotNull
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
                "data=" + data +
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
