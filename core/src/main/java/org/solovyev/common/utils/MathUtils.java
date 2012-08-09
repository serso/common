package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
 * User: serso
 * Date: 02.07.2009
 * Time: 13:32:29
 */
public class MathUtils {

	/**
	 * @param nominal nominal
	 * @param value   value
	 * @return nearest value to specified value that can be divided by nominal value without remainder
	 */
	public static double getRoundedAmount(double nominal, double value) {
		double result;
		int numberOfTimes = (int) (value / nominal);
		result = numberOfTimes * nominal;
		return result;
	}

	/**
	 * @param l	first number
	 * @param sign sign
	 * @param r	second number
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
	public static double sumUp(Double l, Double r) {
		return sumUp(l, 1, r);
	}

	/**
	 * @param l fist number
	 * @param r second number
	 * @return difference of two numbers (supposed: null = 0)
	 */
	public static double subtract(Double l, Double r) {
		return sumUp(l, -1, r);
	}

	/**
	 * Method compares two double values with specified precision
	 *
	 * @param d1		first value to compare
	 * @param d2		second value for compare
	 * @param precision number of digits after dot
	 * @return 'true' if values are equal with specified precision
	 */
	public static boolean equals(double d1, double d2, int precision) {
		assert precision >= 1;
		return Math.abs(d1 - d2) < getMaxPreciseAmount(precision);
	}

