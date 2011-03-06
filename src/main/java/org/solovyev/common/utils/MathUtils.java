package org.solovyev.common.utils;

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
}