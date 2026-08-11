package com.flashbite.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flashbite.user.persistence.RefreshTokenEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
}
