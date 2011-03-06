package org.solovyev.common.exceptions;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 1:35 PM
 */
public class AnyRuntimeException extends RuntimeException {

	public AnyRuntimeException() {
	}

	public AnyRuntimeException(String message) {
		super(message);
	}

	public AnyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnyRuntimeException(Throwable cause) {
		super(cause);
	}
}
