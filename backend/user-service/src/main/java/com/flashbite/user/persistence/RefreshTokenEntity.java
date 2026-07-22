package com.flashbite.user.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshTokenEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 255, unique = true)
    private String tokenHash;

    @Column(nullable = false)
    private Instant expiresAt;

    private Instant revokedAt;
}
