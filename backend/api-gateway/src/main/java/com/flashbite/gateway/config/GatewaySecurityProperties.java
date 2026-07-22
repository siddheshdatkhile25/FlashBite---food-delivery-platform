package com.flashbite.gateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "flashbite.gateway.security")
public class GatewaySecurityProperties {
    private String jwtSecret = "flashbite-dev-jwt-secret-change-me-1234567890abcdef";
    private List<String> publicPaths = new ArrayList<>(List.of(
            "/api/v1/auth/**",
            "/api/v1/health",
            "/api/v1/payments/webhook",
            "/actuator/**"
    ));
    private String userIdHeader = "X-User-Id";
    private String userRoleHeader = "X-User-Role";

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public List<String> getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }

    public String getUserIdHeader() {
        return userIdHeader;
    }

    public void setUserIdHeader(String userIdHeader) {
        this.userIdHeader = userIdHeader;
    }

    public String getUserRoleHeader() {
        return userRoleHeader;
    }

    public void setUserRoleHeader(String userRoleHeader) {
        this.userRoleHeader = userRoleHeader;
    }
}
