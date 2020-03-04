package com.travel.service;

import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserInfo findUserByUsername(String username);

    void addUser(UserInfo userInfo);

    List<UserInfo> findAll(Integer page,Integer rows);

    UserInfo findUserAndRoleAndpermByUserId(String userId);

    List<Role> findNoRoleByUserId(String userId);

    void addRoleToUser(String userId, List<String>  roleIds);

    void setUserStatus(Integer status, List<String> userIds);

    UserInfo findUserAndRoleAndpermByUserId2(String userId);
}
