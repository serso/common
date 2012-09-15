/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

/**
 * User: serso
 * Date: May 11, 2010
 * Time: 11:35:05 PM
 */
public class DateUtils {

	public static int deltaDays(Date l, Date r) {
		return Days.daysBetween(new DateTime(l), new DateTime(r)).getDays();
	}
}
