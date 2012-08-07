package org.solovyev.common.math.matrix;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:09 AM
 */
public interface ArrayMatrix<T> extends Matrix<T> {

	/**
	 * @return array that represents matrix
	 */
	T[] getArray();

}
