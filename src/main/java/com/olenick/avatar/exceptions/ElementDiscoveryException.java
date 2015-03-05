package com.olenick.avatar.exceptions;

/**
 * An exception occurring when setting the elements into the extended ones.
 */
public class ElementDiscoveryException extends RuntimeException {
    public ElementDiscoveryException() {}

    public ElementDiscoveryException(final String message) {
        super(message);
    }

    public ElementDiscoveryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElementDiscoveryException(final Throwable cause) {
        super(cause);
    }

    public ElementDiscoveryException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
