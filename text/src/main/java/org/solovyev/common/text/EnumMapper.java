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

/**
 * User: serso
 * Date: 12/25/11
 * Time: 1:17 PM
 */
public class EnumMapper<T extends Enum> implements Mapper<T> {

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final Class<T> enumClass;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    public EnumMapper(@NotNull Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @NotNull
    public static <T extends Enum> Mapper<T> newInstance(@NotNull Class<T> enumClass) {
        return new EnumMapper<T>(enumClass);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public String formatValue(@Nullable T value) throws IllegalArgumentException {
        return value == null ? null : value.name();
    }

    @Override
    public T parseValue(@Nullable String value) throws IllegalArgumentException {
        return value == null ? null : (T) Enum.valueOf(enumClass, value);
    }
}
