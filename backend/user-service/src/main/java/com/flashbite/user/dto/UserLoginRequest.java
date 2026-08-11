package com.flashbite.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for user login")
public record UserLoginRequest(
        @NotBlank(message = "Email or phone is required")
        @Size(max = 255, message = "Email or phone must be at most 255 characters")
        @Pattern(
                regexp = AuthValidationPatterns.EMAIL_OR_PHONE,
                message = "Identifier must be a valid email address or E.164 phone number"
        )
        String identifier,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
        String password
) {
}
