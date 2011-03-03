package org.solovyev.common.math.matrix;

import org.solovyev.common.utils.TextDisplay;
import org.solovyev.common.definitions.SimpleCloneable;

import java.io.IOException;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:19:09
 */

/**
 * @param <T> type of element in matrix
 */

// todo serso: remove TextDisplay interface
public interface Matrix<T> extends TextDisplay, SimpleCloneable<Matrix<T>> {

	/**
	 * @param i row index
	 * @param j column index
	 * @return element in i-th row and j-th column
	 */
	public T getIJ(int i, int j);

	/**
	 * @param i	 row index
	 * @param j	 column index
	 * @param value value to be set on the i-th row nad j-th column
	 */
	public void setIJ(int i, int j, T value);

	/**
	 * Initialize m x n matrix (i.e. allocate memory) filled with nulls
	 *
	 * @param m number of rows
	 * @param n number of columns
	 */
	public void init(int m, int n);

	/**
	 * Initialize m x n matrix (i.e. allocate memory) filled with default value
	 *
	 * @param m number of rows
	 * @param n number of columns
	 * @param defaultValue value to be set in all elements of matrix
	 */
	public void init(int m, int n, T defaultValue);

	/**
	 * @return 'true' if matrix has 0 rows and 0 columns
	 */
	public boolean isEmpty();

	/**
	 * @return number of rows
	 */
	public int getNumberOfRows();


	/**
	 * @return number of columns
	 */
	public int getNumberOfColumns();

	/**
	 * Method transposes current matrix
	 */
	public void transpose();

	/**
	 *
	 * @return 'true' if matrix is symmetric, 'false' otherwise
	 */
	public boolean isSymmetric();

	/**
	 * @param that matrix with which current will be compared on equals
	 * @return 'true' is all elements of this matrix equals to all elements of that matrix, 'false' otherwise
	 */
	public boolean equals(Matrix<T> that);

	// todo serso: think about removing from interface
	public void save(String fName, MatrixFileFormat matrixFileFormat) throws IOException;
}
