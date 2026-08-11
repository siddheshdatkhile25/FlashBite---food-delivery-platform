package com.flashbite.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flashbite.user.persistence.UserEntity;
import com.flashbite.user.service.VerificationNotificationService;

@Service
public class VerificationNotificationServiceImpl implements VerificationNotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationNotificationServiceImpl.class);

    @Override
    public void queueRegistrationVerification(UserEntity user) {
        LOGGER.info("Queued verification notification stub for userId={}, email={}, phone={}",
                user.getId(), user.getEmail(), user.getPhone());
    }
}
