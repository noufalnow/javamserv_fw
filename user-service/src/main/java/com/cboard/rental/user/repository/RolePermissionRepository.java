package com.cboard.rental.user.repository;

import com.cboard.rental.user.entity.RolePermission;
import com.cboard.rental.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRole(UserRole role);

    // New methods for additional functionality

    // Find all RolePermissions by a specific permission name
    List<RolePermission> findByPermissionName(String permissionName);

    // Find a RolePermission by role and permission name
    RolePermission findByRoleAndPermissionName(UserRole role, String permissionName);

    // Delete RolePermissions by role
    void deleteByRole(UserRole role);
}
