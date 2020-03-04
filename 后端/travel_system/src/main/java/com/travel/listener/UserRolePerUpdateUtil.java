package com.travel.listener;

import com.travel.pojo.UserInfo;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRolePerUpdateUtil {

    @Autowired
    private static UserService userService;

    public static UserInfo UpdateAfter(String userId){
        UserInfo userInfo = userService.findUserAndRoleAndpermByUserId(userId);
        return userInfo;
    }
}
