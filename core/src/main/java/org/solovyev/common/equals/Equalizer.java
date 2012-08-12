package org.solovyev.common.equals;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 1/27/11
 * Time: 7:21 PM
 */
public interface Equalizer<T> {

	boolean equals(@Nullable T first, @Nullable T second);
}