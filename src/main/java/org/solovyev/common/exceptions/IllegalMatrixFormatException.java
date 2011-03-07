package org.solovyev.common.exceptions;

/**
 * User: serso
 * Date: 3/7/11
 * Time: 1:37 PM
 */
public class IllegalMatrixFormatException extends  SersoException {

	public IllegalMatrixFormatException() {
	}

	public IllegalMatrixFormatException(String message) {
		super(message);
	}

	public IllegalMatrixFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalMatrixFormatException(Throwable cause) {
		super(cause);
	}
}
