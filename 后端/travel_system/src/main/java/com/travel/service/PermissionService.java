package com.travel.service;

import com.travel.pojo.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll(Integer page, Integer rows);

    void addPer(Permission permission);
}
