/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: serso
 * Date: 9/26/11
 * Time: 11:10 PM
 */
public class NumberMapper<N extends Number> implements Mapper<N>{

	@NotNull
	private final Formatter<N> formatter = ValueOfFormatter.getNotNullFormatter();

	@NotNull
	private final Parser<? extends N> parser;

    /**
     * Use org.solovyev.common.text.NumberMapper#getMapper(java.lang.Class<N>) instead
     * @param clazz class representing parsed object
     */
    @Deprecated
	public NumberMapper(@NotNull Class<? extends N> clazz) {
		this.parser = NumberParser.getParser(clazz);
	}

	@Override
	public String formatValue(@Nullable N value) throws IllegalArgumentException {
		return formatter.formatValue(value);
	}

	@Override
	public N parseValue(@Nullable String value) throws IllegalArgumentException {
		return this.parser.parseValue(value);
	}

        /*
    **********************************************************************
    *
    *                           STATIC
    *
    **********************************************************************
    */

    private static final List<Class<? extends Number>> supportedClasses = NumberParser.supportedClasses;

    private static final Map<Class<?>, Mapper<?>> mappers = new HashMap<Class<?>, Mapper<?>>(supportedClasses.size());

    static {
        for (Class<? extends Number> supportedClass : supportedClasses) {
            mappers.put(supportedClass, new NumberMapper<Number>(supportedClass));
        }
    }

    @NotNull
    public static <N extends Number> Mapper<N> getMapper(@NotNull Class<N> clazz) {
        assert supportedClasses.contains(clazz) : "Class " + clazz + " is not supported by " + NumberMapper.class;
        return (Mapper<N>) mappers.get(clazz);
    }
}
