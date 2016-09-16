package com.amhzing.participant.query.exception;

public class QueryIdException extends RuntimeException {

    public QueryIdException() {
    }

    public QueryIdException(final String message) {
        super(message);
    }

    public QueryIdException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public QueryIdException(final Throwable cause) {
        super(cause);
    }

    public QueryIdException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
