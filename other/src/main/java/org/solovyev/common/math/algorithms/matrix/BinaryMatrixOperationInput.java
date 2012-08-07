package org.solovyev.common.math.algorithms.matrix;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.matrix.Matrix;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 7:20 PM
 */
public class BinaryMatrixOperationInput<T> {

	@NotNull
	private final Matrix<T> l;

	@NotNull
	private final Matrix<T> r;

	public BinaryMatrixOperationInput(@NotNull Matrix<T> l, @NotNull Matrix<T> r) {
		this.l = l;
		this.r = r;
	}

	@NotNull
	public Matrix<T> getL() {
		return l;
	}

	@NotNull
	public Matrix<T> getR() {
		return r;
	}
}
