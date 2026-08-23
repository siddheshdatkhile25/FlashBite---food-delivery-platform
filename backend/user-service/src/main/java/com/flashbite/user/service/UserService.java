package com.flashbite.user.service;


import com.flashbite.user.dto.UserProfileReponse;
import com.flashbite.user.dto.UserProfileRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserProfileReponse> getUserProfile(UUID userId);

    Optional<UserProfileReponse> updateUserProfile(UUID userId , UserProfileRequest userProfileRequest);

}
