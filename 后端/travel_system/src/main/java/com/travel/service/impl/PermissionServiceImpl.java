package com.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.travel.mapper.PermissionMapper;
import com.travel.pojo.Permission;
import com.travel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper perMapper;

    //查询所有权限
    @Override
    public List<Permission> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return perMapper.selectList(null);
    }

    //添加权限
    @Override
    public void addPer(Permission permission) {
        perMapper.insert(permission);
    }
}
