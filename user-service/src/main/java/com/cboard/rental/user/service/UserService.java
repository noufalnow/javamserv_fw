package com.cboard.rental.user.service;

import com.cboard.rental.user.entity.User;
import com.cboard.rental.user.entity.UserRole;
import com.cboard.rental.user.entity.UserPermission;
import java.util.List;
import java.util.Set;

public interface UserService {

    User registerUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    boolean deleteUser(Long id);
    void assignRoleToUser(Long userId, UserRole role);
    //Set<UserPermission> getPermissionsByRole(UserRole role); // Kept as UserRole
}
