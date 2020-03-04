package com.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.travel.mapper.PermissionMapper;
import com.travel.mapper.RoleMapper;
import com.travel.pojo.Permission;
import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import com.travel.service.RoleService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper perMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //查询所有角色
    @Override
    public List<Role> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return roleMapper.selectList(null);
    }

    //添加角色
    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    //根据角色id 查询 该角色没有的权限
    @Override
    public List<Permission> findNoPerByRoleId(String roleId) {
        return roleMapper.findNoPerByRoleId(roleId);
    }

    //根据角色id 添加 权限
    @Transactional
    @Override
    public void addPerToRole(String roleId, String[] perIds) {
        for (String perId : perIds) {
            roleMapper.addPerToRole(perId, roleId);
        }

        String userForRedis = redisTemplate.opsForValue().get("user-role-per");
        UserInfo userInfo = JSON.parseObject(userForRedis, UserInfo.class);
        List<Role> roles = userInfo.getRoles();
        List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        if(roleIds.contains(roleId)){
            sendMsg("insert",userInfo.getId());
        }
    }

    //根据角色id 查询 对应权限
    @Override
    public Role findPerAndPerByRoleId(String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (role == null){
            return null;
        }
        List<Permission> pers = perMapper.findPersByRoleIds(roleId);
        role.setPermissions(pers);
        return role;
    }

    //消息生产者
    private void sendMsg(String type, String userId) {
        amqpTemplate.convertAndSend("role." + type, userId);
    }

}
