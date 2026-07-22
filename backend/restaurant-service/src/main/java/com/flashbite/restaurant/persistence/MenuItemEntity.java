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
@Table(name = "menu_items")
@Getter
@Setter
public class MenuItemEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID restaurantId;

    private UUID categoryId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean isAvailable;
}
