package org.solovyev.common.utils;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/20/11
 * Time: 10:35 PM
 */
public interface Formatter<T> {
	/**
	 * Method formats string value of specified object
	 * @param value object to be converted to string
	 *
	 * @return string representation of current object
	 *
	 * @throws IllegalArgumentException illegal argument exception in case of any error (AND ONLY ONE EXCEPTION, I.E. NO NUMBER FORMAT EXCEPTIONS AND SO ON)
	 */
	@Nullable
	String formatValue(@Nullable T value) throws IllegalArgumentException;
}
