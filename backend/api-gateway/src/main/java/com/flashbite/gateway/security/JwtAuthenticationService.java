package com.flashbite.gateway.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;
import com.flashbite.common.security.JwtClaims;
import com.flashbite.gateway.config.GatewaySecurityProperties;

@Service
public class JwtAuthenticationService {
    private final GatewaySecurityProperties securityProperties;

    public JwtAuthenticationService(GatewaySecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public AuthenticatedUser authenticate(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            throw unauthorized("Missing bearer token");
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();
        if (!StringUtils.hasText(token)) {
            throw unauthorized("Missing bearer token");
        }

        try {
            Jwt jwt = jwtDecoder().decode(token);
            String userId = jwt.getSubject();
            if (!StringUtils.hasText(userId)) {
                throw unauthorized("JWT subject is required");
            }

            String role = jwt.getClaimAsString(JwtClaims.ROLE);
            
            return new AuthenticatedUser(userId, role);
            
        } catch (JwtException exception) {
            throw unauthorized("Invalid or expired token");
        }
    }

    private JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(
                securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    private FlashBiteException unauthorized(String message) {
        return new FlashBiteException(ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, message);
    }

    public record AuthenticatedUser(String userId, String role) {
    }
}
