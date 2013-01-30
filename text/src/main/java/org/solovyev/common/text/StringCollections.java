package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.CollectionsUtils;
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

        if (!StringUtils.isEmpty(string)) {
            @SuppressWarnings({"ConstantConditions"}) final String[] parts = string.split(delimiter);

            if (!CollectionsUtils.isEmpty(parts)) {
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

        if (!CollectionsUtils.isEmpty(values)) {
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
