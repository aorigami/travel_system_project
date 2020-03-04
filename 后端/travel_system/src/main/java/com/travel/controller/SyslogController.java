package com.travel.controller;

import com.github.pagehelper.PageInfo;
import com.travel.pojo.Syslog;
import com.travel.service.SyslogService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/syslog")
public class SyslogController {

    @Autowired
    private SyslogService syslogService;

    //查询所有日志，并分页
    @GetMapping("/findAll")
    @RequiresRoles("ADMIN")
    public ResponseEntity<PageInfo<Syslog>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "20") Integer rows
    ) {
        List<Syslog> syslogs = syslogService.findAll(page, rows);
        if (CollectionUtils.isEmpty(syslogs)) {
            return ResponseEntity.notFound().build();
        }

        PageInfo<Syslog> pageInfo = new PageInfo<>(syslogs);
        return ResponseEntity.ok(pageInfo);
    }

    //批量删除，根据日志的id集合
    @PostMapping("/delSyslog")
    public ResponseEntity<Void> delSyslog(@RequestParam("ids") List<String> syslogIds) {
        syslogService.delSyslog(syslogIds);
        return ResponseEntity.ok(null);
    }

    //实时搜索，模糊查询
    @GetMapping("/likeSearch")
    public ResponseEntity<PageInfo<Syslog>> likeSearch(
            @RequestParam String searchTitle,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "20") Integer rows
    ) {
        List<Syslog> syslogs = syslogService.likeSearch(searchTitle,page, rows);
        if (CollectionUtils.isEmpty(syslogs)) {
            return ResponseEntity.notFound().build();
        }

        PageInfo<Syslog> pageInfo = new PageInfo<>(syslogs);
        return ResponseEntity.ok(pageInfo);
    }
}
