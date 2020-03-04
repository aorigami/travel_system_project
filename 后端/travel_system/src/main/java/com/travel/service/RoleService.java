package com.travel.service;

import com.travel.pojo.Permission;
import com.travel.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll(Integer page, Integer rows);

    void addRole(Role role);

    List<Permission> findNoPerByRoleId(String roleId);

    void addPerToRole(String roleId, String[] perIds);

    Role findPerAndPerByRoleId(String roleId);
}
