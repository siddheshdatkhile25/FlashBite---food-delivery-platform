package com.flashbite.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object for user login")
public record UserLoginResponse(
        AuthUserResponse user,
        AuthTokensResponse tokens
) {
}
