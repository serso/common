package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.IPredicate;
import org.solovyev.common.collections.CollectionsUtils;
import org.solovyev.common.text.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:22 PM
 */
public class TreeImpl<T> implements MutableTree<T> {

    @NotNull
    private MutableTreeNode<T> root;

    private TreeImpl() {
    }

    @NotNull
    public static <T> MutableTree<T> newInstance(@Nullable T data) {
        final TreeImpl<T> result = new TreeImpl<T>();

        result.root = TreeNodeImpl.newInstance(data);

        return result;
    }

    @NotNull
    @Override
    public MutableTreeNode<T> getRoot() {
        return this.root;
    }

    @Override
    public int getSize() {
        // root + children
        int result = 1 + this.root.getOwnChildren().size();

        for (MutableTreeNode<T> child : root.getOwnChildren()) {
            result += child.getSize();
        }

        return result;
    }

    @Override
    public void removeNodeIf(@NotNull IPredicate<? super TreeNode<T>> filter) {
        CollectionsUtils.removeIf(this.iterator(), filter);
    }

    @NotNull
    @Override
    public List<? extends MutableTreeNode<T>> getAllNodes() {
        final List<MutableTreeNode<T>> result = new ArrayList<MutableTreeNode<T>>();
        result.add(this.root);
        result.addAll(this.root.getAllChildren());
        return result;
    }

    @NotNull
    @Override
    public DepthTreeIterator<T> iterator() {
        return new DepthTreeIterator<T>(this.root);
    }

    @NotNull
    @Override
    public DepthTreeIterator<T> getIterator() {
        return iterator();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        for ( DepthTreeIterator<T> it = iterator(); it.hasNext(); ) {
            final TreeNode<T> node = it.next();
            result.append(StringUtils.repeat(" ", it.getDepth()));
            result.append(node.getData());
            result.append("\n");
        }

        return result.toString();
    }
}
