package com.flashbite.order.persistence;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.flashbite.common.domain.OrderStatus;

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
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private UUID restaurantId;

    private UUID driverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> deliveryAddress;

    @Column(nullable = false)
    private Instant placedAt;

    private Instant deliveredAt;

    @Version
    @Column(nullable = false)
    private int version;
}
