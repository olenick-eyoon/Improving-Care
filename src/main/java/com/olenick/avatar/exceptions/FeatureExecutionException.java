package com.olenick.avatar.exceptions;

/**
 * An exception in the scenario execution.
 */
public class FeatureExecutionException extends Exception {
    public FeatureExecutionException() {}

    public FeatureExecutionException(final String message) {
        super(message);
    }

    public FeatureExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FeatureExecutionException(final Throwable cause) {
        super(cause);
    }

    public FeatureExecutionException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
