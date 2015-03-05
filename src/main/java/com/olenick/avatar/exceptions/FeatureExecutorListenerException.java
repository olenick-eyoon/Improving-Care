package com.olenick.avatar.exceptions;

/**
 * Exception while listening to the FeatureExecutor.
 */
public class FeatureExecutorListenerException extends Exception {
    public FeatureExecutorListenerException() {}

    public FeatureExecutorListenerException(final String message) {
        super(message);
    }

    public FeatureExecutorListenerException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public FeatureExecutorListenerException(final Throwable cause) {
        super(cause);
    }

    public FeatureExecutorListenerException(final String message,
            final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
