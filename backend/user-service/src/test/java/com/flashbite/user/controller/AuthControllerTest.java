package com.flashbite.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashbite.common.domain.UserRole;
import com.flashbite.common.exception.GlobalExceptionHandler;
import com.flashbite.common.domain.UserStatus;
import com.flashbite.user.dto.AuthTokensResponse;
import com.flashbite.user.dto.AuthUserResponse;
import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserLoginResponse;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.dto.UserRegisterResponse;
import com.flashbite.user.service.AuthService;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void registerReturnsCreatedWhenPayloadIsValid() throws Exception {
        UserRegisterResponse response = new UserRegisterResponse(
                new AuthUserResponse(
                        UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                        "alex@example.com",
                        "+15551234567",
                        UserRole.CUSTOMER,
                        UserStatus.PENDING_VERIFICATION,
                        false,
                        false
                ),
                "Verification email and SMS queued"
        );
        when(authService.register(any(UserRegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserRegisterRequest(
                                "alex@example.com",
                                "+15551234567",
                                "Str0ng!Pass",
                                UserRole.CUSTOMER
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value("alex@example.com"))
                .andExpect(jsonPath("$.verificationStatus").value("Verification email and SMS queued"));

        verify(authService).register(any(UserRegisterRequest.class));
    }

    @Test
    void registerReturnsBadRequestWhenPasswordViolatesPolicy() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserRegisterRequest(
                                "alex@example.com",
                                "+15551234567",
                                "weakpass",
                                UserRole.CUSTOMER
                        ))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.password")
                        .value("Password must include upper, lower, number, and special character"));
    }

    @Test
    void loginReturnsTokensWhenPayloadIsValid() throws Exception {
        UserLoginResponse response = new UserLoginResponse(
                new AuthUserResponse(
                        UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                        "alex@example.com",
                        "+15551234567",
                        UserRole.CUSTOMER,
                        UserStatus.ACTIVE,
                        true,
                        true
                ),
                new AuthTokensResponse(
                        "access-token",
                        "refresh-token",
                        "Bearer",
                        900,
                        604800
                )
        );
        when(authService.login(any(UserLoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new UserLoginRequest(
                                "alex@example.com",
                                "Str0ng!Pass"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens.accessToken").value("access-token"))
                .andExpect(jsonPath("$.tokens.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.user.role").value("CUSTOMER"));

        verify(authService).login(any(UserLoginRequest.class));
    }
}
