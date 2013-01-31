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

public class CompositeMapper<T> implements Mapper<T> {

    @NotNull
    private final Formatter<T> formatter;

    @NotNull
    private final Parser<T> parser;

    private CompositeMapper(@NotNull Formatter<T> formatter, @NotNull Parser<T> parser) {
        this.formatter = formatter;
        this.parser = parser;
    }

    @NotNull
    public static <T> CompositeMapper<T> newInstance(@NotNull Formatter<T> formatter, @NotNull Parser<T> parser) {
        return new CompositeMapper<T>(formatter, parser);
    }

    @Nullable
    @Override
    public String formatValue(@Nullable T value) throws IllegalArgumentException {
        return formatter.formatValue(value);
    }

    @Nullable
    @Override
    public T parseValue(@Nullable String value) throws IllegalArgumentException {
        return parser.parseValue(value);
    }
}
