package com.flashbite.restaurant.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
public class RestaurantEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID ownerUserId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(precision = 9, scale = 6)
    private BigDecimal lat;

    @Column(precision = 9, scale = 6)
    private BigDecimal lng;

    @Column(nullable = false, length = 120)
    private String cuisine;

    @Column(columnDefinition = "TEXT")
    private String logoUrl;

    @Column(nullable = false, length = 32)
    private String status;
}
