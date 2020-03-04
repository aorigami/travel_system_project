package com.travel;

import com.travel.pojo.UserInfo;
import com.travel.service.UserService;
import com.travel.utils.encodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class shiroRegisterTest {

    @Autowired
    private UserService userService;

    @Test
    public void regTest() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        userInfo.setUsername("admin");

        Map<String, String> saltAndPassword = encodeUtil.md5Encoding("admin");
        userInfo.setPassword(saltAndPassword.get("password"));
        userInfo.setSalt(saltAndPassword.get("salt"));

        userInfo.setPhoneNum("8208820123");
        userInfo.setEmail("administrator@gmail.com");


        userService.addUser(userInfo);
    }

}
