package com.flashbite.user.dto;

public record UserRegisterResponse(
        AuthUserResponse user,
        String verificationStatus
) {
}
