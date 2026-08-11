package com.flashbite.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flashbite.user.persistence.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phone);

    @Query("""
        SELECT u
        FROM UserEntity u
        WHERE u.email = :identifier
            OR u.phone = :identifier
    """)
    Optional<UserEntity> findByEmailOrPhone(@Param("identifier") String identifier);
}
