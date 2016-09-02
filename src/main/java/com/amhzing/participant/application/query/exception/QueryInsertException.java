package com.amhzing.participant.application.query.exception;

public class QueryInsertException extends RuntimeException {

    public QueryInsertException() {
    }

    public QueryInsertException(final String message) {
        super(message);
    }

    public QueryInsertException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public QueryInsertException(final Throwable cause) {
        super(cause);
    }

    public QueryInsertException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
