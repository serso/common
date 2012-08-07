/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;

import hirondelle.date4j.DateTime;

import java.util.Date;
import java.util.TimeZone;

/**
 * User: serso
 * Date: May 11, 2010
 * Time: 11:35:05 PM
 */
public class DateUtils {

	public static int deltaDays(Date l, Date r) {
		final DateTime lDateTime = DateTime.forInstant(l.getTime(), TimeZone.getDefault());
		final DateTime rDateTime = DateTime.forInstant(r.getTime(), TimeZone.getDefault());

		return lDateTime.numDaysFrom(rDateTime);
	}
}
