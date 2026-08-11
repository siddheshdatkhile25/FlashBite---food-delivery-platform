package com.flashbite.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank(message = "Refresh Token is required")
    String refreshToken
) {
}
