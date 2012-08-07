package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 4/28/12
 * Time: 1:42 AM
 */
/**
* Builder with data - creates object of specified type T with some data needed on the time of creation
* NOTE: if no data is needed use {@link org.solovyev.common.Builder}
 * @see org.solovyev.common.Builder
* */
public interface BuilderWithData<T, D> {

    @NotNull
    T build(D data);
}
