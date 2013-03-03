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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.equals;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.Objects;

/**
 * User: serso
 * Date: 3/3/13
 * Time: 10:18 AM
 */
public class ArrayEqualizer<T> implements Equalizer<T[]> {

    @NotNull
    private static final ArrayEqualizer<?> instance = new ArrayEqualizer<Object>(null);

    @Nullable
    private final Equalizer<T> nestedEqualizer;

    private ArrayEqualizer(@Nullable Equalizer<T> nestedEqualizer) {
        this.nestedEqualizer = nestedEqualizer;
    }

    @NotNull
    public static <T> ArrayEqualizer<T> newWithNestedEqualizer(@NotNull Equalizer<T> nestedEqualizer) {
        return new ArrayEqualizer<T>(nestedEqualizer);
    }

    @NotNull
    public static <T> ArrayEqualizer<T> newWithNaturalEquals() {
        return (ArrayEqualizer<T>) instance;
    }

    @Override
    public boolean areEqual(@NotNull T[] first, @NotNull T[] second) {
        boolean result = false;

        if (first.length == second.length) {
            result = true;
            for (int i = 0; i < first.length; i++) {
                final T el1 = first[i];
                final T el2 = second[i];

                if (!Objects.areEqual(el1, el2, nestedEqualizer)) {
                    result = false;
                    break;
                }

            }
        }

        return result;
    }
}
