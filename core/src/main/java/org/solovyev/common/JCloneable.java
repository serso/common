package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 6/9/12
 * Time: 8:40 PM
 */

/**
 * Common interface for clonable objects. Suppresses {@link CloneNotSupportedException}, has a explicit
 * {@link #clone()} method declaration and use type T for it's result
 * @param <T> type of cloned object
 */
public interface JCloneable<T extends JCloneable<T>> extends Cloneable {

    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    @NotNull
    T clone();
}
