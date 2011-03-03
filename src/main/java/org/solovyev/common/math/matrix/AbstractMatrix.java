package org.solovyev.common.math.matrix;

import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.LinkedNode;
import org.solovyev.common.math.graph.Node;
import org.solovyev.common.utils.StringsUtils;

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

	public AbstractMatrix(int m, int n, T defaultValue) {
		this.init(m, n, defaultValue);
	}

	public AbstractMatrix(Graph<?, T> g) {
		this.init(g.getNodes().size(), g.getNodes().size());
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				this.setIJ(i, j, this.getEmptyValue());
			}
		}
		for (Node<?, T> node : g.getNodes()) {
			for (LinkedNode<?, T> linkedNode : node.getLinkedNodes()) {
				this.setIJ(node.getId(), linkedNode.getNode().getId(), linkedNode.getArc());
			}
		}
	}

	public AbstractMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
		if (fName != null) {
			BufferedReader in = new BufferedReader(new FileReader(fName));
			String s = in.readLine();
			String[] params = StringsUtils.getParams(s, " ");
			try {
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
						case SIMPLE:
							for (int i = 0; i < this.getNumberOfRows(); i++) {
								s = in.readLine();
								params = StringsUtils.getParams(s, " ");
								if (params != null && params.length == this.getNumberOfColumns()) {
									for (int j = 0; j < this.getNumberOfColumns(); j++) {
										this.setIJ(i, j, this.getValueFromString(params[j]));
									}
								} else {
									throw new IllegalArgumentException();
								}
							}
							break;
						case SHORTED:
							Integer param0;
							Integer param1;
							T param2;
							while ((s = in.readLine()) != null) {
								params = StringsUtils.getParams(s, " ");
								if (params.length > 2) {
									param0 = Integer.valueOf(params[0]) - 1;
									param1 = Integer.valueOf(params[1]) - 1;
									param2 = this.getValueFromString(params[2]);
									this.setIJ(param0, param1, param2);
									this.setIJ(param1, param0, param2);
								}
							}
							break;
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
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

	public void save(String fName, MatrixFileFormat matrixFileFormat) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fName));

		out.write(String.valueOf(this.getNumberOfRows()));
		out.write(" ");
		out.write(String.valueOf(this.getNumberOfColumns()));
		out.newLine();

		T value;
		if (matrixFileFormat.equals(MatrixFileFormat.SHORTED)) {
			for (int i = 0; i < this.getNumberOfRows(); i++) {
				for (int j = 0; j < this.getNumberOfColumns(); j++) {
					value = this.getIJ(i, j);
					if (value != null) {
						if (value instanceof Number) {
							if (((Number) value).doubleValue() > 0) {
								out.write((i + 1) + " " + (j + 1) + " " + value.toString());
							}
						} else {
							if (!value.toString().equals("")) {
								out.write((i + 1) + " " + (j + 1) + " " + value.toString());
							}
						}
						out.newLine();
					}
				}
			}
		} else if (matrixFileFormat.equals(MatrixFileFormat.SIMPLE)) {
			for (int i = 0; i < this.getNumberOfRows(); i++) {
				for (int j = 0; j < this.getNumberOfColumns(); j++) {
					value = this.getIJ(i, j);
					if (value != null) {
						out.write(value.toString() + " ");
					}
				}
				out.newLine();
			}
		}

		out.close();
	}

	protected abstract T getValueFromString(String value) throws InstantiationException, IllegalAccessException;

	protected abstract T getEmptyValue();

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
				result.append(this.getIJ(i, j).toString());
				result.append(" ");
			}
			result.append("/");
		}
		return result.toString();
	}

	public void textDisplay(PrintWriter out) {
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfColumns(); j++) {
				out.write(this.getIJ(i, j).toString() + " ");
			}
			out.println();
		}
	}

	public boolean isSymmetric() {
		boolean result = true;
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < i; j++) {
				if (!getIJ(i, j).equals(getIJ(j, i))) {
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

	public boolean equals(Matrix<T> that) {
		boolean result = true;
		if (this.getNumberOfRows() != that.getNumberOfRows()) {
			result = false;
		} else if (this.getNumberOfColumns() != that.getNumberOfColumns()) {
			result = false;
		}

		if (result) {
			for (int i = 0; i < this.getNumberOfRows(); i++) {
				for (int j = 0; j < this.getNumberOfColumns(); j++) {
					if (!this.getIJ(i, j).equals(that.getIJ(i, j))) {
						result = false;
						break;
					}
				}
				if (!result) {
					break;
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Matrix<T> clone() {
		Matrix<T> result = null;
		try {
			result = (Matrix<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException(e);
		}
		return result;
	}

	@Override
	public final T getIJ(int i, int j) {
		checkIJ(i, j);
		return getCheckedIJ(i, j);
	}

	@Override
	public final void setIJ(int i, int j, T value) {
		checkIJ(i, j);
		setCheckedIJ(i, j, value);
	}

	/**
	 * Same as setIJ(), but i, j are checked on bounds
	 * @param i	 row index already checked for bounds
	 * @param j	 column index already checked for bounds
	 * @param value value to be set on the i-th row nad j-th column
	 */
	protected abstract void setCheckedIJ(int i, int j, T value);

	/**
	 * Same as getIJ(), but i, j are checked on bounds
	 * @param i	 row index already checked for bounds
	 * @param j	 column index already checked for bounds
	 * @return element in i-th row and j-th column
	 */
	protected abstract T getCheckedIJ(int i, int j);

	protected void checkIJ(int i, int j) {
		if ( i < 0 || i >= this.m || j < 0 && j >= this.n ) {
			throw new IndexOutOfBoundsException("Matrix dimensions: " + m + " x " + n + ", i = " + i + ", j = " + j);
		}
	}
}
