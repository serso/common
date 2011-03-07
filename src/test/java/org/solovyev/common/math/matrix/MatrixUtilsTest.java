package org.solovyev.common.math.matrix;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 5:37 PM
 */
public class MatrixUtilsTest extends TestCase {

	public void testRead() throws Exception {
		final InputStream inputStream = MatrixUtilsTest.class.getResourceAsStream("/org/solovyev/math/matrix/bcspwr01.mtx");
		assertNotNull(inputStream);
		try {
			final Matrix<Double> matrix = MatrixUtils.read(new DoubleSparseMatrix(), inputStream, MatrixFileFormat.sparse, 1d);
		} finally {
			inputStream.close();
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

