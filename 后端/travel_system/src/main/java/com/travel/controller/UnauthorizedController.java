package com.travel.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnauthorizedController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //没有角色，返回401code
    @GetMapping("/403")
    public ResponseEntity<Void> Unauthorized(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.stop();
        redisTemplate.delete("user-role-per");
        subject.logout();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
