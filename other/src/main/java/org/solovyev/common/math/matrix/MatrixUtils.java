package org.solovyev.common.math.matrix;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.JCollections;
import org.solovyev.common.definitions.Property;
import org.solovyev.common.exceptions.AnyRuntimeException;
import org.solovyev.common.exceptions.IllegalMatrixFormatException;
import org.solovyev.common.math.algorithms.matrix.BinaryMatrixOperationInput;
import org.solovyev.common.math.algorithms.matrix.MatrixEquals;
import org.solovyev.common.math.algorithms.matrix.MatrixMultiplication;
import org.solovyev.common.text.JStrings;

import java.io.*;
import java.util.List;

/**
 * User: serso
 * Date: 15.04.2009
 * Time: 12:36:52
 */
public class MatrixUtils {

	public static <T> Matrix<T> getTransposeMatrix(Matrix<T> m) {
		Matrix<T> result = m.clone();
		result.transpose();
		return result;
	}

	public static <T> Matrix<T> multiply(final Matrix<T> l, final Matrix<T> r) {
		return new MatrixMultiplication<T>().init(new BinaryMatrixOperationInput<T>(l, r)).doAlgorithm();
	}

	public static Matrix<Double> copyMatrix(Matrix<Double> m, int startRow, int startCol, int endRow, int endCol) {
		final Matrix<Double> result = initMatrix(m.getClass(), endRow - startRow, endCol - startCol);

		for (int i = startRow; i < endRow; i++) {
			for (int j = startCol; j < endCol; j++) {
				result.set(i, j, m.get(i, j));
			}
		}

		return result;
	}


	public static Matrix<Double> difference(Matrix<Double> l, Matrix<Double> r) {
		final Matrix<Double> result = initMatrix(l.getClass(), l.getNumberOfRows(), l.getNumberOfColumns());

		for (int i = 0; i < result.getNumberOfRows(); i++) {
			for (int j = 0; j < result.getNumberOfColumns(); j++) {
				result.set(i, j, l.get(i, j) - r.get(i, j));
			}
		}

		return result;
	}

	@NotNull
	public static <K extends Matrix> K initMatrix(Class<K> klass, int numberOfRows, int numberOfColumns) {
		final K result;

		try {
			result = klass.newInstance();
		} catch (InstantiationException e) {
			throw new AnyRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new AnyRuntimeException(e);
		}

		result.init(numberOfRows, numberOfColumns);

		return result;
	}

