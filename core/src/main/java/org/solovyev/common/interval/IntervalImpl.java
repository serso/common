package org.solovyev.common.interval;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JObject;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:51 PM
 */
public class IntervalImpl<T extends Comparable<T>>
        extends JObject
        implements Interval<T>, Cloneable {

    @NotNull
    protected IntervalLimit<T> left;

    @NotNull
    protected IntervalLimit<T> right;

    // for JAXB
    protected IntervalImpl() {
    }

    public IntervalImpl(@NotNull IntervalLimit<T> left,
                        @NotNull IntervalLimit<T> right) {
        int c = left.compareTo(right);
        if (c > 0) {
            throw new IllegalArgumentException("Left limit must <= than right!");
        } else if (c == 0) {
            if (left.isOpened() && right.isOpened()) {
                throw new IllegalArgumentException("Empty interval (x, x) is not possible!");
            }
        }

        this.left = left;
        this.right = right;
    }

    @NotNull
    public static <T extends Comparable<T>> Interval<T> newPoint(@NotNull T point) {
        return new IntervalImpl<T>(IntervalLimitImpl.newInstance(point, true), IntervalLimitImpl.newInstance(point, true));
    }

    @NotNull
    public static <T extends Comparable<T>> Interval<T> newClosed(@NotNull T left, @NotNull T right) {
        return new IntervalImpl<T>(IntervalLimitImpl.newInstance(left, true), IntervalLimitImpl.newInstance(right, true));
    }

    /**
     * @return left border
     */
    @Nullable
    public T getLeftLimit() {
        return left.getValue();
    }

    /**
     * @return right border
     */
    @Nullable
    public T getRightLimit() {
        return this.right.getValue();
    }

    @NotNull
    @Override
    public IntervalLimit<T> getRight() {
        return this.right;
    }

    @NotNull
    @Override
    public IntervalLimit<T> getLeft() {
        return this.left;
    }

    /**
     * @param value value
     * @return true if single value inside interval, false otherwise
     */
    @Override
    public boolean contains(@NotNull T value) {
        return this.left.isLowerOrEqualsThan(value) && this.right.isHigherOrEqualsThan(value);
    }

    /**
     * @param that interval.
     * @return true if interval inside interval, false otherwise
     */
    @Override
    public boolean contains(@NotNull Interval<T> that) {
        return this.left.isLowerOrEqualsThan(that.getLeft()) && this.right.isHigherOrEqualsThan(that.getRight());
    }


    /**
     * @return true if interval is closed (borders != null), false otherwise.
     */
    @Override
    public boolean isClosed() {
        return this.left.isClosed() && this.right.isClosed();
    }

    /**
     * @return true if interval is infinity (borders == null), false otherwise.
     */
    @Override
    public boolean isInfinite() {
        return this.left.isLowest() && this.right.isHighest();
    }

    /**
     * @return true if only one border is closed, false otherwise.
     */
    @Override
    public boolean isHalfClosed() {
        return (this.left.isClosed() && !this.right.isClosed()) || (!this.left.isClosed() && this.right.isClosed());
    }

    @NotNull
    @Override
    public IntervalImpl<T> clone() {
        final IntervalImpl<T> clone = (IntervalImpl<T>) super.clone();

        clone.left = this.left.clone();
        clone.right = this.right.clone();

        return clone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (this.left.isClosed()) {
            sb.append("[");
        } else {
            sb.append("(");
        }

        sb.append(this.left).append(", ");
        sb.append(this.right);

        if (this.right.isClosed()) {
            sb.append("]");
        } else {
            sb.append(")");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntervalImpl)) return false;

        final IntervalImpl that = (IntervalImpl) o;

        if (!this.left.equals(that.left)) return false;
        if (!this.right.equals(that.right)) return false;

        return true;
    }

    protected boolean equals(@NotNull T thisBorder, @Nullable Object thatBorder) {
        return thisBorder.equals(thatBorder);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}

