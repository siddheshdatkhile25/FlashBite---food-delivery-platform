package com.flashbite.payment.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import com.flashbite.common.domain.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private PaymentStatus status;

    @Column(nullable = false, length = 64)
    private String provider;

    @Column(length = 255)
    private String providerPaymentId;

    @Column(columnDefinition = "TEXT")
    private String failureReason;

    @Version
    @Column(nullable = false)
    private int version;
}
