package org.solovyev.common.math.matrix.helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 3:03 PM
 */
public interface MatrixHelper<T> {

	String getStringValue(T t);

	public static enum Helper {

		double_(new DoubleMatrixHelper());

		private final MatrixHelper helper;

		Helper(@NotNull MatrixHelper helper) {
			this.helper = helper;
		}

		public <T> MatrixHelper<T> getHelper() {
			return (MatrixHelper<T>)helper;
		}
	}

	@Nullable
	T getValueFromString(String value) throws IllegalArgumentException;

	@Nullable
	T getEmptyValue();

	@NotNull
	public Class<T> getObjectClass();
}
