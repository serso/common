package org.solovyev.common.text;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/20/11
 * Time: 10:35 PM
 */
public interface Parser<T> {
    /**
     * Method parses specified value and returns converted object
     *
     * @param value string to be parsed
     * @return parsed object
     * @throws IllegalArgumentException illegal argument exception in case of any error (AND ONLY ONE EXCEPTION, I.E. NO NUMBER FORMAT EXCEPTIONS AND SO ON)
     */
    @Nullable
    T parseValue(@Nullable String value) throws IllegalArgumentException;
}
