
package org.solovyev.common.utils;

/**
 * User: serso
 * Date: Jan 18, 2010
 * Time: 10:53:37 AM
 */

/**
 * Class using in list filtration
 * @param <T> type of object in list
 */
public interface FilterRule<T> {

	/**
	 * Method indicates filtration of element
	 * @param object current element in list
	 * @return 'true' if element is filtered of 'false' otherwise
	 */
	public boolean isFiltered(T object) ;
}

