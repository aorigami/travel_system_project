package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.Role;
import com.travel.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserinfoMapper extends BaseMapper<UserInfo> {

    @Select("select * from users where username = #{username}")
    @Results(id = "findUserByUsername", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = List.class, many = @Many(select = "com.travel.mapper.RoleMapper.findRoleByUserId"))
    })
    UserInfo findUserByUsername(String username);

    @Insert("insert into users_role values (#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);

    @Select("select * from role where id not in (select roleId from users_role where userId = #{userId})")
    List<Role> findNoRoleByUserId(String userId);
}
