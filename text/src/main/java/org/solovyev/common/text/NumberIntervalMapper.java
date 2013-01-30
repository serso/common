/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.interval.Interval;
import org.solovyev.common.interval.IntervalImpl;
import org.solovyev.common.interval.IntervalLimit;
import org.solovyev.common.interval.IntervalLimitImpl;

/**
 * User: serso
 * Date: 9/20/11
 * Time: 11:56 PM
 */
public final class NumberIntervalMapper<T extends Number & Comparable<T>> extends AbstractIntervalMapper<T> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private NumberIntervalMapper(@NotNull Class<T> clazz) {
        super(NumberMapper.of(clazz));
    }

    private NumberIntervalMapper(@NotNull Class<T> clazz, @NotNull String delimiter) {
        super(NumberMapper.of(clazz), delimiter);
    }

    @NotNull
    public static <T extends Number & Comparable<T>> NumberIntervalMapper<T> of(@NotNull Class<T> clazz) {
        return new NumberIntervalMapper<T>(clazz);
    }

    @NotNull
    public static <T extends Number & Comparable<T>> NumberIntervalMapper<T> newInstance(@NotNull Class<T> clazz, @NotNull String delimiter) {
        return new NumberIntervalMapper<T>(clazz, delimiter);
    }

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    @Override
    protected Interval<T> newInstance(@Nullable T left, @Nullable T right) {
        final IntervalLimit<T> leftLimit;
        if (left == null) {
            leftLimit = IntervalLimitImpl.newLowest();
        } else {
            leftLimit = IntervalLimitImpl.newInstance(left, true);
        }

        final IntervalLimit<T> rightLimit;
        if (right == null) {
            rightLimit = IntervalLimitImpl.newHighest();
        } else {
            rightLimit = IntervalLimitImpl.newInstance(right, true);
        }

        return new IntervalImpl<T>(leftLimit, rightLimit);
    }
}
