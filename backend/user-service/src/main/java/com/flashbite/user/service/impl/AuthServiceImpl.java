package com.flashbite.user.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;
import com.flashbite.user.dto.AuthTokensResponse;
import com.flashbite.user.dto.AuthUserResponse;
import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserLoginResponse;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.dto.UserRegisterResponse;
import com.flashbite.user.persistence.RefreshTokenEntity;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.RefreshTokenRepository;
import com.flashbite.user.repository.UserRepository;
import com.flashbite.user.service.AuthService;
import com.flashbite.user.service.JwtService;
import com.flashbite.user.service.VerificationNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VerificationNotificationService verificationNotificationService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        String normalizedEmail = normalizeEmail(request.email());
        String normalizedPhone = normalizePhone(request.phone());

        ensureUserDoesNotExist(normalizedEmail, normalizedPhone);

        UserEntity user = UserEntity.builder()
                .email(normalizedEmail)
                .phone(normalizedPhone)
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        try {
            UserEntity savedUser = userRepository.save(user);
            verificationNotificationService.queueRegistrationVerification(savedUser);
            return new UserRegisterResponse(
                    toAuthUserResponse(savedUser),
                    "Verification email and SMS queued"
            );
        } catch (DataIntegrityViolationException exception) {
            throw duplicateUserException(normalizedEmail, normalizedPhone);
        }
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        String identifier = normalizeIdentifier(request.identifier());

        UserEntity user = userRepository.findByEmailOrPhone(identifier)
                .orElseThrow(this::invalidCredentialsException);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw invalidCredentialsException();
        }

        switch (user.getStatus()) {
            case BLOCKED, DELETED -> throw invalidCredentialsException();
            default -> {
            }
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken();

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setId(UUID.randomUUID());
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenEntity.setTokenHash(hashToken(refreshToken));
        refreshTokenEntity.setExpiresAt(Instant.now().plusSeconds(jwtService.refreshTokenExpiresInSeconds()));
        refreshTokenRepository.save(refreshTokenEntity);

        return new UserLoginResponse(
                toAuthUserResponse(user),
                new AuthTokensResponse(
                        accessToken,
                        refreshToken,
                        "Bearer",
                        jwtService.accessTokenExpiresInSeconds(),
                        jwtService.refreshTokenExpiresInSeconds()
                )
        );
    }

    private void ensureUserDoesNotExist(String email, String phone) {
        boolean emailExists = userRepository.existsByEmail(email);
        boolean phoneExists = userRepository.existsByPhone(phone);

        if (emailExists || phoneExists) {
            throw duplicateUserException(email, phone);
        }
    }

    private FlashBiteException duplicateUserException(String email, String phone) {
        return new FlashBiteException(
                ErrorCode.CONFLICT,
                HttpStatus.CONFLICT,
                "Email or phone already registered",
                Map.of(
                        "email", email,
                        "phone", phone
                )
        );
    }

    private FlashBiteException invalidCredentialsException() {
        return new FlashBiteException(
                ErrorCode.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                "Invalid credentials"
        );
    }

    private AuthUserResponse toAuthUserResponse(UserEntity user) {
        return new AuthUserResponse(
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.isEmailVerified(),
                user.isPhoneVerified()
        );
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizePhone(String phone) {
        return phone.trim();
    }

    private String normalizeIdentifier(String identifier) {
        String normalized = identifier.trim();
        if (normalized.contains("@")) {
            return normalized.toLowerCase();
        }
        return normalized;
    }

    private String hashToken(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(messageDigest.digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is required", exception);
        }
    }
}
