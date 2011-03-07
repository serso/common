package org.solovyev.common.exceptions;

/**
 * User: serso
 * Date: 2/6/11
 * Time: 5:07 PM
 */
public class SersoException extends Exception {

	public SersoException() {
	}

	public SersoException(String message) {
		super(message);
	}

	public SersoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SersoException(Throwable cause) {
		super(cause);
	}
}
