/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: serso
 * Date: 9/26/11
 * Time: 11:07 PM
 */
public class NumberParser<N extends Number> implements Parser<N> {

    /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    static final List<Class<? extends Number>> supportedClasses = Arrays.<Class<? extends Number>>asList(Integer.class, Float.class, Long.class, Double.class);

    private static final Map<Class<?>, Parser<?>> parsers = new HashMap<Class<?>, Parser<?>>(supportedClasses.size());

    static {
        for (Class<? extends Number> supportedClass : supportedClasses) {
            parsers.put(supportedClass, new NumberParser<Number>(supportedClass));
        }
    }

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @NotNull
    private final Class<? extends N> clazz;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private NumberParser(@NotNull Class<? extends N> clazz) {
        this.clazz = clazz;
    }

    @NotNull
    public static <N extends Number> Parser<N> of(@NotNull Class<N> clazz) {
        assert supportedClasses.contains(clazz) : "Class " + clazz + " is not supported by " + NumberParser.class;
        return (Parser<N>) parsers.get(clazz);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public N parseValue(@Nullable String value) throws IllegalArgumentException {
        N result;

        if (value != null) {
            if (this.clazz.equals(Integer.class)) {
                result = (N) Integer.valueOf(value);
            } else if (this.clazz.equals(Float.class)) {
                result = (N) Float.valueOf(value);
            } else if (this.clazz.equals(Long.class)) {
                result = (N) Long.valueOf(value);
            } else if (this.clazz.equals(Double.class)) {
                result = (N) Double.valueOf(value);
            } else {
                throw new UnsupportedOperationException(this.clazz + " is not supported!");
            }
        } else {
            result = null;
        }

        return result;
    }
}
