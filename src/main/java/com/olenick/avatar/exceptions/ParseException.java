package com.olenick.avatar.exceptions;

/**
 * Parse Exception: Errors related to the parsing of use case files.
 */
public class ParseException extends Exception {
    public ParseException() {}

    public ParseException(final String message) {
        super(message);
    }

    public ParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParseException(final Throwable cause) {
        super(cause);
    }

    public ParseException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
