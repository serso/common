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
import org.solovyev.common.collections.Collections;
import org.solovyev.common.collections.LoopData;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 8/7/12
 * Time: 8:24 PM
 */
public class StringCollections {

    /**
     * @param string    - string where stored list of objects separated by delimiter
     * @param delimiter - delimiter with which string will be split
     * @param parser    - object that will create objects of specified type
     * @param <T>       - type of object
     * @return list of objects, not null
     */
    @NotNull
    public static <T> List<T> split(@Nullable String string,
                                    @NotNull String delimiter,
                                    @NotNull Parser<T> parser) {
        final List<T> result = new ArrayList<T>();

        if (!Strings.isEmpty(string)) {
            @SuppressWarnings({"ConstantConditions"}) final String[] parts = string.split(delimiter);

            if (!Collections.isEmpty(parts)) {
                for (String part : parts) {
                    result.add(parser.parseValue(part));
                }
            }
        }

        return result;
    }

    /**
     * @param string    - string where stored list of objects separated by delimiter
     * @param delimiter - delimiter with which string will be split
     * @return list of objects, not null
     */
    @NotNull
    public static List<String> split(@Nullable String string, @NotNull String delimiter) {
        return split(string, delimiter, StringMapper.getInstance());
    }

    @Nullable
    public static <T> String formatValue(@Nullable List<T> values,
                                         @NotNull String delimiter,
                                         @NotNull org.solovyev.common.text.Formatter<T> formatter) {
        String result = null;

        if (!Collections.isEmpty(values)) {
            final StringBuilder sb = new StringBuilder();

            final LoopData ld = new LoopData(values);
            for (T value : values) {
                sb.append(formatter.formatValue(value));
                if (!ld.isLastAndNext()) {
                    sb.append(delimiter);
                }
            }

            result = sb.toString();
        }

        return result;
    }
}
