package com.flashbite.user.service;

import com.flashbite.user.persistence.UserEntity;

public interface VerificationNotificationService {
    void queueRegistrationVerification(UserEntity user);
}
