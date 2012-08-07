package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:53 PM
 */
public interface IntervalHelper<T> extends Comparator<T> {

    @NotNull
	Interval<T> createInstance();
}