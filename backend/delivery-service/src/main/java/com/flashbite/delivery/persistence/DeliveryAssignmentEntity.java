package com.flashbite.delivery.persistence;

import java.time.Instant;
import java.util.UUID;

import com.flashbite.common.domain.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_assignments")
@Getter
@Setter
public class DeliveryAssignmentEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID orderId;

    private UUID driverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeliveryStatus status;

    private Instant acceptedAt;

    private Instant pickedUpAt;

    private Instant deliveredAt;
}
