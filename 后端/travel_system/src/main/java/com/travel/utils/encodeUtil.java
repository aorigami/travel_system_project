package com.travel.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.HashMap;
import java.util.Map;

public class encodeUtil {

    //md5 密码加盐加密
    public static Map<String,String> md5Encoding(String password){
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String enPassword = new Md5Hash(password, salt, 3).toString();
        Map<String,String> map = new HashMap<>();
        map.put("salt",salt);
        map.put("password",enPassword);
        return map;
    }
}
