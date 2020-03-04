package com.travel.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import com.travel.service.UserService;
import com.travel.utils.encodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //用户添加
    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo.getStatus());
        String password = userInfo.getPassword();
        Map<String, String> map = encodeUtil.md5Encoding(password);
        userInfo.setSalt(map.get("salt"));
        userInfo.setPassword(map.get("password"));
        userService.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //用户登录
    @PostMapping("/login")
    public ResponseEntity<Void> userLogin(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "remeberMe") Boolean remeberMe
    ) {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            token.setRememberMe(remeberMe);

            //将用户-角色-权限存入redis
            UserInfo principal = (UserInfo) subject.getPrincipal();
            userService.findUserAndRoleAndpermByUserId(principal.getId());

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (UnknownAccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //用户注销
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.stop();
        redisTemplate.delete("user-role-per");
        subject.logout();
        return ResponseEntity.ok(null);
    }

    //查询所有用户
    @GetMapping("/findAll")
    @RequiresRoles("ADMIN")
    @ResponseBody
    public ResponseEntity<PageInfo<UserInfo>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows) {
        List<UserInfo> userInfos = userService.findAll(page, rows);
        if (CollectionUtils.isEmpty(userInfos)) {
            return ResponseEntity.notFound().build();
        }
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfos);
        return ResponseEntity.ok(pageInfo);
    }

    //根据用户Id，查出该用户对应的角色，角色对应的权限
    @GetMapping("/findUserAndRoleAndpermByUserId/{id}")
    @ResponseBody
    public ResponseEntity<UserInfo> findUserAndRoleAndpermByUserId(
            @PathVariable("id") String userId
    ) {

        UserInfo userInfo = userService.findUserAndRoleAndpermByUserId2(userId);

        if (userInfo == null || userInfo.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userInfo);
    }

    //根据用户id查询用户 没有拥有的角色
    @GetMapping("/findNoRoleByUserId/{id}")
    @ResponseBody
    public ResponseEntity<List<Role>> findNoRoleByUserId(@PathVariable("id") String userId) {
        List<Role> roles = userService.findNoRoleByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }

    //根据用户id和角色id，添加用户没有的角色
    @PostMapping("/addRoleToUser")
    @ResponseBody
    public ResponseEntity<Void> addRoleToUser(
            @RequestParam("id") String userId,
            @RequestParam("ids") List<String> roleIds
    ) {
        userService.addRoleToUser(userId, roleIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //批量开启或关闭用户状态
    @PostMapping("/setUserStatus")
    @ResponseBody
    public ResponseEntity<Void> setUserStatus(
            @RequestParam Integer status,
            @RequestParam("ids") List<String> userIds
    ) {
        if (null == status || "".equals(status)) {
            return ResponseEntity.badRequest().build();
        }
        userService.setUserStatus(status, userIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //获取用户名
    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null || "".equals(user)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getUsername());
    }
}
