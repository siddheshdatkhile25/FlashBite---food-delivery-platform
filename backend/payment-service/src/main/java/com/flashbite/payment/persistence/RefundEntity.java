package com.flashbite.payment.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "refunds")
@Getter
@Setter
public class RefundEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID paymentId;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(length = 255)
    private String providerRefundId;
}
