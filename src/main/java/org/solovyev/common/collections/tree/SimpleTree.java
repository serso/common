package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.StringUtils;

import java.util.Arrays;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 12:22 PM
 */
public class SimpleTree<T> implements MutableTree<T> {

    @NotNull
    private MutableTreeNode<T> root;

    private SimpleTree() {
    }

    @NotNull
    public static <T> MutableTree<T> newInstance(@Nullable T data) {
        final SimpleTree<T> result = new SimpleTree<T>();

        result.root = SimpleTreeNode.newInstance(data);

        return result;
    }

    @NotNull
    @Override
    public MutableTreeNode<T> getRoot() {
        return this.root;
    }

    @NotNull
    @Override
    public DepthTreeIterator<T> iterator() {
        return new DepthTreeIterator<T>(Arrays.asList(this.root));
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
            result.append(StringUtils.repeat(" ", it.getDepth()));
            final TreeNode<T> node = it.next();
            result.append(node.getData());
            result.append("\n");
        }

        return result.toString();
    }
}
