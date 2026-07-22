package com.flashbite.user.persistence;

import java.util.UUID;

import com.flashbite.common.domain.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 32, unique = true)
    private String phone;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserRole role;

    @Column(nullable = false)
    private boolean emailVerified;
}
