package com.amhzing.participant.web.response;

public enum ResponseErrorCode {

    CANNOT_CREATE_PARTICIPANT("C0001", "Unable to create participant"),
    CANNOT_INSERT_PARTICIPANT("Q0001", "Unable to insert participant"),
    CANNOT_QUERY_PARTICIPANT ("Q0002", "Unable to query participant");

    public static final String INVALID_REQUEST_CODE = "G0001";

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
