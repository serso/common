package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 5:03 PM
 */
public class NumberInterval<T extends Number> extends IntervalImpl<T> {

	public NumberInterval(@Nullable T leftBorder, @Nullable T rightBorder) {
		super(leftBorder, rightBorder);
	}

	public NumberInterval(@Nullable T leftBorder, boolean leftBorderIn, @Nullable T rightBorder, boolean rightBorderIn) {
		super(leftBorder, leftBorderIn, rightBorder, rightBorderIn);
	}

	@NotNull
	public Double distance() {
		final Double result;

		if ( leftBorder == null || rightBorder == null ) {
			result = Double.MAX_VALUE;
		} else {
			result = rightBorder.doubleValue() - leftBorder.doubleValue();
		}

		return result;
	}

	public void transform(@NotNull Transformation<T> t) {
		leftBorder = t.transform(leftBorder);
		rightBorder = t.transform(rightBorder);
	}

	public static interface Transformation<T> {
		 @Nullable T transform(@Nullable T value);
	}
}
