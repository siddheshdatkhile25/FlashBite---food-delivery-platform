package com.flashbite.user.dto;

public record UserLoginResponse(
        AuthUserResponse user,
        AuthTokensResponse tokens
) {
}
