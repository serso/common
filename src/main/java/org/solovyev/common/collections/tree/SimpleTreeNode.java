package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:24 PM
 */
public class SimpleTreeNode<T> implements MutableTreeNode<T> {

    @Nullable
    private T data;

    private Collection<MutableTreeNode<T>> children = new ArrayList<MutableTreeNode<T>>();

    private SimpleTreeNode() {
    }

    @NotNull
    public static <T> SimpleTreeNode<T> newInstance (@Nullable T data) {
        final SimpleTreeNode<T> result = new SimpleTreeNode<T>();

        result.data = data;

        return result;
    }

    @NotNull
    @Override
    public Collection<MutableTreeNode<T>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public void addChild(@NotNull MutableTreeNode<T> node) {
        this.children.add(node);
    }

    @NotNull
    @Override
    public MutableTreeNode<T> addChild(@NotNull T data) {
        final SimpleTreeNode<T> node = SimpleTreeNode.newInstance(data);
        addChild(node);
        return node;
    }

    @Nullable
    @Override
    public T getData() {
        return this.data;
    }
}
