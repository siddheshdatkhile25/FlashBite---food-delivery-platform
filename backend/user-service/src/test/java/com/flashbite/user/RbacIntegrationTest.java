package com.flashbite.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class RbacIntegrationTest {

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
                          "phone": "+1555000000%d",
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

    @org.springframework.boot.test.context.TestConfiguration
    static class TestControllerConfig {
        @org.springframework.web.bind.annotation.RestController
        static class TestController {
            @org.springframework.web.bind.annotation.GetMapping("/api/v1/admin/dummy")
            public String admin() { return "ok"; }
            
            @org.springframework.web.bind.annotation.GetMapping("/api/v1/restaurant/dummy")
            public String restaurant() { return "ok"; }
            
            @org.springframework.web.bind.annotation.GetMapping("/api/v1/cart/dummy")
            public String cart() { return "ok"; }
            
            @org.springframework.web.bind.annotation.GetMapping("/api/v1/driver/dummy")
            public String driver() { return "ok"; }
        }
    }

    @Test
    void customerCannotAccessAdminOrRestaurantRoutes() throws Exception {
        String token = getAccessTokenForRole("customer_rbac@example.com", "CUSTOMER");

        // Customer hitting admin route -> 403 Forbidden
        mockMvc.perform(get("/api/v1/admin/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());

        // Customer hitting restaurant route -> 403 Forbidden
        mockMvc.perform(get("/api/v1/restaurant/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());

        // Customer hitting customer route -> 200 OK
        mockMvc.perform(get("/api/v1/cart/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanAccessAllRoutes() throws Exception {
        String token = getAccessTokenForRole("admin_rbac@example.com", "ADMIN");

        // Admin hitting admin route -> 200 OK
        mockMvc.perform(get("/api/v1/admin/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // Admin hitting customer route -> 200 OK
        mockMvc.perform(get("/api/v1/cart/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void restaurantCanAccessRestaurantButNotAdminRoutes() throws Exception {
        String token = getAccessTokenForRole("restaurant_rbac@example.com", "RESTAURANT");

        // Restaurant hitting restaurant route -> 200 OK
        mockMvc.perform(get("/api/v1/restaurant/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // Restaurant hitting admin route -> 403 Forbidden
        mockMvc.perform(get("/api/v1/admin/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void driverCanAccessDriverButNotCustomerRoutes() throws Exception {
        String token = getAccessTokenForRole("driver_rbac@example.com", "DRIVER");

        // Driver hitting driver route -> 200 OK
        mockMvc.perform(get("/api/v1/driver/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // Driver hitting cart route -> 403 Forbidden
        mockMvc.perform(get("/api/v1/cart/dummy")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
