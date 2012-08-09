package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JCloneable;
import org.solovyev.common.utils.history.IntervalLimit;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:52 PM
 */
public interface Interval<T extends Comparable<T>> extends JCloneable<Interval<T>> {

    @Nullable
    public T getLeftLimit();

    @Nullable
    public T getRightLimit();

    @NotNull
    public IntervalLimit<T> getLeft();

    @NotNull
    public IntervalLimit<T> getRight();

    public boolean contains(@NotNull T value);

    public boolean contains(@NotNull Interval<T> that);

    public boolean isClosed();

    public boolean isInfinite();

    public boolean isHalfClosed();

    boolean equals(Object obj);
}
