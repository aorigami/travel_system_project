package com.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.travel.mapper.PermissionMapper;
import com.travel.mapper.RoleMapper;
import com.travel.mapper.UserinfoMapper;
import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import com.travel.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserinfoMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper perMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    //用户登录
    @Override
    public UserInfo findUserByUsername(String username) {
        UserInfo userAndRole = userMapper.findUserByUsername(username);
        return userAndRole;
    }

    //用户注册
    @Override
    public void addUser(UserInfo userInfo) {
        userMapper.insert(userInfo);
    }

    //查询所有用户
    @Override
    public List<UserInfo> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<UserInfo> userInfos = userMapper.selectList(null);

        return userInfos;
    }


    //根据用户Id，查出该用户对应的角色，角色对应的权限
    @Override
    public UserInfo findUserAndRoleAndpermByUserId2(String userId) {
        return getUserInfoAll(userId);
    }

    //根据用户Id，查出该用户对应的角色，角色对应的权限(redis缓存更新)
    @Override
    public UserInfo findUserAndRoleAndpermByUserId(String userId) {
        UserInfo userInfo = getUserInfoAll(userId);

        //将用户-角色-权限存入redis
        String user = JSON.toJSONString(userInfo);
        redisTemplate.opsForValue().set("user-role-per", user, 1, TimeUnit.DAYS);

        return userInfo;
    }

    //头上两方法的抽取代码
    private UserInfo getUserInfoAll(String userId) {
        UserInfo userInfo = userMapper.selectById(userId);

        List<Role> roleList = roleMapper.findRoleByUserId(userId);

        for (Role role : roleList) {
            String roleId = role.getId();
            role.setPermissions(perMapper.findPersByRoleIds(roleId));
        }

        userInfo.setRoles(roleList);
        return userInfo;
    }

    //根据用户id查询用户 没有拥有的角色
    @Override
    public List<Role> findNoRoleByUserId(String userId) {
        return userMapper.findNoRoleByUserId(userId);
    }

    //根据用户id和角色id，添加用户没有的角色
    @Transactional
    @Override
    public void addRoleToUser(String userId, List<String> roleIds) {
        for (String roleId : roleIds) {
            userMapper.addRoleToUser(userId, roleId);
        }

        if (userId.equals(getSessionUid())) {
            sendMsg("insert", userId);
        }
    }

    //批量开启或关闭用户状态
    @Transactional
    @Override
    public void setUserStatus(Integer status, List<String> userIds) {
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        for (String userId : userIds) {
            userInfo.setId(userId);
            userMapper.updateById(userInfo);
        }
        String uid = getSessionUid();
        if (userIds.contains(uid)) {
            sendMsg("update", uid);
        }
    }

    //消息生产者
    private void sendMsg(String type, String userId) {
        amqpTemplate.convertAndSend("user." + type, userId);
    }

    //获取session里的用户id
    private String getSessionUid() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        return user.getId();
    }
}
