package com.flashbite.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class FlashBiteException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus status;
    private final Map<String, Object> details;

    public FlashBiteException(ErrorCode errorCode, HttpStatus status, String message) {
        this(errorCode, status, message, null);
    }

    public FlashBiteException(ErrorCode errorCode, HttpStatus status, String message,
                              Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.details = details;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
