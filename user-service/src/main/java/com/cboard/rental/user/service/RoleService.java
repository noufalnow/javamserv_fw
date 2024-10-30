package com.cboard.rental.user.service;

import java.util.Set;

import com.cboard.rental.user.entity.Role;
import com.cboard.rental.user.entity.UserPermission;

public interface RoleService {
	
	Set<UserPermission> getPermissionsByRole(Role role);


}
