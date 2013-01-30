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

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Objects;
import org.solovyev.common.JPredicate;

/**
 * User: serso
 * Date: 9/9/11
 * Time: 2:38 PM
 */
public class EqualsFilter<T> implements JPredicate<T> {

    @NotNull
    private final T filterObject;

    public EqualsFilter(@NotNull T filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public boolean apply(T object) {
        return Objects.areEqual(filterObject, object);
    }
}
