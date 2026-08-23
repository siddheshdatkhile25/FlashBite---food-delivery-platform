package com.flashbite.user.service.impl;

import com.flashbite.common.exception.ErrorCode;
import com.flashbite.common.exception.FlashBiteException;
import com.flashbite.user.dto.UserProfileReponse;
import com.flashbite.user.dto.UserProfileRequest;
import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.repository.UserRepository;
import com.flashbite.user.service.UserService;
import com.flashbite.user.utils.PiiSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    @Override
    public Optional<UserProfileReponse> getUserProfile(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new FlashBiteException(ErrorCode.NOT_FOUND , HttpStatus.NOT_FOUND , "User Not Exist !"));
        UserProfileReponse response = new UserProfileReponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatarUrl(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.isEmailVerified(),
                user.isPhoneVerified()
        );
        return Optional.of(response);
    }

    @Override
    public Optional<UserProfileReponse> updateUserProfile(UUID userId , UserProfileRequest userProfileRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new FlashBiteException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND , "User Does Not Exists !"));

        String firstName = PiiSanitizer.sanitizeName(userProfileRequest.firstname());
        String lastName = PiiSanitizer.sanitizeName(userProfileRequest.lastname());
        String email = PiiSanitizer.sanitizeName(userProfileRequest.email());
        String phone = PiiSanitizer.sanitizePhone(userProfileRequest.phone());

        if (StringUtils.hasText(firstName)){
            user.setFirstName(firstName);
        }
        if (StringUtils.hasText(lastName)){
            user.setLastName(lastName);
        }
        if (StringUtils.hasText(userProfileRequest.avatarUrl())) {
            user.setAvatarUrl(userProfileRequest.avatarUrl());
        }
        if (StringUtils.hasText(email)){
            user.setEmail(email);
        }
        if (StringUtils.hasText(phone)){
            user.setPhone(phone);
        }

        UserEntity updatedUser = userRepository.save(user);

        UserProfileReponse response = new UserProfileReponse(
                updatedUser.getId(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getAvatarUrl(),
                updatedUser.getEmail(),
                updatedUser.getPhone(),
                updatedUser.getRole(),
                updatedUser.getStatus(),
                updatedUser.isEmailVerified(),
                updatedUser.isPhoneVerified()
        );
        return Optional.of(response);
    }
}
