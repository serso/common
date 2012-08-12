package org.solovyev.common.math.algorithms.matrix;

import org.solovyev.common.math.algorithms.AbstractAlgorithm;
import org.solovyev.common.math.calculators.Calculator;
import org.solovyev.common.math.calculators.CalculatorFactory;
import org.solovyev.common.equals.EqualsUtils;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 7:19 PM
 */
public class MatrixEquals<T> extends AbstractAlgorithm<BinaryMatrixOperationInput<T>, Boolean> {

	@Override
	public Boolean doAlgorithm() {
		Boolean result = true;

		if (input.getL().getNumberOfRows() != input.getR().getNumberOfRows() || input.getL().getNumberOfColumns() != input.getR().getNumberOfColumns()) {
			result = false;
		}

		if ( !input.getL().getObjectClass().equals(input.getR().getObjectClass()) ) {
			result = false;
		}

		if (result) {

			final Calculator<T> c = CalculatorFactory.getDefaultInstance().getCalculator(input.getL().getObjectClass());

			for (int i = 0; i < input.getL().getNumberOfRows(); i++) {

				for (int j = 0; j < input.getL().getNumberOfColumns(); j++) {
					if (!EqualsUtils.areEqual(input.getL().get(i, j), input.getR().get(i, j), c)) {
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
}
