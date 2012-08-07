package org.solovyev.common.math.matrix;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.JCloneable;
import org.solovyev.common.math.matrix.helpers.MatrixHelper;
import org.solovyev.common.utils.TextDisplay;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:19:09
 */

/**
 * @param <T> type of element in matrix
 */

// todo serso: remove TextDisplay interface
public interface Matrix<T> extends TextDisplay, JCloneable<Matrix<T>> {

	/**
	 * @param i row index
	 * @param j column index
	 * @return element in i-th row and j-th column
	 */
	public T get(int i, int j);

	/**
	 * @param i	 row index
	 * @param j	 column index
	 * @param value value to be set on the i-th row nad j-th column
	 */
	public void set(int i, int j, T value);

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

	@NotNull
	public MatrixHelper<T> getMatrixHelper();

	public Class<T> getObjectClass();
}
