package com.flashbite.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flashbite.common.domain.UserRole;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class UserRegisterRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void rejectsWeakPassword() {
        Set<ConstraintViolation<UserRegisterRequest>> violations = validator.validate(new UserRegisterRequest(
                "alex@example.com",
                "+15551234567",
                "weakpass",
                UserRole.CUSTOMER
        ));

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("password");
    }
}
