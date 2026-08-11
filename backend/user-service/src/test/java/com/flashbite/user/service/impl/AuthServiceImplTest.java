package com.flashbite.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flashbite.common.domain.UserRole;
import com.flashbite.common.exception.FlashBiteException;
import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.RefreshTokenRepository;
import com.flashbite.user.repository.UserRepository;
import com.flashbite.user.service.JwtService;
import com.flashbite.user.service.VerificationNotificationService;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private VerificationNotificationService verificationNotificationService;

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthServiceImpl(
                userRepository,
                refreshTokenRepository,
                passwordEncoder,
                jwtService,
                verificationNotificationService
        );
    }

    @Test
    void registerHashesPasswordBeforeSavingUser() {
        when(userRepository.existsByEmail("alex@example.com")).thenReturn(false);
        when(userRepository.existsByPhone("+15551234567")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0, UserEntity.class);
            return UserEntity.builder()
                    .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                    .email(entity.getEmail())
                    .phone(entity.getPhone())
                    .passwordHash(entity.getPasswordHash())
                    .role(entity.getRole())
                    .status(entity.getStatus())
                    .emailVerified(entity.isEmailVerified())
                    .phoneVerified(entity.isPhoneVerified())
                    .build();
        });

        authService.register(new UserRegisterRequest(
                "Alex@Example.com",
                "+15551234567",
                "Str0ng!Pass",
                UserRole.CUSTOMER
        ));

        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo("alex@example.com");
        assertThat(savedUser.getPasswordHash()).isNotEqualTo("Str0ng!Pass");
        assertThat(passwordEncoder.matches("Str0ng!Pass", savedUser.getPasswordHash())).isTrue();
        verify(verificationNotificationService).queueRegistrationVerification(any(UserEntity.class));
    }

    @Test
    void loginReturnsUnauthorizedForInvalidCredentialsWithoutSavingRefreshToken() {
        UserEntity user = UserEntity.builder()
                .id(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .email("alex@example.com")
                .phone("+15551234567")
                .passwordHash(passwordEncoder.encode("Str0ng!Pass"))
                .role(UserRole.CUSTOMER)
                .build();
        when(userRepository.findByEmailOrPhone("alex@example.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login(new UserLoginRequest("alex@example.com", "wrong-pass")))
                .isInstanceOf(FlashBiteException.class)
                .hasMessage("Invalid credentials");

        verify(refreshTokenRepository, never()).save(any());
    }
}
