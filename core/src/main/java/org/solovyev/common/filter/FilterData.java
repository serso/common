
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

package org.solovyev.common.filter;

/**
 * User: serso
 * Date: Jan 18, 2010
 * Time: 5:10:52 PM
 */

import org.jetbrains.annotations.NotNull;

/**
 * Used in Filter
 *
 * @param <T> type of element in iterator
 */
public class FilterData<T> {

    private final T element;

    private final boolean wasFiltered;

    private FilterData(T element, boolean wasFiltered) {
        this.element = element;
        this.wasFiltered = wasFiltered;
    }

    @NotNull
    public static <T> FilterData<T> newInstance(T element, boolean wasFiltered) {
        return new FilterData<T>(element, wasFiltered);
    }

    public T getElement() {
        return element;
    }

    public T getNotFiltered() {
        return wasFiltered ? null : element;
    }

    public boolean wasFiltered() {
        return wasFiltered;
    }
}
