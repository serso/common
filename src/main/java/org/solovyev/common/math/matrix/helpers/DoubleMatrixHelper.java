package org.solovyev.common.math.matrix.helpers;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 3:02 PM
 */
class DoubleMatrixHelper implements MatrixHelper<Double>{

	@Override
	public String getStringValue(Double value) {
		return String.valueOf(value);
	}

	@NotNull
	@Override
	public Double getValueFromString(String value) {
		return Double.valueOf(value);
	}

	@NotNull
	@Override
	public Double getEmptyValue() {
		return 0d;
	}

	@NotNull
	@Override
	public Class<Double> getObjectClass() {
		return Double.class;
	}
}
