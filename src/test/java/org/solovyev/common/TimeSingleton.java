package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * User: serso
 * Date: 9/30/11
 * Time: 3:32 PM
 */
public enum TimeSingleton {

	instance;

	@NotNull
	private final Date date;

	TimeSingleton() {
		date = new Date();
	}

	@NotNull
	public Date getDate() {
		return date;
	}
}
