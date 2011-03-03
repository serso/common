package org.solovyev.common.math.matrix;

/**
 * User: serso
 * Date: 3/3/11
 * Time: 10:59 AM
 */
public class MathSparseMatrix<T extends Number> extends SparseMatrix<T> {

	@Override
	protected T getValueFromString(String value) throws InstantiationException, IllegalAccessException {
		T result = null;
		if (value != null) {
			result = (T) Double.valueOf(value);
		}
		return result;
	}

	@Override
	protected T getEmptyValue() {
		return (T) new Double(0d);
	}
}
