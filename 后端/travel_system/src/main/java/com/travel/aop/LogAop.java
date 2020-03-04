package com.travel.aop;

import com.travel.pojo.Syslog;
import com.travel.pojo.UserInfo;
import com.travel.service.SyslogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SyslogService syslogService;
    @Autowired
    private HttpServletRequest requests;

    private final static Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    private Date visitTime; //访问的时间
    private String ip; //用户ip
    private String url; //访问的url
    private Long executionTime; //执行方法的总时间
    private String method; //执行的方法
    private String packagez;//方法对应的包
    private String username;

    @Pointcut("execution(* com.travel.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void logBefore(JoinPoint jp) throws NoSuchMethodException {
        //访问时间
        visitTime = new Date();

        //ip
        ip = getIp();

        //获取url
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        url = req.getRequestURL().toString();

        Signature signature = jp.getSignature();
        //访问方法所在的包名
        packagez = signature.getDeclaringTypeName();
        //访问的方法名
        method = signature.getName();

    }

    @After("log()")
    public void logAfter(JoinPoint jp) {
        //访问总时长
        executionTime = new Date().getTime() - visitTime.getTime();

        //用户名
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            UserInfo user = (UserInfo) session.getAttribute("user");
            try {
                username = user.getUsername();
            } catch (Exception e) {
                username = "未登录/登录失败";
                LOGGER.info("username未找到-session可能过期或用户未登录");
            }

        Syslog syslog = new Syslog();
        syslog.setExecutionTime(executionTime);
        syslog.setIp(ip);
        syslog.setMethod("[类名] " + packagez + "[方法名 ]" + method);
        syslog.setUrl(url);
        syslog.setUsername(username);
        syslog.setVisitTime(visitTime);

        syslogService.addLog(syslog);
    }

    @AfterThrowing("log()")
    public String exceptionLog() {
        return "failer";
    }

    public String getIp() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }
}