	/**
	 * Method tests if first value is less than second with specified precision
	 *
	 * @param d1		first value to compare
	 * @param d2		second value for compare
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

	public static double getNotNull(@Nullable Double value) {
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
				if (CompareTools.comparePreparedObjects(first, second) < 0) {
					result = second;
				}
				break;
			default:
				throw new UnsupportedOperationException("Comparison type " + comparisonType + " is not supported in minMax() method!");
		}

		return result;
	}

	public static final float MIN_AMOUNT = 0.05f;

	public static double round(@NotNull Double value, int precision) {
		double factor = Math.pow(10, precision);
		return ((double) Math.round(value * factor)) / factor;
	}

	public static float getDistance(@NotNull Point2d startPoint,
									@NotNull Point2d endPoint) {
		return getNorm(subtract(endPoint, startPoint));
	}

	public static Point2d subtract(@NotNull Point2d p1, @NotNull Point2d p2) {
		return new Point2d(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}

	public static Point2d sum(@NotNull Point2d p1, @NotNull Point2d p2) {
		return new Point2d(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}

	public static float getNorm(@NotNull Point2d point) {
		return (float) Math.pow(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2), 0.5);
	}

	public static float getAngle(@NotNull Point2d startPoint,
								 @NotNull Point2d axisEndPoint,
								 @NotNull Point2d endPoint,
								 @Nullable MutableObject<Boolean> left) {
		final Point2d axisVector = subtract(axisEndPoint, startPoint);
		final Point2d vector = subtract(endPoint, startPoint);

		double a_2 = Math.pow(getDistance(vector, axisVector), 2);
		double b = getNorm(vector);
		double b_2 = Math.pow(b, 2);
		double c = getNorm(axisVector);
		double c_2 = Math.pow(c, 2);

		if (left != null) {
			left.setObject(axisVector.getX() * vector.getY() - axisVector.getY() * vector.getX() < 0);
		}

		return (float) Math.acos((-a_2 + b_2 + c_2) / (2 * b * c));
	}

	public static double countMean(@NotNull List<Double> objects) {

		double sum = 0d;
		for (Double object : objects) {
			sum += object;
		}

		return objects.size() == 0 ? 0d : (sum / objects.size());
	}

	public static double countStandardDeviation(@NotNull Double mean, @NotNull List<Double> objects) {
		double sum = 0d;

		for (Double object : objects) {
			sum += Math.pow(object - mean, 2);
		}

		return objects.size() == 0 ? 0d : Math.sqrt(sum / objects.size());
	}

	public static StatData getStatData(@NotNull List<Double> objects) {

		final double mean = countMean(objects);
		final double standardDeviation = countStandardDeviation(mean, objects);

		return new StatData(mean, standardDeviation);
	}

	public static class StatData {

		private final double mean;

		private final double standardDeviation;

		public StatData(double mean, double standardDeviation) {
			this.mean = mean;
			this.standardDeviation = standardDeviation;
		}

		public double getMean() {
			return mean;
		}

		public double getStandardDeviation() {
			return standardDeviation;
		}

	}

/*	*//**
	 * Method returns intersection of 2 intervals.
	 *
	 * @param interval1 first interval
	 * @param interval2 second interval
	 * @param ih		interval helper object which will compare borders
	 * @param <T>       border type
	 * @return intersection of 2 intervals
	 *//*
	public static <T extends Comparable<T>> Interval<T> intersection(@NotNull Interval<T> int1, @NotNull Interval<T> int2) {
		Interval<T> result = null;

		if (earlier(int1.getLeftLimit(), true, int2.getLeftLimit(), true) ||
				(int1.isLeftLimitClosed() && int2.isLeftLimitClosed() && ih.compare(int1.getLeftLimit(), int2.getLeftLimit()) == 0)) {

			if (earlier(int2.getLeftLimit(), true, int1.getRightLimit(), false, ih) ||
					(int1.isLeftLimitClosed() && int2.isLeftLimitClosed() && ih.compare(int2.getLeftLimit(), int1.getRightLimit()) == 0)) {

				result = ih.createInstance();

				result.setLeftBorder(int2.getLeftLimit());
				result.setLeftBorderIn(int2.isLeftLimitClosed());

				if (earlier(int1.getRightLimit(), false, int2.getRightLimit(), false, ih) || (int1.isLeftLimitClosed() && int2.isLeftLimitClosed() && ih.compare(int1.getRightLimit(), int2.getRightLimit()) == 0)) {
					result.setRightBorder(int1.getRightLimit());
					result.setRightBorderIn(int1.isRightLimitClosed());
				} else if (ih.compare(int1.getRightLimit(), int2.getRightLimit()) == 0) {
					result.setRightBorder(int2.getRightLimit());
					result.setRightBorderIn(int2.isRightLimitClosed() && int1.isRightLimitClosed());
				} else {
					result.setRightBorder(int2.getRightLimit());
					result.setRightBorderIn(int2.isRightLimitClosed());
				}
			}

		} else {

			if (earlier(int1.getLeftLimit(), true, int2.getRightLimit(), false, ih) ||
					(int1.isLeftLimitClosed() && int2.isLeftLimitClosed() && ih.compare(int1.getLeftLimit(), int2.getRightLimit()) == 0)) {

				result = ih.createInstance();

				// Set result's left border as a maximum of 2 left borders
				if (ih.compare(int1.getLeftLimit(), int2.getLeftLimit()) == 0) {
					result.setLeftBorder(int1.getLeftLimit());
					result.setLeftBorderIn(int1.isLeftLimitClosed() && int2.isLeftLimitClosed());
				} else if (!earlier(int1.getLeftLimit(), true, int2.getLeftLimit(), true, ih) && int1.isLeftLimitClosed()) {
					result.setLeftBorder(int1.getLeftLimit());
					result.setLeftBorderIn(int1.isLeftLimitClosed());
				} else {
					result.setLeftBorder(int2.getLeftLimit());
					result.setLeftBorderIn(int2.isLeftLimitClosed());
				}

				if (earlier(int2.getRightLimit(), false, int1.getRightLimit(), false, ih) ||
						(int1.isLeftLimitClosed() && int2.isLeftLimitClosed() && ih.compare(int2.getRightLimit(), int1.getRightLimit()) == 0)) {

					result.setRightBorder(int2.getRightLimit());
					result.setRightBorderIn(int2.isRightLimitClosed());
				} else {

					// Set result's right border as a minimum of 2 right borders
					if (ih.compare(int1.getRightLimit(), int2.getRightLimit()) == 0) {
						result.setRightBorder(int1.getRightLimit());
						result.setRightBorderIn(int1.isRightLimitClosed() && int2.isRightLimitClosed());
					} else if (earlier(int1.getRightLimit(), false, int2.getRightLimit(), false, ih)) {
						result.setRightBorder(int1.getRightLimit());
						result.setRightBorderIn(int1.isRightLimitClosed());
					} else {
						result.setRightBorder(int2.getRightLimit());
						result.setRightBorderIn(int2.isRightLimitClosed());
					}
				}
			}
		}

		return result;
	}*/

	private static <T extends Comparable<T>> boolean earlier(@Nullable T t1,
                                                             boolean isNegativeInf1,
                                                             @Nullable T t2,
                                                             boolean isNegativeInf2) {
		boolean result;

		if (t1 == null && t2 == null && (isNegativeInf1 == isNegativeInf2)) {
			// -inf and -inf or +inf and +inf
			result = false;
		} else if (t1 == null) {
			// anything bigger then -inf if left
			result = isNegativeInf1;
		} else if (t2 == null) {
			// anything lower then +inf if right
			result = !isNegativeInf2;
		} else {
			result = t1.compareTo(t2) < 0;
		}

		return result;
	}
}
