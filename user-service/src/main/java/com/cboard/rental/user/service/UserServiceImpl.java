package com.cboard.rental.user.service;

import com.cboard.rental.user.entity.User;
import com.cboard.rental.user.entity.UserRole;
import com.cboard.rental.user.repository.RolePermissionRepository;
import com.cboard.rental.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RolePermissionRepository rolePermissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password before saving
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User user) {
        // Retrieve the existing user from the database
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null; // User not found
        }

        // Only update non-null fields
        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getRoles() != null) {
            existingUser.setRoles(user.getRoles());
        }

        // Save and return the updated user
        return userRepository.save(existingUser);
    }


    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void assignRoleToUser(Long userId, UserRole userRole) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoles().add(userRole);
        userRepository.save(user);
    }
}
