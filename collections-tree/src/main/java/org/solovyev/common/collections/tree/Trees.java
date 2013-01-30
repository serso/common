package org.solovyev.common.collections.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Trees {

    private Trees() {
        throw new AssertionError();
    }

    @NotNull
    public static <N> Tree<N> newTree(@Nullable N root) {
        return TreeImpl.newInstance(root);
    }
    @NotNull
    public static <N> MutableTree<N> newMutableTree(@Nullable N root) {
        return TreeImpl.newInstance(root);
    }

}
