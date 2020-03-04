package com.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.pojo.Permission;
import com.travel.pojo.Role;
import com.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //查询所有角色，并分页
    @GetMapping("/findAll")
    public ResponseEntity<PageInfo<Role>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows) {

        List<Role> roles = roleService.findAll(page, rows);
        if (CollectionUtils.isEmpty(roles)) {
            return ResponseEntity.notFound().build();
        }
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        return ResponseEntity.ok(pageInfo);
    }

    //添加角色
    @PostMapping("/addRole")
    public ResponseEntity<Void> addRole(@RequestBody Role role) {
        roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据角色id 查询 该角色没有的权限
    @GetMapping("/findNoPerByRoleId/{roleId}")
    public ResponseEntity<List<Permission>> findNoPerByRoleId(@PathVariable String roleId) {
        List<Permission> pers = roleService.findNoPerByRoleId(roleId);
        if (CollectionUtils.isEmpty(pers)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pers);
    }

    //根据角色id 添加 权限
    @PostMapping("/addPerToRole")
    public ResponseEntity<Void> addPerToRole(
            @RequestParam String roleId,
            @RequestParam("ids") String[] perIds) {
        roleService.addPerToRole(roleId, perIds);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //根据角色id 查询 对应权限
    @GetMapping("/findPerAndPerByRoleId/{id}")
    public ResponseEntity<Role> findPerAndPerByRoleId(@PathVariable("id") String roleId) {
        Role role = roleService.findPerAndPerByRoleId(roleId);
        if (role == null || "".equals(roleId)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(role);
    }
}
