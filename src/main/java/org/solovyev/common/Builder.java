package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 4/28/12
 * Time: 1:42 AM
 */
public interface Builder<T, D> {

    @NotNull
    T build(D data);
}
