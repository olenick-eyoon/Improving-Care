package com.olenick.avatar.exceptions;

/**
 * This exception represents
 */
public class ElementNotLoadedException extends RuntimeException {
    public ElementNotLoadedException() {}

    public ElementNotLoadedException(final String message) {
        super(message);
    }

    public ElementNotLoadedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElementNotLoadedException(final Throwable cause) {
        super(cause);
    }

    public ElementNotLoadedException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
