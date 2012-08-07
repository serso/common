package org.solovyev.common.math.matrix;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:24 AM
 */
public interface MathMatrix<T> extends Matrix<T> {

	/**
	 * Method multiplies this matrix to another (this matrix - left part, that matrix - right)
	 *
	 * @param that right matrix of multiplication
	 * @return product of two matrices
	 */
	public Matrix<T> multiply(Matrix<T> that);
}
