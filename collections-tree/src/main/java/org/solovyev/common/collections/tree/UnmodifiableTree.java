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
import org.solovyev.common.JPredicate;

import java.util.List;

class UnmodifiableTree<T> implements MutableTree<T> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final MutableTree<T> t;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTOR
    *
    **********************************************************************
    */

    private UnmodifiableTree(@NotNull MutableTree<T> t) {
        this.t = t;
    }

    public static <T> UnmodifiableTree<T> wrap(@NotNull MutableTree<T> t) {
        return new UnmodifiableTree<T>(t);
    }


    public static <T> UnmodifiableTree<T> wrap(@NotNull Tree<T> t) {
        if (t instanceof MutableTree) {
            return wrap((MutableTree<T>)t);
        } else {
            return new UnmodifiableTree<T>(TreeAdapter.adapt(t));
        }
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @NotNull
    @Override
    public MutableTreeNode<T> getRoot() {
        return t.getRoot();
    }

    @Override
    public int getSize() {
        return t.getSize();
    }

    @NotNull
    @Override
    public TreeIterator<T> iterator() {
        return t.iterator();
    }

    @NotNull
    @Override
    public TreeIterator<T> getIterator() {
        return t.getIterator();
    }

    @Override
    public void removeNodeIf(@NotNull JPredicate<? super TreeNode<T>> filter) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public List<? extends MutableTreeNode<T>> getAllNodes() {
        return t.getAllNodes();
    }

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    private static class TreeAdapter<T> implements MutableTree<T> {

        @NotNull
        private final Tree<T> tree;

        private TreeAdapter(@NotNull Tree<T> tree) {
            this.tree = tree;
        }

        @NotNull
        private static <T> TreeAdapter<T> adapt(@NotNull Tree<T> tree) {
            return new TreeAdapter<T>(tree);
        }

        @Override
        @NotNull
        public MutableTreeNode<T> getRoot() {
            return UnmodifiableTreeNode.wrap(tree.getRoot());
        }

        @Override
        public void removeNodeIf(@NotNull JPredicate<? super TreeNode<T>> filter) {
            throw new AssertionError("Should never happen!");
        }

        @Override
        public int getSize() {
            return tree.getSize();
        }

        @NotNull
        @Override
        public TreeIterator<T> iterator() {
            return tree.iterator();
        }

        @Override
        @NotNull
        public TreeIterator<T> getIterator() {
            return tree.getIterator();
        }

        @Override
        @NotNull
        public List<? extends MutableTreeNode<T>> getAllNodes() {
            return Trees.unmodifiableTreeNodes(tree.getAllNodes());
        }
    }
}
