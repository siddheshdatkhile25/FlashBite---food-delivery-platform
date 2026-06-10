package com.flashbite.common.exception;

public enum ErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    NOT_FOUND("NOT_FOUND"),
    CONFLICT("CONFLICT"),
    INVALID_IDEMPOTENCY_KEY("INVALID_IDEMPOTENCY_KEY");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
