package com.flashbite.restaurant.persistence;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu_categories")
@Getter
@Setter
public class MenuCategoryEntity extends AuditableEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID restaurantId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private int displayOrder;
}
