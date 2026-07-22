package com.flashbite.delivery.persistence;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
// This annotation indicates that this class is a base class for JPA entities and its properties will be inherited by subclasses.
@EntityListeners(AuditingEntityListener.class)
// This annotation specifies that the AuditingEntityListener should be used to automatically populate auditing fields (createdAt and updatedAt) when entities are persisted or updated.
@Getter
@Setter
public abstract class AuditableEntity {
    
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
