package com.flashbite.common.security;

import org.springframework.http.HttpStatus;

import com.flashbite.common.domain.UserRole;
import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;

public final class RoleGuard {

    private RoleGuard() {}

    // Require any of the allowed roles
    public static void requireAny(UserRole actual, UserRole... allowed) {
        for (UserRole role : allowed) {
            if (role == actual) {
                return;
            }
        }
        throw new FlashBiteException(
                ErrorCode.FORBIDDEN,
                HttpStatus.FORBIDDEN,
                "Insufficient permissions"
        );
    }

    // Require specific role
    public static void require(UserRole actual, UserRole required) {
        if (actual != required) {
            throw new FlashBiteException(
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN,
                    "Insufficient permissions"
            );
        }
    }
}
