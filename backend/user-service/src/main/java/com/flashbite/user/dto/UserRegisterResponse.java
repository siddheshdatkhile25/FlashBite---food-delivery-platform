package com.flashbite.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for user registration")
public record UserRegisterResponse(
        AuthUserResponse user,
        String verificationStatus
) {
}
