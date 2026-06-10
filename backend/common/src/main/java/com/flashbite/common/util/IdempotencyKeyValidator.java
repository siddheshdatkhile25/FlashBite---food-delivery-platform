package com.flashbite.common.util;

import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

public final class IdempotencyKeyValidator {
    private static final Pattern KEY_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{8,128}$");

    private IdempotencyKeyValidator() {}

    public static String requireValid(String key) {
        if (key == null || key.isBlank()) {
            throw new FlashBiteException(
                    ErrorCode.INVALID_IDEMPOTENCY_KEY,
                    HttpStatus.BAD_REQUEST,
                    "Idempotency-Key header is required"
            );
        }
        if (!KEY_PATTERN.matcher(key).matches()) {
            throw new FlashBiteException(
                    ErrorCode.INVALID_IDEMPOTENCY_KEY,
                    HttpStatus.BAD_REQUEST,
                    "Idempotency-Key must be 8-128 chars (A-Z, a-z, 0-9, _, -)"
            );
        }
        return key;
    }
}
