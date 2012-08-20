package org.solovyev.common.security;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:46 PM
 */
public class CiphererException extends Exception {

    public CiphererException() {
    }

    public CiphererException(String detailMessage) {
        super(detailMessage);
    }

    public CiphererException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CiphererException(Throwable throwable) {
        super(throwable);
    }
}
