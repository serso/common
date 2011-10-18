/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 12:43:22 AM
 */
public enum MessageType {
	error(10),
	warning(5),
	info(1);

	private int errorLevel;

	MessageType(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	public int getErrorLevel() {
		return errorLevel;
	}

	public static MessageType getMessageTypeWithHigherLevel( MessageType mt1, MessageType mt2 ) {
		MessageType result;
		if ( mt1 == null ) {
			result = mt2;
		} else if ( mt2 == null ) {
			result = mt1;
		} else {
			if ( mt1.getErrorLevel() > mt2.getErrorLevel() ) {
				result = mt1;
			} else {
				result = mt2;
			}
		}
		return result;
	}
}
