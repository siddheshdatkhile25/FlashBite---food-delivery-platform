package com.flashbite.user.service;

import com.flashbite.user.dto.AuthTokensResponse;
import com.flashbite.user.dto.RefreshTokenRequest;
import com.flashbite.user.dto.UserLoginRequest;
import com.flashbite.user.dto.UserLoginResponse;
import com.flashbite.user.dto.UserRegisterRequest;
import com.flashbite.user.dto.UserRegisterResponse;

public interface AuthService {
    UserRegisterResponse register(UserRegisterRequest request);

    UserLoginResponse login(UserLoginRequest request);

    AuthTokensResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);
}
