package com.amhzing.participant.api.response;

public enum ResponseErrorCode {

    CANNOT_CREATE_PARTICIPANT("C0001", "Unable to create participant"),
    CANNOT_INSERT_PARTICIPANT("Q0001", "Unable to insert participant");

    private final String code;
    private final String message;

    ResponseErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
