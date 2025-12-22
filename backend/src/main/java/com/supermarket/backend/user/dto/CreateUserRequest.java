package com.supermarket.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private String fullName;
    private String roleCode; // ADMIN, CASHIER, STOCK_MANAGER
}
