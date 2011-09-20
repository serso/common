package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:51 PM
 */
public class IntervalImpl<T> implements Interval<T>, Cloneable {

	@Nullable
	protected T leftBorder;

	@Nullable
	protected T rightBorder;

	protected boolean isLeftBorderIn;

	protected boolean isRightBorderIn;

	// for JAXB
	protected IntervalImpl() {
	}

	public IntervalImpl(@Nullable T leftBorder, @Nullable T rightBorder) {
		this(leftBorder, true, rightBorder, true);
	}

	public IntervalImpl(@Nullable T leftBorder, boolean leftBorderIn, @Nullable T rightBorder, boolean rightBorderIn) {
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
		isLeftBorderIn = leftBorderIn;
		isRightBorderIn = rightBorderIn;
	}

	/**
	 * @return left border
	 */
	@Nullable
	public T getLeftBorder() {
		return leftBorder;
	}

	/**
	 * Method sets left border.
	 *
	 * @param leftBorder - new left border value.
	 */
	public void setLeftBorder(@Nullable T leftBorder) {
		this.leftBorder = leftBorder;
	}

	/**
	 * @return right border
	 */
	@Nullable
	public T getRightBorder() {
		return rightBorder;
	}

	/**
	 * Method sets right border.
	 *
	 * @param rightBorder - new right border value.
	 */
	public void setRightBorder(@Nullable T rightBorder) {
		this.rightBorder = rightBorder;
	}

	/**
	 * @return true if left border date included into interval, false otherwise.
	 */
	public boolean isLeftBorderIn() {
		return isLeftBorderIn;
	}

	/**
	 * Method sets left border included into interval.
	 *
	 * @param leftBorderIn new isLeftBorderIn.
	 */
	public void setLeftBorderIn(boolean leftBorderIn) {
		isLeftBorderIn = leftBorderIn;
	}

	/**
	 * @return true if right border date included into interval, false otherwise.
	 */
	public boolean isRightBorderIn() {
		return isRightBorderIn;
	}

	/**
	 * Method sets right border included into interval.
	 *
	 * @param rightBorderIn new isRightBorderIn.
	 */
	public void setRightBorderIn(boolean rightBorderIn) {
		isRightBorderIn = rightBorderIn;
	}

	/**
	 * @param value date.
	 * @return true if single value inside interval, false otherwise
	 */
	@Override
	public boolean isInInterval(@Nullable T value, @NotNull Comparator<T> c) {
		boolean result;

		Interval<T> interval = this.clone().normalReverse(c);

		if (value == null) {
			result = false;
		} else if (interval.getLeftBorder() == null && interval.getRightBorder() != null) {
			result = isRightBorderRestriction(value, c, interval);
		} else if (interval.getLeftBorder() != null && interval.getRightBorder() == null) {
			result = isLeftBorderRestriction(value, c, interval);
		} else if (interval.getLeftBorder() == null && interval.getRightBorder() == null) {
			result = true;
		} else {
			result = isLeftBorderRestriction(value, c, interval) && isRightBorderRestriction(value, c, interval);
		}

		return result;
	}

	private boolean isLeftBorderRestriction(@NotNull T value, @NotNull Comparator<T> c, @NotNull Interval<T> interval) {
		boolean result;
		int compareResult = c.compare(value, interval.getLeftBorder());
		if (compareResult == 0) {
			result = interval.isLeftBorderIn();
		} else {
			result = compareResult > 0;
		}
		return result;
	}

	private boolean isRightBorderRestriction(@NotNull T value, @NotNull Comparator<T> c, @NotNull Interval<T> interval) {
		boolean result;
		int compareResult = c.compare(value, interval.getRightBorder());
		if (compareResult == 0) {
			result = interval.isRightBorderIn();
		} else {
			result = compareResult < 0;
		}
		return result;
	}

	/**
	 * @param interval interval.
	 * @return true if interval inside interval, false otherwise
	 */
	@Override
	public boolean isInInterval(@NotNull Interval<T> interval, @NotNull IntervalHelper<T> intervalHelper) {
		return interval.equals(MathUtils.intersection(this, interval, intervalHelper));
	}

	/**
	 * @return true if interval is empty, false otherwise
	 */
	@Override
	public boolean isEmptyInterval(@NotNull Comparator<T> comparator) {
		return isClosedInterval() && comparator.compare(leftBorder, rightBorder) == 0;
	}

	/**
	 * @return true if interval has reversed borders order, false otherwise
	 */
	@Override
	public boolean isReversed(@NotNull Comparator<T> comparator) {
		//return isClosedInterval() && comparator.earlier(rightBorder, leftBorder);
		return isClosedInterval() && comparator.compare(rightBorder, leftBorder) < 0;
	}

	/**
	 * @return true if interval is closed (borders != null), false otherwise.
	 */
	@Override
	public boolean isClosedInterval() {
		return leftBorder != null && rightBorder != null;
	}

	/**
	 * @return true if interval is infinity (borders == null), false otherwise.
	 */
	@Override
	public boolean isInfinityInterval() {
		return leftBorder == null && rightBorder == null;
	}

	/**
	 * @return true if only one border is closed, false otherwise.
	 */
	@Override
	public boolean isHalfClosedInterval() {
		return (leftBorder != null && rightBorder == null) || (leftBorder == null && rightBorder != null);
	}

	/**
	 * @return date interval with normal order of borders.
	 */
	@Override
	public IntervalImpl<T> normalReverse(@NotNull Comparator<T> comparator) {
		if (isReversed(comparator)) {
			reverse();
		}

		return this;
	}

	protected void reverse() {
		T tmp = this.getLeftBorder();
		this.leftBorder = this.getRightBorder();
		this.rightBorder = tmp;

		boolean tmp1 = this.isLeftBorderIn;
		this.isLeftBorderIn = this.isRightBorderIn;
		this.isRightBorderIn = tmp1;
	}

	@SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
	@NotNull
	@Override
	public IntervalImpl<T> clone() {
		IntervalImpl<T> clone;
		try {
			//noinspection unchecked
			clone = (IntervalImpl<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException("Cannot be cloned!");
		}
		return clone;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		if (this.isLeftBorderIn) {
			sb.append("[");
		} else {
			sb.append("(");
		}
		if (this.leftBorder != null) {
			sb.append(this.leftBorder).append(", ");
		} else {
			sb.append("-INF, ");
		}

		if (this.rightBorder != null) {
			sb.append(this.rightBorder);
		} else {
			sb.append("INF");
		}

		if (this.isRightBorderIn) {
			sb.append("]");
		} else {
			sb.append(")");
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object obj, @NotNull Comparator<T> comparator) {
		boolean result = false;
		if (obj instanceof IntervalImpl) {
			final IntervalImpl<T> int1 = this.clone().normalReverse(comparator);
			final IntervalImpl<T> int2 = ((IntervalImpl<T>) obj).clone().normalReverse(comparator);

			result = comparator.compare(int1.leftBorder, int2.leftBorder) == 0 && comparator.compare(int1.rightBorder, int2.rightBorder) == 0
					&& int1.isLeftBorderIn() == int2.isLeftBorderIn() && int1.isRightBorderIn() == int2.isRightBorderIn();

		}
		return result;
	}
}

