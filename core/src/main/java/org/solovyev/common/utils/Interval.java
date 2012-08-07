package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:52 PM
 */
public interface Interval<T> extends Cloneable {

    @Nullable
    public T getLeftBorder();

    @Nullable
    public T getRightBorder();

    public void setLeftBorder(@Nullable T leftBorder);

    public void setRightBorder(@Nullable T rightBorder);

    public boolean isInInterval(@Nullable T value, @NotNull Comparator<T> comparator);

    public boolean isInInterval(@NotNull Interval<T> interval, @NotNull IntervalHelper<T> intervalHelper);

    public boolean isEmptyInterval(@NotNull Comparator<T> comparator);

    public boolean isReversed(@NotNull Comparator<T> comparator);

    public boolean isClosedInterval();

    public boolean isInfinityInterval();

    public boolean isHalfClosedInterval();

    public Interval<T> normalReverse(@NotNull Comparator<T> comparator);

    public void setLeftBorderIn(boolean leftBorderIn);

    public void setRightBorderIn(boolean rightBorderIn);

    public boolean isRightBorderIn();

    public boolean isLeftBorderIn();

    @NotNull
    public Interval<T> clone();

    boolean equals(Object obj, @NotNull Comparator<T> comparator);
}
