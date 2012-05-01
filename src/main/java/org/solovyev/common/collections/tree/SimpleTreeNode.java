package org.solovyev.common.collections.tree;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:24 PM
 */
public class SimpleTreeNode<T> implements MutableTreeNode<T> {

    @Nullable
    private T data;

    @Nullable
    private TreeNode<T> parent;

    @NotNull
    private Collection<MutableTreeNode<T>> children = new ArrayList<MutableTreeNode<T>>();

    private SimpleTreeNode() {
    }

    @NotNull
    public static <T> SimpleTreeNode<T> newInstance(@Nullable T data) {
        final SimpleTreeNode<T> result = new SimpleTreeNode<T>();

        result.data = data;

        return result;
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
        final Collection<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>();

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
        final SimpleTreeNode<T> node = SimpleTreeNode.newInstance(data);
        addChild(node);
        return node;
    }

    @Override
    public void removeOwnChildIf(@NotNull Predicate<TreeNode<T>> predicate) {
        Iterables.removeIf(this.children, predicate);
    }

    @Override
    public void removeChildIf(@NotNull Predicate<TreeNode<T>> predicate) {
        Iterables.removeIf(this, predicate);
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
