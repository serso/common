package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:20 AM
 */

import org.jetbrains.annotations.Nullable;

/**
 * Class represents interface for mapping string value with typed object
 * @param <T>
 */
public interface Mapper<T> {

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

	/**
	 * Method parses specified value and returns converted object
	 * @param value string to be parsed
	 *
	 * @return parsed object
	 *
	 * @throws IllegalArgumentException illegal argument exception in case of any error (AND ONLY ONE EXCEPTION, I.E. NO NUMBER FORMAT EXCEPTIONS AND SO ON)
	 * */
	@Nullable
	T parseValue(@Nullable String value) throws IllegalArgumentException;
}
