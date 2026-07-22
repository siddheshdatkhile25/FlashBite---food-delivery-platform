package com.flashbite.user.dto;

import java.util.UUID;

import com.flashbite.common.domain.UserRole;
import com.flashbite.user.domain.UserStatus;

public record AuthUserResponse(
        UUID userId,
        String email,
        String phone,
        UserRole role,
        UserStatus status,
        boolean emailVerified,
        boolean phoneVerified
) {
}
