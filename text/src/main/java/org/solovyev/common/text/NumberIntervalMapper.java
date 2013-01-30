/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
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
