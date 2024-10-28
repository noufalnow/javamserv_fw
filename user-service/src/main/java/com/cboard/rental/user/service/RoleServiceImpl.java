package com.cboard.rental.user.service;

import java.util.HashSet;
import java.util.Set;

import com.cboard.rental.user.entity.Role;
import com.cboard.rental.user.entity.UserPermission;
import com.cboard.rental.user.entity.RolePermission;

public class RoleServiceImpl implements RoleService {

    @Override
    public Set<UserPermission> getPermissionsByRole(Role role) {
        Set<RolePermission> rolePermissions = role.getRolePermissions();
        Set<UserPermission> permissions = new HashSet<>();
        
        for (RolePermission rolePermission : rolePermissions) {
            permissions.add(rolePermission.getPermission()); // Ensure getPermission() returns UserPermission
        }

        return permissions; // Return the set of UserPermission
    }
}
