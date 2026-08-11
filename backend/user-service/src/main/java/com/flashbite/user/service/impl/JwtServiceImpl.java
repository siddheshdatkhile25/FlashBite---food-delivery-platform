package com.flashbite.user.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.flashbite.common.security.JwtClaims;
import com.flashbite.user.config.AuthSecurityProperties;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int REFRESH_TOKEN_BYTES = 48;

    private final AuthSecurityProperties authSecurityProperties;
    private final byte[] signingSecret;

    public JwtServiceImpl(AuthSecurityProperties authSecurityProperties) {
        this.authSecurityProperties = authSecurityProperties;
        this.signingSecret = authSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String generateAccessToken(UserEntity user) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(accessTokenExpiresInSeconds());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issueTime(java.util.Date.from(issuedAt))
                .expirationTime(java.util.Date.from(expiresAt))
                .claim(JwtClaims.ROLE, user.getRole().name())
                .build();

        SignedJWT signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        try {
            signedJwt.sign(new MACSigner(signingSecret));
            return signedJwt.serialize();
        } catch (JOSEException exception) {
            throw new IllegalStateException("Failed to sign JWT", exception);
        }
    }

    @Override
    public String generateRefreshToken() {
        byte[] tokenBytes = new byte[REFRESH_TOKEN_BYTES];
        SECURE_RANDOM.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    @Override
    public long accessTokenExpiresInSeconds() {
        return authSecurityProperties.getAccessTokenTtlSeconds();
    }

    @Override
    public long refreshTokenExpiresInSeconds() {
        return authSecurityProperties.getRefreshTokenTtlSeconds();
    }
}
