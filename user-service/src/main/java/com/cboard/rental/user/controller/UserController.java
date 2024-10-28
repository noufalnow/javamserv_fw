package com.cboard.rental.user.controller;

import com.cboard.rental.user.entity.User;
import com.cboard.rental.user.service.UserService;
import jakarta.validation.Valid;
import com.cboard.rental.user.dto.UserDTO;
import com.cboard.rental.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Validated // For validating UserDTO with @Valid
public class UserController {

    @Autowired
    private UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE; // Assume this is correctly configured

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(userMapper.userToUserDTO(createdUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                                       .map(userMapper::userToUserDTO)
                                       .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LANDLORD')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.userToUserDTO(user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User not found for ID: " + id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setId(id);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(userMapper.userToUserDTO(updatedUser));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User not found for ID: " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("User not found for ID: " + id);
    }

    @GetMapping("/admin-endpoint")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Log user roles
        System.out.println("Authenticated User Roles: " + auth.getAuthorities());
        
        return "Welcome Admin!";
    }
}
