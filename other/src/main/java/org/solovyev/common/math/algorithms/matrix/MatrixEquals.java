/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

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
