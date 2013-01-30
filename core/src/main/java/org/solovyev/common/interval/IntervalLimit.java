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

package org.solovyev.common.interval;

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
