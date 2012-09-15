package org.solovyev.common;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 8/7/12
 * Time: 6:26 PM
 */

/**
 * Predicate, copy of Guava's {@link com.google.common.base.Predicate}
 */
public interface JPredicate<T> {

    boolean apply(@Nullable T t);
}
