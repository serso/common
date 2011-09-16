package org.solovyev.common.math.matrix;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.LinkedNode;
import org.solovyev.common.math.graph.Node;
import org.solovyev.common.utils.StringUtils;

import java.io.*;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:19:46
 */

/**
 * Abstract implementation of matrix interface
 * NOTE: most of method has not to be final as more efficient implementations can be done
 *
 * @param <T>
 */
public abstract class AbstractMatrix<T> implements Matrix<T> {

	//number of rows
	protected int m = 0;

	//number of columns
	protected int n = 0;

	// default number of columns
	protected final static int DEFAULT_M_SIZE = 2;

	// default number of rows
	protected final static int DEFAULT_N_SIZE = 2;

	public AbstractMatrix() {
		this(DEFAULT_M_SIZE, DEFAULT_N_SIZE, null);
	}

	public AbstractMatrix(int m) {
		this(m, m, null);
	}

	public AbstractMatrix(int m, int n, @Nullable T defaultValue) {
		this.init(m, n, defaultValue);
	}

	public AbstractMatrix(Graph<?, T> g) {
		this.init(g.getNodes().size(), g.getNodes().size());

		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.set(i, j, this.getEmptyValue());
			}
		}

		for (Node<?, T> node : g.getNodes()) {
			for (LinkedNode<?, T> linkedNode : node.getLinkedNodes()) {
				this.set(node.getId(), linkedNode.getNode().getId(), linkedNode.getArc());
			}
		}
	}

	public AbstractMatrix(String fName, MatrixFileFormat fileFormat ) throws IOException, IllegalArgumentException {
		if (fName != null) {
			BufferedReader in = new BufferedReader(new FileReader(fName));
			String s = in.readLine();
			String[] params = StringUtils.split(s, " ");

			if (params != null && params.length > 0) {
				if (params.length == 1) {
					Integer size = Integer.valueOf(params[0]);
					this.init(size, size);
				} else {
					Integer m = Integer.valueOf(params[0]);
					Integer n = Integer.valueOf(params[1]);
					this.init(m, n);
				}
				switch (fileFormat) {
					case dense:
						for (int i = 0; i < this.getNumberOfRows(); i++) {
							s = in.readLine();
							params = StringUtils.split(s, " ");
							if (params != null && params.length == this.getNumberOfColumns()) {
								for (int j = 0; j < this.getNumberOfColumns(); j++) {
									this.set(i, j, this.getValueFromString(params[j]));
								}
							} else {
								throw new IllegalArgumentException();
							}
						}
						break;
					case sparse:
						Integer param0;
						Integer param1;
						T param2;
						while ((s = in.readLine()) != null) {
							params = StringUtils.split(s, " ");
							if (params.length > 2) {
								param0 = Integer.valueOf(params[0]) - 1;
								param1 = Integer.valueOf(params[1]) - 1;
								param2 = this.getValueFromString(params[2]);
								this.set(param0, param1, param2);
								this.set(param1, param0, param2);
							}
						}
						break;
				}
			}

			in.close();
		}
	}

	public void init(int m, int n) {
		this.init(m, n, null);
	}

	public boolean isEmpty() {
		return (this.m * this.n == 0d);
	}

	protected final T getValueFromString(String value) {
		return this.getMatrixHelper().getValueFromString(value);
	}

	protected final T getEmptyValue() {
		return this.getMatrixHelper().getEmptyValue();
	}

	public final Class<T> getObjectClass() {
		return this.getMatrixHelper().getObjectClass();
	}

	public int getNumberOfRows() {
		return this.m;
	}

	public int getNumberOfColumns() {
		return this.n;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfColumns(); j++) {
				result.append(this.get(i, j).toString());
				result.append(" ");
			}
			result.append("/");
		}
		return result.toString();
	}

	public void textDisplay(PrintWriter out) {
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfColumns(); j++) {
				out.write(this.get(i, j).toString() + " ");
			}
			out.println();
		}
	}

	public boolean isSymmetric() {
		boolean result = true;
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < i; j++) {
				if (!get(i, j).equals(get(j, i))) {
					result = false;
					break;
				}
			}
			if (!result) {
				break;
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) return true;

		if (!AbstractMatrix.class.isAssignableFrom(that.getClass())) return false;

		if (((AbstractMatrix) that).getObjectClass() != this.getObjectClass()) return false;

		//noinspection unchecked
		return MatrixUtils.areEqual(this, (AbstractMatrix)that);
	}

	public Matrix<T> clone() {
		Matrix<T> result;
		try {
			//noinspection unchecked
			result = (Matrix<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException(e);
		}
		return result;
	}

	@Override
	public final T get(int i, int j) {
		checkIJ(i, j);
		return getChecked(i, j);
	}

	@Override
	public final void set(int i, int j, T value) {
		checkIJ(i, j);
		setChecked(i, j, value);
	}

	/**
	 * Same as set(), but i, j are checked on bounds
	 *
	 * @param i	 row index already checked for bounds
	 * @param j	 column index already checked for bounds
	 * @param value value to be set on the i-th row nad j-th column
	 */
	protected abstract void setChecked(int i, int j, T value);

	/**
	 * Same as get(), but i, j are checked on bounds
	 *
	 * @param i row index already checked for bounds
	 * @param j column index already checked for bounds
	 * @return element in i-th row and j-th column
	 */
	protected abstract T getChecked(int i, int j);

	protected void checkIJ(int i, int j) {
		if (i < 0 || i >= this.m || j < 0 && j >= this.n) {
			throw new IndexOutOfBoundsException("Matrix dimensions: " + m + " x " + n + ", i = " + i + ", j = " + j);
		}
	}

	public final Matrix<T> multiply(Matrix<T> that) {
		return MatrixUtils.multiply(this, that);
	}

	@Override
	public int hashCode() {
		int result = m;
		result = 31 * result + n;
		return result;
	}
}
