package com.travel.config;

import com.alibaba.fastjson.JSON;
import com.travel.pojo.Permission;
import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import com.travel.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserReaIm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();

        String userForRedis = redisTemplate.opsForValue().get("user-role-per");
        UserInfo userInfo = JSON.parseObject(userForRedis, UserInfo.class);

        //设置角色
        List<Role> roles = userInfo.getRoles();
        List<String> rolesName = new ArrayList<>();
        roles.forEach(role -> rolesName.add(role.getRoleName()));
        sai.addRoles(rolesName);

        //设置权限
        Set<String> perName = new HashSet<>();
        roles.forEach(role -> {
            List<Permission> permissions = role.getPermissions();
            permissions.forEach(per -> {
                perName.add(per.getUrl());
            });
        });
        sai.addStringPermissions(perName);

        return sai;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserInfo user = userService.findUserByUsername(token.getUsername());

        if (user == null || "".equals(user)) {
            throw new UnknownAccountException();
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user", user);

        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }
}
