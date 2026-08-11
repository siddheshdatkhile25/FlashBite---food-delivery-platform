package com.flashbite.user.persistence;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

@RedisHash("RefreshToken")
@Getter
@Setter
public class RefreshTokenEntity {

    @Id
    private String tokenHash; // Storing the hash of the token as the primary key makes lookups O(1)

    @Indexed
    private UUID userId;

    @TimeToLive
    private Long timeToLiveSeconds;

    private Instant expiresAt;

    private Instant revokedAt;
}
