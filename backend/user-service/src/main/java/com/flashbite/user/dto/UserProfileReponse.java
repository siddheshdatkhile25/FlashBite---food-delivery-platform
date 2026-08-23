package com.flashbite.user.dto;

import com.flashbite.common.domain.UserRole;
import com.flashbite.common.domain.UserStatus;

import java.util.UUID;

public record UserProfileReponse(
        UUID userId,
        String firstname,
        String lastname,
        String avatarUrl,
        String email,
        String phone,
        UserRole role,
        UserStatus status,
        boolean emailVerified,
        boolean phoneVerified
) {
}