	public static <T extends Number> boolean isEMatrix(Matrix<T> m, Double error) {
		boolean isE = true;
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				if (i == j) {
					if (Math.abs(m.get(i, j).doubleValue() - 1d) > error) {
						isE = false;
						break;
					}
				} else {
					if (Math.abs(m.get(i, j).doubleValue()) > error) {
						isE = false;
						break;
					}
				}
			}
			if (!isE) {
				break;
			}
		}
		return isE;
	}

	public static <T extends Number> boolean isSame(Matrix<T> m1, Matrix<T> m2, Double error) {
		boolean result = true;
		if (m1.getNumberOfRows() != m2.getNumberOfRows()) {
			result = false;
		} else if (m1.getNumberOfColumns() != m2.getNumberOfColumns()) {
			result = false;
		}

		if (result) {
			for (int i = 0; i < m1.getNumberOfRows(); i++) {
				for (int j = 0; j < m1.getNumberOfColumns(); j++) {
					if (Math.abs(m1.get(i, j).doubleValue() - m2.get(i, j).doubleValue()) > error) {
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

	public static void saveMatrixInMatlabRepresentation(Matrix<Double> m, String fName) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fName));

		Double value;
		if (m instanceof AbstractSparseMatrix) {
			int index;
			for (int i = 0; i < m.getNumberOfRows(); i++) {
				List<Property<Double, Integer>> row = ((AbstractSparseMatrix<Double>) m).getRows().get(i);
				if (row != null) {
					index = 0;
					for (Property<Double, Integer> element : row) {
						while (index < element.getId()) {
							out.write(0d + " ");
							index++;
						}
						out.write(element.getValue() + " ");
						index++;
					}
				} else {
					for (int j = 0; j < m.getNumberOfColumns(); j++) {
						out.write(0d + " ");
					}
				}
				out.newLine();
			}
		} else {
			for (int i = 0; i < m.getNumberOfRows(); i++) {
				for (int j = 0; j < m.getNumberOfColumns(); j++) {
					value = m.get(i, j);
					if (value != null) {
						out.write(value.toString() + " ");
					}
				}
				out.newLine();
			}
		}

		out.close();
	}


	public static <T extends Number> int getBandWidth(Matrix<T> m) {
		int result = 0;
		int bandWidth;
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			bandWidth = 0;
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				if (Math.abs(m.get(i, j).doubleValue()) > 0) {
					bandWidth = m.getNumberOfColumns() / 2 - j;
					break;
				}
			}
			if (bandWidth > result) {
				result = bandWidth;
			}
		}
		return result;
	}

	public static <T extends Number> int getProfile(Matrix<T> m) {
		int result = 0;
		int bandWidth;
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			bandWidth = 0;
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				if (Math.abs(m.get(i, j).doubleValue()) > 0) {
					bandWidth = m.getNumberOfColumns() / 2 - j;
					break;
				}
			}
			result += bandWidth;
		}
		return result;
	}

	public static Double getEuclidianMetric(Matrix<Double> m) {
		Double sum = 0d;
		for (int i = 0; i < m.getNumberOfRows(); i++) {
			for (int j = 0; j < m.getNumberOfColumns(); j++) {
				sum = m.get(i, j) * m.get(i, j);
			}
		}
		return Math.pow(sum, 0.5d);
	}

	public static Double getEuclidianDistance(Matrix<Double> m1, Matrix<Double> m2) {
		return getEuclidianMetric(difference(m1, m2));
	}

	public static double maxInColumn(Matrix<Double> m, int j) {
		double result = Double.MIN_VALUE;

		for (int i = 0; i < m.getNumberOfRows(); i++) {
			if (m.get(i, j) > result) {
				result = m.get(i, j);
			}
		}

		return result;
	}

	public static double minInColumn(Matrix<Double> m, int j) {
		double result = Double.MAX_VALUE;

		for (int i = 0; i < m.getNumberOfRows(); i++) {
			if (m.get(i, j) < result) {
				result = m.get(i, j);
			}
		}

		return result;
	}

	/**
	 * Method gets element from array supposing that this array represents matrix
	 *
	 * @param array		   array of elements
	 * @param i			   i-th row in matrix
	 * @param j			   j-th column in matrix
	 * @param numberOfColumns total number of columns in matrix
	 * @param <T>             type of elements in matrix
	 * @return element on (i, j) position in matrix represented by array
	 */
	public static <T> T getElement(@NotNull T[] array, int i, int j, int numberOfColumns) {
		return (T) array[i * numberOfColumns + j];
	}

	/**
	 * Method sets element from array supposing that this array represents matrix
	 *
	 * @param array		   array of elements
	 * @param value
	 * @param i			   i-th row in matrix
	 * @param j			   j-th column in matrix
	 * @param numberOfColumns total number of columns in matrix
	 */
	public static <T> void setElement(@NotNull T[] array, T value, int i, int j, int numberOfColumns) {
		array[i * numberOfColumns + j] = value;
	}

	public static <T> boolean areEqual(Matrix<T> l, Matrix<T> r) {
		return new MatrixEquals<T>().init(new BinaryMatrixOperationInput<T>(l, r)).doAlgorithm();
	}

	public static <T> Matrix<T> read(@NotNull Matrix<T> matrix, @NotNull String fileName, @NotNull MatrixFileFormat fileFormat, @Nullable T defaultValue) throws IOException, IllegalMatrixFormatException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
			return read(matrix, in, fileFormat, defaultValue);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static <T> Matrix<T> read(@NotNull Matrix<T> matrix, @NotNull InputStream inputStream, @NotNull MatrixFileFormat fileFormat, @Nullable T defaultValue) throws IOException, IllegalMatrixFormatException {

		final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

		String s = in.readLine();

		// skip comments
		while (s.startsWith("%")) {
			s = in.readLine();
		}

		String[] values = JStrings.split(s, " ");

		if (!JCollections.isEmpty(values)) {

			if (values.length == 1) {
				Integer size = Integer.valueOf(values[0]);
				matrix.init(size, size);
			} else if (values.length > 1) {
				Integer m = Integer.valueOf(values[0]);
				Integer n = Integer.valueOf(values[1]);
				matrix.init(m, n);
			} else {
				throw new IllegalMatrixFormatException("Matrix dimensions have to be specified!");
			}

			switch (fileFormat) {
				case dense:

					for (int i = 0; i < matrix.getNumberOfRows(); i++) {

						s = in.readLine();

						if (s != null) {
							values = JStrings.split(in.readLine(), " ");

							if (values != null && values.length == matrix.getNumberOfColumns()) {

								try {
									for (int j = 0; j < matrix.getNumberOfColumns(); j++) {

										matrix.set(i, j, matrix.getMatrixHelper().getValueFromString(values[j]));

									}
								} catch (IllegalArgumentException e) {
									throw new IllegalMatrixFormatException(e);
								}

							} else {
								throw new IllegalMatrixFormatException("Number of columns in file differs from expected!");
							}
						} else {
							throw new IllegalMatrixFormatException("Number of rows in file differs from expected!");
						}

					}
					break;
				case sparse:
					while ((s = in.readLine()) != null) {

						values = JStrings.split(s, " ");

						if (values.length > 1) {

							final Integer param0 = Integer.valueOf(values[0]) - 1;
							final Integer param1 = Integer.valueOf(values[1]) - 1;

							if (values.length > 2) {
								try {
									final T param2 = matrix.getMatrixHelper().getValueFromString(values[2]);

									matrix.set(param0, param1, param2);
								} catch (IllegalArgumentException e) {
									throw new IllegalMatrixFormatException(e);
								}
							} else {
								if (defaultValue != null) {
									matrix.set(param0, param1, defaultValue);
								} else {
									throw new IllegalMatrixFormatException("Default value has to be set!");
								}
							}
						} else {
							throw new IllegalMatrixFormatException("Number of columns in file differs from expected!");
						}
					}
					break;
			}
		}

		return matrix;
	}

	public static <T> void write(@NotNull Matrix<T> matrix, @NotNull String fileName, @NotNull MatrixFileFormat matrixFileFormat) throws IOException {
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(fileName);
			write(matrix, fileOutputStream, matrixFileFormat);
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}

	public static <T> OutputStream write(@NotNull Matrix<T> matrix, @NotNull OutputStream outputStream, @NotNull MatrixFileFormat matrixFileFormat) throws IOException {
		final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));

		out.print(String.valueOf(matrix.getNumberOfRows()));
		out.print(" ");
		out.println(String.valueOf(matrix.getNumberOfColumns()));

		T value;
		if (matrixFileFormat.equals(MatrixFileFormat.sparse)) {

			for (int i = 0; i < matrix.getNumberOfRows(); i++) {
				for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
					out.println((i + 1) + " " + (j + 1) + " " + matrix.getMatrixHelper().getStringValue(matrix.get(i, j)));
				}
			}

		} else if (matrixFileFormat.equals(MatrixFileFormat.dense)) {
			for (int i = 0; i < matrix.getNumberOfRows(); i++) {
				for (int j = 0; j < matrix.getNumberOfColumns(); j++) {
					out.print(matrix.getMatrixHelper().getStringValue(matrix.get(i, j)) + " ");
				}
				out.println();
			}
		}

		out.flush();

		return outputStream;
	}

	/*
		public void save(String fName, MatrixFileFormat matrixFileFormat) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fName));

		out.write(String.valueOf(this.getNumberOfRows()));
		out.write(" ");
		out.write(String.valueOf(this.getNumberOfColumns()));
		out.newLine();

		if (matrixFileFormat.equals(MatrixFileFormat.sparse)) {
			for (int i = 0; i < this.getNumberOfRows(); i++) {
				List<Property<T, Integer>> row = this.getRows().find(i);
				if (row != null) {
					for (Property<T, Integer> element : row) {
						out.write((i+1) + " " + (element.getId()+1) + " " + element.getValue());
						out.newLine();
					}
				}
			}
		} else if (matrixFileFormat.equals(MatrixFileFormat.dense)) {
			int index;
			for (int i = 0; i < this.getNumberOfRows(); i++) {
				List<Property<T, Integer>> row = this.getRows().find(i);
				if (row != null) {
					index = 0;
					for (Property<T, Integer> element : row) {
						while (index < element.getId()) {
							out.write(0d + " ");
							index++;
						}
						out.write(element.getValue() + " ");
						index++;
					}
				} else {
					for (int j = 0; j < this.getNumberOfColumns(); j++) {
						out.write(0d + " ");
					}
				}
				out.newLine();
			}
		}

		out.close();
	}

	*/
}
