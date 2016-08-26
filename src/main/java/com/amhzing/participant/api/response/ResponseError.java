package com.amhzing.participant.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonInclude
public class ResponseError {

    @NotNull
    @JsonProperty("code")
    private final String code;
    @NotNull
    @JsonProperty("message")
    private final String message;

    private ResponseError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseError create(final String code, final String message) {
        return new ResponseError(code, message);
    }

    public static ResponseError create(final ResponseErrorCode errorCode) {
        return new ResponseError(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResponseError empty() {
        return new ResponseError("", "");
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ResponseError error = (ResponseError) o;
        return Objects.equals(code, error.code) &&
                Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
