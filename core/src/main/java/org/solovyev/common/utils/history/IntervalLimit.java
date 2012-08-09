package org.solovyev.common.utils.history;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JCloneable;

/**
 * User: serso
 * Date: 8/9/12
 * Time: 12:12 PM
 */
public interface IntervalLimit<T extends Comparable<T>>
        extends Comparable<IntervalLimit<T>>,
        JCloneable<IntervalLimit<T>> {

    @Nullable
    T getValue();

    boolean isClosed();

    boolean isOpened();

    boolean isLowest();

    boolean isHighest();

    boolean isLowerThan(@NotNull T that);
    boolean isLowerThan(@NotNull IntervalLimit<T> that);

    boolean isLowerOrEqualsThan(@NotNull T that);
    boolean isLowerOrEqualsThan(@NotNull IntervalLimit<T> that);

    boolean isHigherThan(@NotNull T that);
    boolean isHigherThan(@NotNull IntervalLimit<T> that);

    boolean isHigherOrEqualsThan(@NotNull T that);
    boolean isHigherOrEqualsThan(@NotNull IntervalLimit<T> that);
}
