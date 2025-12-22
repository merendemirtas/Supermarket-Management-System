package com.supermarket.backend.auth.controller;

import com.supermarket.backend.auth.dto.AuthResponse;
import com.supermarket.backend.auth.dto.LoginRequest;
import com.supermarket.backend.auth.dto.RegisterRequest;
import com.supermarket.backend.auth.service.AuthService;
import com.supermarket.backend.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        System.out.println(">>> AUTH REGISTER ENDPOINT HIT <<<");
        return authService.register(req);
}

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @GetMapping("/me")
    public Map<String, Object> me(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "").trim();
        Jws<Claims> parsed = jwtService.parse(token);
        Claims claims = parsed.getBody();

        Object perms = claims.get("permissions");
        List<String> permissions = (perms instanceof List<?> list)
                ? list.stream().map(String::valueOf).toList()
                : List.of();

        return Map.of(
                "userId", claims.getSubject(),
                "username", String.valueOf(claims.get("username")),
                "role", String.valueOf(claims.get("role")),
                "permissions", permissions
        );
    }
}
