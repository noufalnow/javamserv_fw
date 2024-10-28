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
        if (userRepository.existsById(id)) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Ensure password is hashed on update
            return userRepository.save(user);
        }
        return null;
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
