package com.flashbite.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.flashbite.user.persistence.RefreshTokenEntity;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

    List<RefreshTokenEntity> findByUserId(UUID userId);
}

