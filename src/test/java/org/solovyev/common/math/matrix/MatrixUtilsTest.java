package org.solovyev.common.math.matrix;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 5:37 PM
 */
public class MatrixUtilsTest extends TestCase {

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

		Matrix<Double> result = new DoubleArrayMatrix(2, 2);
		result.set(0, 0, 11d);
		result.set(0, 1, 0d);
		result.set(1, 0, 35d);
		result.set(1, 1, 20d);

		doMultiplyTest(l, r, result);
	}

	public static <T> void doMultiplyTest(@NotNull Matrix<T> l, @NotNull Matrix<T> r, @NotNull Matrix<T> expectedResult) {
	 	assertTrue(MatrixUtils.areEqual(MatrixUtils.multiply(l, r), expectedResult));
	}
}

