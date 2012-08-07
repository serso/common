package org.solovyev.common.math.matrix;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 5:37 PM
 */
public class MatrixUtilsTest extends TestCase {

	public void testRead() throws Exception {
		final InputStream in = MatrixUtilsTest.class.getResourceAsStream("/org/solovyev/math/matrix/bcspwr01.mtx");
		assertNotNull(in);
		try {
			final Matrix<Double> matrix = MatrixUtils.read(new DoubleSparseMatrix(), in, MatrixFileFormat.sparse, 1d);

			// some elements from the beginning
			assertEquals(matrix.get(0, 0), 1d);
			assertEquals(matrix.get(1, 0), 1d);
			assertEquals(matrix.get(38, 0), 1d);
			assertEquals(matrix.get(2, 1), 1d);
			assertEquals(matrix.get(24, 1), 1d);
			assertEquals(matrix.get(29, 1), 1d);
			assertEquals(matrix.get(2, 2), 1d);
			assertEquals(matrix.get(3, 2), 1d);
			assertEquals(matrix.get(17, 2), 1d);
			assertEquals(matrix.get(3, 3), 1d);
			assertEquals(matrix.get(13, 3), 1d);

			assertEquals(matrix.get(13, 4), 0d);
			assertEquals(matrix.get(2, 3), 0d);
			assertEquals(matrix.get(1, 2), 0d);
			assertEquals(matrix.get(0, 1), 0d);
			assertEquals(matrix.get(29, 0), 0d);

			// some from the end
			assertEquals(matrix.get(33, 33), 1d);
			assertEquals(matrix.get(34, 34), 1d);
			assertEquals(matrix.get(35, 35), 1d);
			assertEquals(matrix.get(36, 36), 1d);
			assertEquals(matrix.get(37, 37), 1d);
			assertEquals(matrix.get(38, 38), 1d);

			assertEquals(matrix.get(36, 35), 0d);
			assertEquals(matrix.get(37, 36), 0d);
			assertEquals(matrix.get(38, 37), 0d);

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			MatrixUtils.write(matrix, out, MatrixFileFormat.dense );

			System.out.print(new String(out.toByteArray()));


		} finally {
			in.close();
		}
	}

	public void testMultiply() throws Exception {
		Matrix<Double> l = new DoubleArrayMatrix(2, 3);
		l.set(0, 0, 0d);
		l.set(0, 1, -1d);
		l.set(0, 2, 2d);
		l.set(1, 0, 4d);
		l.set(1, 1, 11d);
		l.set(1, 2, 2d);

		Matrix<Double> r = new DoubleArrayMatrix(3, 2);
		r.set(0, 0, 3d);
		r.set(0, 1, -1d);
		r.set(1, 0, 1d);
		r.set(1, 1, 2d);
		r.set(2, 0, 6d);
		r.set(2, 1, 1d);

		Matrix<Double> rSparse = new DoubleSparseMatrix(3, 2);
		rSparse.set(0, 0, 3d);
		rSparse.set(0, 1, -1d);
		rSparse.set(1, 0, 1d);
		rSparse.set(1, 1, 2d);
		rSparse.set(2, 0, 6d);
		rSparse.set(2, 1, 1d);

		Matrix<Double> result = new DoubleArrayMatrix(2, 2);
		result.set(0, 0, 11d);
		result.set(0, 1, 0d);
		result.set(1, 0, 35d);
		result.set(1, 1, 20d);

		doMultiplyTest(l, r, result);
		doMultiplyTest(l, rSparse, result);

		doMultiplyTest(l, rSparse);
		doMultiplyTest(l, r);

		doEqualsTest(r, rSparse);
	}

	private <T> void doMultiplyTest(@NotNull Matrix<T> l, @NotNull Matrix<T> r) {
		MatrixUtils.multiply(l, r);
	}

	private <T> void doEqualsTest(@NotNull Matrix<T> l, @NotNull Matrix<T> r) {
		assertTrue(MatrixUtils.areEqual(l, r));
	}

	public static <T> void doMultiplyTest(@NotNull Matrix<T> l, @NotNull Matrix<T> r, @NotNull Matrix<T> expectedResult) {
		assertTrue(MatrixUtils.areEqual(MatrixUtils.multiply(l, r), expectedResult));
	}
}

