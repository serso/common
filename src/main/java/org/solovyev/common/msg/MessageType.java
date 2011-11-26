/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 12:43:22 AM
 */
public enum MessageType {

	error(1000, "ERROR"),

	warning(500, "WARNING"),

	info(100, "INFO");

	private final int errorLevel;

	@NotNull
	private final String stringValue;

	MessageType(int errorLevel, @NotNull String stringValue) {
		this.errorLevel = errorLevel;
		this.stringValue = stringValue;
	}

	@NotNull
	public String getStringValue() {
		return stringValue;
	}

	@NotNull
	public static MessageType getMessageTypeWithHigherLevel( @NotNull MessageType l, @NotNull MessageType r ) {
		MessageType result;

		if ( l.errorLevel > r.errorLevel  ) {
			result = l;
		} else {
			result = r;
		}

		return result;
	}
}
