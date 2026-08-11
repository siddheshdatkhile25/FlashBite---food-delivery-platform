package com.flashbite.user.service;

import com.flashbite.user.persistence.UserEntity;

public interface JwtService {

    String generateAccessToken(UserEntity user);

    String generateRefreshToken();

    long accessTokenExpiresInSeconds();

    long refreshTokenExpiresInSeconds();
}
