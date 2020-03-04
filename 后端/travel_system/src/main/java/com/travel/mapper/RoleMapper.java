package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.Permission;
import com.travel.pojo.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from role where id in (select roleId from users_role where userId = #{userId})")
    List<Role> findRoleByUserId(String userId);

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findNoPerByRoleId(String roleId);

    @Insert("insert into role_permission values (#{perId},#{roleId})")
    void addPerToRole(@Param("perId") String perId, @Param("roleId") String roleId);

}
