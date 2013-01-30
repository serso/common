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

package org.solovyev.common.equals;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.EqualsResult;
import org.solovyev.common.Objects;

import java.util.List;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:05 PM
 */
public class ListEqualizer<T> implements Equalizer<List<T>> {

    private final boolean checkOrder;

    @Nullable
    protected final Equalizer<T> nestedEqualizer;

    public ListEqualizer(boolean checkOrder, @Nullable Equalizer<T> nestedEqualizer) {
        this.checkOrder = checkOrder;
        this.nestedEqualizer = nestedEqualizer;
    }

    @Override
    public boolean equals(@Nullable List<T> first, @Nullable List<T> second) {
        final EqualsResult equalsResult = Objects.getEqualsResult(first, second);
        boolean result = false;
        if (equalsResult.areBothNulls()) {
            result = true;
        } else if (equalsResult.areBothNotNulls()) {

            if (first.size() == second.size()) {
                if (checkOrder) {
                    result = true;
                    for (int i = 0; i < first.size(); i++) {
                        final T el1 = first.get(i);
                        final T el2 = second.get(i);

                        if (!Objects.areEqual(el1, el2, nestedEqualizer)) {
                            result = false;
                            break;
                        }

                    }
                } else {
                    result = new CollectionEqualizer<T>(nestedEqualizer).equals(first, second);
                }
            }
        }

        return result;
    }
}
