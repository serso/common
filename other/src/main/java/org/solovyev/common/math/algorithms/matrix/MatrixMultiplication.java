package org.solovyev.common.math.algorithms.matrix;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.algorithms.AbstractAlgorithm;
import org.solovyev.common.math.calculators.Calculator;
import org.solovyev.common.math.calculators.CalculatorFactory;
import org.solovyev.common.math.matrix.Matrix;
import org.solovyev.common.math.matrix.MatrixUtils;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 5:29 PM
 */
public class MatrixMultiplication<T> extends AbstractAlgorithm<BinaryMatrixOperationInput<T>, Matrix<T>> {

	@Override
	public MatrixMultiplication<T> init(@NotNull BinaryMatrixOperationInput<T> input) {
		super.init(input);

		if ( input.getL().getNumberOfColumns() != input.getR().getNumberOfRows()  ) {
			throw new IllegalArgumentException("Number of columns of left matrix has to be equals to number of rows of right!");
		}

		return this;
	}

	@Override
	public Matrix<T> doAlgorithm() {
		final Matrix<T> result = (Matrix<T>) MatrixUtils.initMatrix(input.getL().getClass(), input.getL().getNumberOfRows(), input.getR().getNumberOfColumns());

		final Calculator<T> c = CalculatorFactory.getDefaultInstance().getCalculator(input.getL().getObjectClass());

		T sum;
		for (int i = 0; i < result.getNumberOfRows(); i++) {
			for (int j = 0; j < result.getNumberOfColumns(); j++) {
				sum = c.getZero();
				for (int k = 0; k < input.getL().getNumberOfColumns(); k++) {
					sum = c.summarize(sum, c.multiply(input.getL().get(i, k), input.getR().get(k, j)));
				}
				result.set(i, j, sum);
			}
		}

		return result;
	}
}
