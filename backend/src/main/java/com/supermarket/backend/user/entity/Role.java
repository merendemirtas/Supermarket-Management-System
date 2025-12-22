package com.supermarket.backend.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {

    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode; // ADMIN, CASHIER, STOCK_MANAGER

    @Column(name = "description")
    private String description;
}
