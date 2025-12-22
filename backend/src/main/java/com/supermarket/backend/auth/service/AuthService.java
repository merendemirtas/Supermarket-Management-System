package com.supermarket.backend.auth.service;

import com.supermarket.backend.auth.dto.AuthResponse;
import com.supermarket.backend.auth.dto.LoginRequest;
import com.supermarket.backend.auth.dto.RegisterRequest;
import com.supermarket.backend.user.dto.CreateUserRequest;
import com.supermarket.backend.user.entity.User;
import com.supermarket.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        CreateUserRequest create = new CreateUserRequest();
        create.setUsername(req.getUsername());
        create.setPassword(req.getPassword());
        create.setFullName(req.getFullName());
        create.setRoleCode(req.getRoleCode());

        User user = userService.createUser(create);

        List<String> permissions = userService.getPermissionCodesByUser(user);

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole().getRoleCode(),
                permissions
        );

        return new AuthResponse(token, jwtService.getExpirationMs() / 1000);
    }

    public AuthResponse login(LoginRequest req) {
        User user = userService.findByUsernameOrThrow(req.getUsername());

        if (!user.isActive()) {
            throw new IllegalArgumentException("User is inactive");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        List<String> permissions = userService.getPermissionCodesByUser(user);

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole().getRoleCode(),
                permissions
        );

        return new AuthResponse(token, jwtService.getExpirationMs() / 1000);
    }
}
