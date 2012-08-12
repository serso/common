package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:23 PM
 */
public interface Valuer<T> {

	/**
	 * @param item object for which value is counted
	 * @return value of the object
	 */
	@NotNull
	public Double getValue (@NotNull T item);

}
