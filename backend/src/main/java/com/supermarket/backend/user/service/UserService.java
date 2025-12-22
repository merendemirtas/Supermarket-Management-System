package com.supermarket.backend.user.service;

import com.supermarket.backend.user.dto.CreateUserRequest;
import com.supermarket.backend.user.entity.Role;
import com.supermarket.backend.user.entity.User;
import com.supermarket.backend.user.repository.RolePermissionRepository;
import com.supermarket.backend.user.repository.RoleRepository;
import com.supermarket.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + req.getUsername());
        }

        Role role = roleRepository.findByRoleCode(req.getRoleCode())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + req.getRoleCode()));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setFullName(req.getFullName());
        user.setActive(true);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return userRepository.save(user);
    }

    public User findByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    public List<String> getPermissionCodesByUser(User user) {
        return rolePermissionRepository.findPermissionCodesByRoleId(user.getRole().getId());
    }
}
