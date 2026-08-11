package com.flashbite.user.dto;

import com.flashbite.common.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for user registration")
public record UserRegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid address")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = AuthValidationPatterns.E164_PHONE,
                message = "Phone number must be in E.164 format, for example +15551234567"
        )
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
        @Pattern(
                regexp = AuthValidationPatterns.STRONG_PASSWORD,
                message = "Password must include upper, lower, number, and special character"
        )
        String password,

        @NotNull(message = "Role is required")
        UserRole role
) {
}
