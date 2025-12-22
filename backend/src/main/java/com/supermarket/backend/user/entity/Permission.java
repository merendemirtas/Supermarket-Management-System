package com.supermarket.backend.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission extends BaseEntity {

    @Column(name = "permission_code", nullable = false, unique = true)
    private String permissionCode; // USER_MANAGE, PRODUCT_MANAGE, SALE_CREATE...

    @Column(name = "description")
    private String description;
}
