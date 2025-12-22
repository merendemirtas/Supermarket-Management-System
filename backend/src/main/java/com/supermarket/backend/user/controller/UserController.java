package com.supermarket.backend.user.controller;

import com.supermarket.backend.user.dto.CreateUserRequest;
import com.supermarket.backend.user.entity.User;
import com.supermarket.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PostMapping
    public User create(@RequestBody CreateUserRequest req) {
        return userService.createUser(req);
    }
}
