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
