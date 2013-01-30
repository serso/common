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

import java.util.List;

public class ListMapper<T> implements Mapper<List<T>> {

    /*
    **********************************************************************
    *
    *                           CONSTANTS
    *
    **********************************************************************
    */

    @NotNull
    private static final String DEFAULT_DELIMITER = ";";

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    @NotNull
    private final Mapper<T> nestedMapper;

    @NotNull
    private final String delimiter;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private ListMapper(@NotNull Mapper<T> nestedMapper,
                       @NotNull String delimiter) {
        this.nestedMapper = nestedMapper;
        this.delimiter = delimiter;
    }

    @NotNull
    public static <T> Mapper<List<T>> newInstance(@NotNull Mapper<T> nestedMapper) {
        return new ListMapper<T>(nestedMapper, DEFAULT_DELIMITER);
    }

    @NotNull
    public static <T> Mapper<List<T>> newInstance(@NotNull Mapper<T> nestedMapper, @NotNull String delimiter) {
        return new ListMapper<T>(nestedMapper, delimiter);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Nullable
    @Override
    public String formatValue(@Nullable List<T> value) throws IllegalArgumentException {
        return StringCollections.formatValue(value, delimiter, nestedMapper);
    }

    @Nullable
    @Override
    public List<T> parseValue(@Nullable String value) throws IllegalArgumentException {
        return StringCollections.split(value, delimiter, nestedMapper);
    }
}
