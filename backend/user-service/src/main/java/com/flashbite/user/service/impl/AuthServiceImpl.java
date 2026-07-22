package com.flashbite.user.service.impl;

import org.springframework.stereotype.Service;

import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserLoginResponse;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.dto.UserRegisterResponse;
import com.flashbite.user.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        throw new UnsupportedOperationException("BE-010 registration flow is not implemented yet");
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        throw new UnsupportedOperationException("BE-010 login flow is not implemented yet");
    }
}
