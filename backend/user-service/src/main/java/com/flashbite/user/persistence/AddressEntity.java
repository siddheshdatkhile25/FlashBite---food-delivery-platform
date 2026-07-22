package com.flashbite.user.persistence;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class AddressEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 80)
    private String label;

    @Column(nullable = false, length = 255)
    private String line1;

    @Column(length = 255)
    private String line2;

    @Column(nullable = false, length = 120)
    private String city;

    @Column(length = 120)
    private String state;

    @Column(nullable = false, length = 20)
    private String zip;

    @Column(precision = 9, scale = 6)
    private BigDecimal lat;

    @Column(precision = 9, scale = 6)
    private BigDecimal lng;

    @Column(nullable = false)
    private boolean isDefault;
}
