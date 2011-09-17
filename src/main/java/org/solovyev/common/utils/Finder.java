package org.solovyev.common.utils;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/17/11
 * Time: 10:14 PM
 */
public interface Finder<T> {

	boolean isFound(@Nullable T object);
}
