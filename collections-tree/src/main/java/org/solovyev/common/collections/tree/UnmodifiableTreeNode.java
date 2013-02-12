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
import org.solovyev.common.collections.UnmodifiableIterator;

import java.util.Collection;
import java.util.Iterator;

class UnmodifiableTreeNode<T> implements MutableTreeNode<T> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final MutableTreeNode<T> treeNode;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private UnmodifiableTreeNode(@NotNull MutableTreeNode<T> treeNode) {
        this.treeNode = treeNode;
    }

    @NotNull
    public static <T> UnmodifiableTreeNode<T> wrap(@NotNull MutableTreeNode<T> treeNode) {
        return new UnmodifiableTreeNode<T>(treeNode);
    }

    @NotNull
    public static <T> UnmodifiableTreeNode<T> wrap(@NotNull TreeNode<T> treeNode) {
        if (treeNode instanceof MutableTreeNode) {
            return wrap((MutableTreeNode<T>)treeNode);
        } else {
            return new UnmodifiableTreeNode<T>(TreeNodeAdapter.adapt(treeNode));
        }
    }


    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @Nullable
    public MutableTreeNode<T> findOwnChild(@NotNull JPredicate<TreeNode<T>> finder) {
        return treeNode.findOwnChild(finder);
    }

    public void setValue(@Nullable T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Collection<? extends MutableTreeNode<T>> getOwnChildren() {
        return Trees.unmodifiableMutableTreeNodes(treeNode.getOwnChildren());
    }

    @Override
    @NotNull
    public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
        return UnmodifiableIterator.wrap(treeNode.getOwnChildrenIterator());
    }

    @Override
    @NotNull
    public Collection<? extends MutableTreeNode<T>> getAllChildren() {
        return Trees.unmodifiableMutableTreeNodes(treeNode.getAllChildren());
    }

    public void addChild(@NotNull MutableTreeNode<T> node) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public MutableTreeNode<T> addChild(@NotNull T value) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public MutableTreeNode<T> addChildIfNotExists(@NotNull T value) {
        throw new UnsupportedOperationException();
    }

    public void removeOwnChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
        throw new UnsupportedOperationException();
    }

    public void removeChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
        throw new UnsupportedOperationException();
    }

    public void setParent(@Nullable TreeNode<T> parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public T getValue() {
        return treeNode.getValue();
    }

    @Override
    @NotNull
    public Iterator<TreeNode<T>> iterator() {
        return UnmodifiableIterator.wrap(treeNode.iterator());
    }

    @Override
    @NotNull
    public Iterator<? extends TreeNode<T>> getIterator() {
        return UnmodifiableIterator.wrap(treeNode.getIterator());
    }

    @Override
    public int getSize() {
        return treeNode.getSize();
    }

    @Override
    public boolean isLeaf() {
        return treeNode.isLeaf();
    }

    @Override
    public boolean isRoot() {
        return treeNode.isRoot();
    }

    @Override
    public int getDepth() {
        return treeNode.getDepth();
    }

    @Override
    @Nullable
    public TreeNode<T> getParent() {
        final TreeNode<T> result = treeNode.getParent();
        if (result != null) {
            return wrap(result);
        } else {
            return null;
        }
    }

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    private static class TreeNodeAdapter<T> implements MutableTreeNode<T> {

        @NotNull
        private final TreeNode<T> treeNode;

        private TreeNodeAdapter(@NotNull TreeNode<T> treeNode) {
            this.treeNode = treeNode;
        }

        @NotNull
        private static <T> TreeNodeAdapter<T> adapt(@NotNull TreeNode<T> treeNode) {
            return new TreeNodeAdapter<T>(treeNode);
        }

        @Nullable
        public MutableTreeNode<T> findOwnChild(@NotNull JPredicate<TreeNode<T>> finder) {
            final TreeNode<T> result = treeNode.findOwnChild(finder);
            if (result != null) {
                return wrap(result);
            } else {
                return null;
            }
        }

        @Override
        public void setValue(@Nullable T value) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        @NotNull
        public Collection<? extends MutableTreeNode<T>> getOwnChildren() {
            return Trees.unmodifiableTreeNodes(treeNode.getOwnChildren());
        }

        @Override
        @NotNull
        public Iterator<? extends MutableTreeNode<T>> getOwnChildrenIterator() {
            return UnmodifiableIterator.wrap(TreeNodeIteratorAdapter.adapt(treeNode.getOwnChildrenIterator()));
        }

        @Override
        @Nullable
        public T getValue() {
            return treeNode.getValue();
        }

        @Override
        @NotNull
        public Iterator<TreeNode<T>> iterator() {
            return UnmodifiableIterator.wrap(treeNode.iterator());
        }

        @Override
        @NotNull
        public Iterator<? extends TreeNode<T>> getIterator() {
            return UnmodifiableIterator.wrap(treeNode.getIterator());
        }

        @Override
        @NotNull
        public Collection<? extends MutableTreeNode<T>> getAllChildren() {
            return Trees.unmodifiableTreeNodes(treeNode.getAllChildren());
        }

        @Override
        public void addChild(@NotNull MutableTreeNode<T> node) {
            throw new AssertionError("Should never happen!");
        }

        @NotNull
        @Override
        public MutableTreeNode<T> addChild(@NotNull T value) {
            throw new AssertionError("Should never happen!");
        }

        @NotNull
        @Override
        public MutableTreeNode<T> addChildIfNotExists(@NotNull T value) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        public void removeOwnChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        public void removeChildIf(@NotNull JPredicate<TreeNode<T>> predicate) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        public void setParent(@Nullable TreeNode<T> parent) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        public int getSize() {
            return treeNode.getSize();
        }

        @Override
        public boolean isLeaf() {
            return treeNode.isLeaf();
        }

        @Override
        public boolean isRoot() {
            return treeNode.isRoot();
        }

        @Override
        public int getDepth() {
            return treeNode.getDepth();
        }

        @Override
        @Nullable
        public TreeNode<T> getParent() {
            return treeNode.getParent();
        }
    }

    private static class TreeNodeIteratorAdapter<T> implements Iterator<MutableTreeNode<T>> {

        @NotNull
        private final Iterator<? extends TreeNode<T>> i;

        private TreeNodeIteratorAdapter(@NotNull Iterator<? extends TreeNode<T>> i) {
            this.i = i;
        }

        @NotNull
        private static <T> Iterator<MutableTreeNode<T>> adapt(@NotNull Iterator<? extends TreeNode<T>> i) {
            return new TreeNodeIteratorAdapter<T>(i);
        }

        @Override
        public boolean hasNext() {
            return i.hasNext();
        }

        @Override
        public MutableTreeNode<T> next() {
            return wrap(i.next());
        }

        @Override
        public void remove() {
            i.remove();
        }
    }
}
