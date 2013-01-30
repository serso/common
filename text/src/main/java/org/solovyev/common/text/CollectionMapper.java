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

import java.util.Collection;

/**
 * User: serso
 * Date: 1/30/13
 * Time: 8:16 PM
 */
public abstract class CollectionMapper<C extends Collection<E>, E> implements Mapper<C> {

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
    private final Formatter<E> formatter;

    @NotNull
    private final Parser<E> parser;

    @NotNull
    private final String delimiter;

    protected CollectionMapper(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter, @NotNull String delimiter) {
        this.parser = parser;
        this.formatter = formatter;
        this.delimiter = delimiter;
    }

    protected CollectionMapper(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter) {
        this(parser, formatter, DEFAULT_DELIMITER);
    }

    protected CollectionMapper(@NotNull Mapper<E> mapper, @NotNull String delimiter) {
        this(mapper, mapper, delimiter);
    }

    protected CollectionMapper(@NotNull Mapper<E> mapper) {
        this(mapper, DEFAULT_DELIMITER);
    }

    @Nullable
    @Override
    public String formatValue(@Nullable C collection) throws IllegalArgumentException {
        return StringCollections.formatValue(collection, delimiter, formatter);
    }

    @Nullable
    @Override
    public C parseValue(@Nullable String value) throws IllegalArgumentException {
        return StringCollections.split(newCollection(), value, delimiter, parser);
    }

    @NotNull
    protected abstract C newCollection();
}
