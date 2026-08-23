package com.flashbite.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
public class ProfileManagementIntegrationTest {

    @Container
    static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS::getHost);
        registry.add("spring.data.redis.port", () -> REDIS.getMappedPort(6379).toString());
    }

    @Autowired
    private MockMvc mockMvc;

    private String getAccessTokenForRole(String email, String role) throws Exception {
        // Register user
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(APPLICATION_JSON)
                .content(String.format("""
                        {
                          "email": "%s",
                          "phone": "+1555100000%d",
                          "password": "Password123!",
                          "role": "%s"
                        }
                        """, email, System.currentTimeMillis() % 10000, role)))
                .andExpect(status().isCreated());

        // Login user
        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(APPLICATION_JSON)
                .content(String.format("""
                        {
                          "identifier": "%s",
                          "password": "Password123!"
                        }
                        """, email)))
                .andExpect(status().isOk())
                .andReturn();

        return JsonPath.read(loginResult.getResponse().getContentAsString(), "$.tokens.accessToken");
    }

    @Test
    void testUpdateAndRetrieveProfile() throws Exception {
        String token = getAccessTokenForRole("profile_update@example.com", "CUSTOMER");

        // 1. Get initial profile
        mockMvc.perform(get("/api/v1/users/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("profile_update@example.com"));

        // 2. Patch user profile
        mockMvc.perform(patch("/api/v1/users/me")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "firstname": "John",
                          "lastname": "Doe",
                          "avatarUrl": "https://example.com/avatar.jpg"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstname").value("John"))
                .andExpect(jsonPath("$.data.lastname").value("Doe"))
                .andExpect(jsonPath("$.data.avatarUrl").value("https://example.com/avatar.jpg"));

        // 3. Get profile again to ensure changes are reflected
        mockMvc.perform(get("/api/v1/users/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("profile_update@example.com"))
                .andExpect(jsonPath("$.data.firstname").value("John"))
                .andExpect(jsonPath("$.data.lastname").value("Doe"))
                .andExpect(jsonPath("$.data.avatarUrl").value("https://example.com/avatar.jpg"));
    }
}
