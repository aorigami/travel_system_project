package com.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.pojo.Permission;
import com.travel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionList {

    @Autowired
    private PermissionService perService;

    //查询所有权限，并分页
    @GetMapping("/findAll")
    public ResponseEntity<PageInfo<Permission>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows",  defaultValue = "5") Integer rows
    ) {

        List<Permission> permissions = perService.findAll(page, rows);
        if (CollectionUtils.isEmpty(permissions)) {
            return ResponseEntity.notFound().build();
        }
        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        return ResponseEntity.ok(pageInfo);
    }

    //添加权限
    @PostMapping("/addPer")
    public ResponseEntity<Void> addPer(@RequestBody Permission permission){

        perService.addPer(permission);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
