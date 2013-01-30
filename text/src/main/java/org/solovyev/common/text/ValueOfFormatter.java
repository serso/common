/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 9/20/11
 * Time: 10:52 PM
 */
public class ValueOfFormatter<T> implements Formatter<T> {

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    @NotNull
    private static final ValueOfFormatter<Object> notNullFormatter = new ValueOfFormatter<Object>(false);

    @NotNull
    private static final ValueOfFormatter<Object> nullableFormatter = new ValueOfFormatter<Object>(true);

    @NotNull
    public static <T> ValueOfFormatter<T> getNotNullFormatter() {
        return (ValueOfFormatter<T>) notNullFormatter;
    }

    @NotNull
    public static <T> ValueOfFormatter<T> getNullableFormatter() {
        return (ValueOfFormatter<T>) nullableFormatter;
    }

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    private final boolean processNulls;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private ValueOfFormatter() {
        this(false);
    }

    private ValueOfFormatter(boolean processNulls) {
        this.processNulls = processNulls;
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public String formatValue(@Nullable T t) throws IllegalArgumentException {
        return t == null ? (processNulls ? String.valueOf(t) : null) : String.valueOf(t);
    }
}
