/*
* Copyright (c) Short Consulting AG 2000-2008. All rights reserved.
*/

package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/*
 * User: serso
 * Date: 02.07.2009
 * Time: 13:32:29
 */
public class MathUtils {

	/**
	 *
	 * @param nominal nominal
	 * @param value value
	 * @return nearest value to specified value that can be divided by nominal value without remainder
	 */
	public static double getRoundedAmount( double nominal, double value) {
		double result;
		int numberOfTimes = (int) ( value / nominal );
		result = numberOfTimes * nominal;
		return result;
	}

	/**
	 * @param l first number
	 * @param sign sign
	 * @param r second number
	 * @return sum or difference of two numbers (supposed: null = 0)
	 */
	public static double sumUp(Double l, int sign, Double r) {
		double result = 0d;
		if (l != null && r != null) {
			result = l + sign * r;
		} else if (l != null) {
			result = l;
		} else if (r != null) {
			result = sign * r;
		}
		return result;
	}

	/**
	 * @param l first number
	 * @param r second number
	 * @return sum of tow numbers (supposed: null = 0)
	 */
	public static double sumUp (Double l, Double r) {
		return sumUp(l, 1, r);
	}

	/**
	 * @param l fist number
	 * @param r second number
	 * @return difference of two numbers (supposed: null = 0)
	 */
	public static double subtract (Double l, Double r) {
		return sumUp(l, -1, r);
	}

	/**
	 * Method compares two double values with specified precision
	 * @param d1 first value to compare
	 * @param d2 second value for compare
	 * @param precision number of digits after dot
	 * @return 'true' if values are equal with specified precision
	 */
	public static boolean equals ( double d1, double d2, int precision ) {
		assert precision >= 1;
		return Math.abs(d1 - d2) < getMaxPreciseAmount(precision);
	}

	/**
	 * Method tests if first value is less than second with specified precision
	 * @param d1 first value to compare
	 * @param d2 second value for compare
	 * @param precision number of digits after dot
	 * @return 'true' if first value is less than second with specified precision
	 */
	public static boolean less(double d1, double d2, int precision) {
		return d1 < d2 - getMaxPreciseAmount(precision);
	}

	/**
	 * Method tests if first value is more than second with specified precision
	 *
	 * @param d1		first value to compare
	 * @param d2		second value for compare
	 * @param precision number of digits after dot
	 * @return 'true' if first value is more than second with specified precision
	 */
	public static boolean more(double d1, double d2, int precision) {
		return d1 > d2 + getMaxPreciseAmount(precision);
	}

	private static double getMaxPreciseAmount(int precision) {
		return Math.pow(0.1d, precision) / 2;
	}

	@Nullable
	public static <T extends Number> T min(T... numbers) {
		return min(CollectionsUtils.asList(numbers));
	}

	@Nullable
	public static <T extends Number> T min(Collection<T> numbers) {
		return minMax(numbers, ComparisonType.min);
	}

	public static double getNotNull ( @Nullable Double value ) {
		return value != null ? value : 0d;
	}

	@Nullable
	public static <T extends Number> T max(T... numbers) {
		return max(CollectionsUtils.asList(numbers));
	}

		@Nullable
	public static <T extends Number> T max(Collection<T> numbers) {
		return minMax(numbers, ComparisonType.max);
	}

	public static enum ComparisonType {
		min,
		max
	}

	@Nullable
	public static <T extends Number> T minMax(@Nullable Collection<T> numbers, @NotNull ComparisonType comparisonType) {
		T result = null;
		if (!CollectionsUtils.isEmpty(numbers)) {
			for (T number : numbers) {
				if (number != null) {
					if (result == null) {
						result = number;
					} else {
						result = minMax(number, result, comparisonType);
					}
				}
			}
		}
		return result;
	}

	@NotNull
	public static <T extends Number> T minMax(@NotNull T first, @NotNull T second, @NotNull ComparisonType comparisonType) {
		T result = first;

		switch (comparisonType) {
			case min:
				if (CompareTools.comparePreparedObjects(first, second) > 0) {
					result = second;
				}
				break;
			case max:
				if ( CompareTools.comparePreparedObjects(first, second) < 0 ) {
					result = second;
				}
				break;
		   default:
			   throw new UnsupportedOperationException("Comparison type " + comparisonType + " is not supported in minMax() method!");
		}

		return result;
	}
}
