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

import javax.annotation.Nonnull;

import org.solovyev.common.JPredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 10/15/11
 * Time: 2:03 PM
 */
public class FilterRulesChain<T> implements JPredicate<T> {

    @Nonnull
    private final List<JPredicate<T>> filters = new ArrayList<JPredicate<T>>();

    public FilterRulesChain() {
    }

    public void addFilterRule(@Nonnull JPredicate<T> filterRule) {
        filters.add(filterRule);
    }

    @Override
    public boolean apply(T object) {
        boolean result = false;

        for (JPredicate<T> filter : filters) {
            if (filter.apply(object)) {
                result = true;
                break;
            }
        }

        return result;
    }
}
