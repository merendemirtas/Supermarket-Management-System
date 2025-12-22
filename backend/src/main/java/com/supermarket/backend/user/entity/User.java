package com.supermarket.backend.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // hashed

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
