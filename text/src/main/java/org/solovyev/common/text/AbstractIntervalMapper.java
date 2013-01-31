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

import java.util.Arrays;
import java.util.List;

/**
 * User: serso
 * Date: 9/21/11
 * Time: 12:02 AM
 */
public abstract class AbstractIntervalMapper<T extends Comparable<T>> implements Mapper<Interval<T>> {

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
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final Formatter<T> formatter;

    @NotNull
    private final Parser<T> parser;

    @NotNull
    private final String delimiter;

    // used in getMapper method
    @Nullable
    private Mapper<T> mapper;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    protected AbstractIntervalMapper(@NotNull Formatter<T> formatter, @NotNull Parser<T> parser, @NotNull String delimiter) {
        this.formatter = formatter;
        this.parser = parser;
        this.delimiter = delimiter;
    }

    protected AbstractIntervalMapper(@NotNull Mapper<T> mapper, @NotNull String delimiter) {
        this.formatter = mapper;
        this.parser = mapper;
        this.delimiter = delimiter;
    }

    protected AbstractIntervalMapper(@NotNull Formatter<T> formatter, @NotNull Parser<T> parser) {
        this(formatter, parser, DEFAULT_DELIMITER);
    }

    protected AbstractIntervalMapper(@NotNull Mapper<T> mapper) {
        this(mapper, DEFAULT_DELIMITER);

    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */


    @Override
    public final String formatValue(@Nullable Interval<T> interval) throws IllegalArgumentException {
        if (interval != null) {
            return StringCollections.formatValue(Arrays.asList(interval.getLeftLimit(), interval.getRightLimit()), delimiter, this.formatter);
        } else {
            return null;
        }
    }

    @Override
    public final Interval<T> parseValue(@Nullable String s) throws IllegalArgumentException {
        final List<T> list = StringCollections.split(s, delimiter, this.parser);

        assert list.size() == 2 : "Interval contains more than 2 elements!";
        return newInstance(list.get(0), list.get(1));
    }

    @NotNull
    protected abstract Interval<T> newInstance(@Nullable T left, @Nullable T right);

    @NotNull
    public final Parser<T> getParser() {
        return parser;
    }

    @NotNull
    public final Formatter<T> getFormatter() {
        return formatter;
    }

    @NotNull
    public final Mapper<T> getMapper() {
        if (mapper == null) {
            mapper = CompositeMapper.newInstance(formatter, parser);
        }
        return mapper;
    }
}
