package com.travel.service;

import com.travel.pojo.Syslog;

import java.util.List;

public interface SyslogService {
    void addLog(Syslog syslog);

    List<Syslog> findAll(Integer page, Integer rows);

    void delSyslog(List<String> syslogIds);

    List<Syslog> likeSearch(String searchTitle,Integer page, Integer rows);
}
