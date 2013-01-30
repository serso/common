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
 * Date: 9/26/11
 * Time: 11:27 PM
 */
public class BooleanMapper implements Mapper<Boolean> {

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */
    @NotNull
    private final static Mapper<Boolean> instance = new BooleanMapper();

    @NotNull
    public static Mapper<Boolean> getInstance() {
        return instance;
    }

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final Formatter<Boolean> formatter = ValueOfFormatter.getNotNullFormatter();

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private BooleanMapper() {
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public String formatValue(@Nullable Boolean value) throws IllegalArgumentException {
        return formatter.formatValue(value);
    }

    @Override
    public Boolean parseValue(@Nullable String value) throws IllegalArgumentException {
        return value == null ? null : Boolean.valueOf(value);
    }
}
