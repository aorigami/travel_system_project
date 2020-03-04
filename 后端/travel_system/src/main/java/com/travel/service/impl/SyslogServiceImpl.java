package com.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.travel.mapper.SyslogMapper;
import com.travel.pojo.Syslog;
import com.travel.service.SyslogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyslogServiceImpl implements SyslogService {

    @Autowired
    private SyslogMapper syslogMapper;

    //aop日志
    @Override
    public void addLog(Syslog syslog) {
        syslogMapper.insert(syslog);
    }

    //查询所有日志，并分页
    @Override
    public List<Syslog> findAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return syslogMapper.selectList(null);
    }

    //批量删除，根据日志的id集合
    @Override
    public void delSyslog(List<String> syslogIds) {
        syslogMapper.deleteBatchIds(syslogIds);
    }

    //实时搜索，模糊查询
    @Override
    public List<Syslog> likeSearch(String searchTitle, Integer page, Integer rows) {
        QueryWrapper<Syslog> wrapper = new QueryWrapper<>();
        wrapper.like("username", searchTitle)
                .or().like("method", searchTitle)
                .or().like("url", searchTitle)
                .or().like("ip", searchTitle);
        PageHelper.startPage(page,rows);
        List<Syslog> syslogs = syslogMapper.selectList(wrapper);
        return syslogs;
    }

}
