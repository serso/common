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

import java.util.ArrayList;
import java.util.List;

public class ListMapper<E> extends CollectionMapper<List<E>, E> {

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private ListMapper(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter, @NotNull String delimiter) {
        super(parser, formatter, delimiter);
    }

    private ListMapper(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter) {
        super(parser, formatter);
    }

    private ListMapper(@NotNull Mapper<E> mapper, @NotNull String delimiter) {
        super(mapper, delimiter);
    }

    private ListMapper(@NotNull Mapper<E> mapper) {
        super(mapper);
    }

    @NotNull
    public static <E> ListMapper<E> newInstance(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter, @NotNull String delimiter) {
        return new ListMapper<E>(parser, formatter, delimiter);
    }

    @NotNull
    public static <E> ListMapper<E> newInstance(@NotNull Parser<E> parser, @NotNull Formatter<E> formatter) {
        return new ListMapper<E>(parser, formatter);
    }

    @NotNull
    public static <E> ListMapper<E> newInstance(@NotNull Mapper<E> mapper, @NotNull String delimiter) {
        return new ListMapper<E>(mapper, delimiter);
    }

    @NotNull
    public static <E> ListMapper<E> newInstance(@NotNull Mapper<E> mapper) {
        return new ListMapper<E>(mapper);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @NotNull
    @Override
    protected List<E> newCollection() {
        return new ArrayList<E>();
    }
}
