package com.flashbite.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerThenLoginHappyPath() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "alex@example.com",
                                  "phone": "+15551234567",
                                  "password": "Str0ng!Pass",
                                  "role": "CUSTOMER"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value("alex@example.com"))
                .andExpect(jsonPath("$.verificationStatus").value("Verification email and SMS queued"));

        UserEntity savedUser = userRepository.findByEmail("alex@example.com").orElseThrow();
        assertThat(savedUser.getPasswordHash()).isNotEqualTo("Str0ng!Pass");
        assertThat(passwordEncoder.matches("Str0ng!Pass", savedUser.getPasswordHash())).isTrue();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "identifier": "alex@example.com",
                                  "password": "Str0ng!Pass"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokens.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.tokens.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.tokens.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.user.email").value("alex@example.com"));
    }

    @Test
    void refreshTokensThenLogoutFlow() throws Exception {
        // Register a unique user for this test
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "sarah@example.com",
                                  "phone": "+15559998888",
                                  "password": "Str0ng!Pass",
                                  "role": "CUSTOMER"
                                }
                                """))
                .andExpect(status().isCreated());

        // 1. Initial Login
        org.springframework.test.web.servlet.MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "identifier": "sarah@example.com",
                                  "password": "Str0ng!Pass"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String originalRefreshToken = com.jayway.jsonpath.JsonPath.read(
                loginResult.getResponse().getContentAsString(), 
                "$.tokens.refreshToken"
        );

        // 2. Trigger a Refresh
        org.springframework.test.web.servlet.MvcResult refreshResult = mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\": \"" + originalRefreshToken + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").value(org.hamcrest.Matchers.not(originalRefreshToken))) 
                .andReturn();

        String newRefreshToken = com.jayway.jsonpath.JsonPath.read(
                refreshResult.getResponse().getContentAsString(), 
                "$.refreshToken"
        );

        // 3. Logout using the new refresh token
        mockMvc.perform(post("/api/v1/auth/logout")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\": \"" + newRefreshToken + "\"}"))
                .andExpect(status().isNoContent()); 

        // 4. Attempt to use the logged-out token (Expect 401 Unauthorized)
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(APPLICATION_JSON)
                        .content("{\"refreshToken\": \"" + newRefreshToken + "\"}"))
                .andExpect(status().isUnauthorized());
    }
}
