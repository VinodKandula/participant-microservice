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

    @NotNull
    @JsonProperty("correlationId")
    private final String correlationId;

    private ResponseError(final String code, final String message, final String correlationId) {
        this.code = code;
        this.message = message;
        this.correlationId = correlationId;
    }

    public static ResponseError create(final String code, final String message, final String correlationId) {
        return new ResponseError(code, message, correlationId);
    }

    public static ResponseError create(final ResponseErrorCode errorCode, final String correlationId) {
        return new ResponseError(errorCode.getCode(), errorCode.getMessage(), correlationId);
    }

    public static ResponseError empty() {
        return new ResponseError("", "", "");
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ResponseError that = (ResponseError) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, correlationId);
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
